package com.joy.five.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.joy.five.R;
import com.joy.five.ui.ResultListener;
import com.joy.five.utils.WuziqiUtil;

public class RRPanel extends View {

	protected int mPanelWidth;
	protected float mLineHeight;
	public static int MAX_LINE = 15;
	
	public static final int MAX_PIECES_NUMBER=MAX_LINE*MAX_LINE;//用于判断是否没有点可下，如果没有了即为和棋
	protected Paint paint = new Paint();
	
	protected Bitmap WhitePiece;
	protected Bitmap BlackPiece;
	protected Bitmap SignPoint;

	protected float ratioPieceOfLineHeight = 9.0f/10;
	protected boolean isBlack = true;// 轮到黑棋
	protected boolean isGameOver;
	
	protected int mResult;//0-和棋，1-白子赢，2-黑子赢
    public static final int DRAW=0;//平局
    public static final int WHITE_WON=1;//白子赢
    public static final int BLACK_WON=2;//黑子赢
    
    public RRPanel(Context context, AttributeSet attrs) {
    	super(context, attrs);
    	super.setBackgroundResource(R.mipmap.chessboard1);
    	init();
    }
    
    protected ResultListener mListener;
    public void setListener(ResultListener listener) {
    	mListener=listener;
    }

	protected void init() {
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStyle(Paint.Style.STROKE);

		WhitePiece = BitmapFactory.decodeResource(getResources(),
				R.mipmap.stone_w2);
		BlackPiece = BitmapFactory.decodeResource(getResources(),
				R.mipmap.stone_b1);
		SignPoint = BitmapFactory.decodeResource(getResources(),
				R.mipmap.sign);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		int width = Math.min(widthSize, heightSize);

		if (widthMode == MeasureSpec.UNSPECIFIED) {
			width = heightSize;
		} else if (heightMode == MeasureSpec.UNSPECIFIED) {
			width = widthSize;
		}
		setMeasuredDimension(width, width);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		super.onSizeChanged(w, h, oldw, oldh);
		mPanelWidth = w;
		mLineHeight = mPanelWidth * 1.0f / MAX_LINE;
		int pieceWidth = (int) (mLineHeight * ratioPieceOfLineHeight);
		
		WhitePiece = Bitmap.createScaledBitmap(WhitePiece, pieceWidth,
				pieceWidth, false);
		BlackPiece = Bitmap.createScaledBitmap(BlackPiece, pieceWidth,
				pieceWidth, false);
		SignPoint = Bitmap.createScaledBitmap(SignPoint, pieceWidth,
				pieceWidth, false);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawBoard(canvas);
		drawPieces(canvas);
		checkGameOver();
	}
	
	protected void drawBoard(Canvas canvas) {
		int w = mPanelWidth;
		float lineHeight = mLineHeight;
		for (int i = 0; i < MAX_LINE; i++) {
			int startX = (int) (lineHeight / 2);
			int endX = (int) (w - lineHeight / 2);
			int y = (int) ((0.5 + i) * lineHeight);
			canvas.drawLine(startX, y, endX, y, paint);
			canvas.drawLine(y, startX, y, endX, paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isGameOver) {
			return false;
		}

		int action = event.getAction();
		if (action == MotionEvent.ACTION_UP) {
			int x = (int) event.getX();
			int y = (int) event.getY();

			Point p = getValidPoint(x, y);
			if (WuziqiUtil.whiteArray.contains(p) || WuziqiUtil.blackArray.contains(p)) {
				return false;
			}

			if (isBlack) {
				WuziqiUtil.blackArray.add(p);
				WuziqiUtil.blackSign();
			} else {
				WuziqiUtil.whiteArray.add(p);
				WuziqiUtil.whiteSign();
			}
			invalidate();
			isBlack = !isBlack;
		}
		return true;
	}
	
	protected void drawPieces(Canvas canvas) {
		for (int i = 0; i < WuziqiUtil.whiteArray.size(); i++) {
			Point whitePoint = WuziqiUtil.whiteArray.get(i);
			canvas.drawBitmap(WhitePiece,
					(whitePoint.x + (1 - ratioPieceOfLineHeight) / 2)
							* mLineHeight,
					(whitePoint.y + (1 - ratioPieceOfLineHeight) / 2)
							* mLineHeight, null);
		}
		for (int i = 0; i < WuziqiUtil.blackArray.size(); i++) {
			Point blackPoint = WuziqiUtil.blackArray.get(i);
			canvas.drawBitmap(BlackPiece,
					(blackPoint.x + (1 - ratioPieceOfLineHeight) / 2)
							* mLineHeight,
					(blackPoint.y + (1 - ratioPieceOfLineHeight) / 2)
							* mLineHeight, null);
		}
		
		if(WuziqiUtil.blackArray.size()>0 || WuziqiUtil.whiteArray.size()>0) {
			canvas.drawBitmap(SignPoint,
					(WuziqiUtil.sign.x + (1 - ratioPieceOfLineHeight) / 2)
					* mLineHeight,
					(WuziqiUtil.sign.y + (1 - ratioPieceOfLineHeight) / 2)
					* mLineHeight, null);
		}
	}

	protected void checkGameOver() {
		boolean whiteWin = WuziqiUtil.checkFiveInLine(WuziqiUtil.whiteArray);
		boolean blackWin = WuziqiUtil.checkFiveInLine(WuziqiUtil.blackArray);

		if (whiteWin || blackWin) {
			isGameOver = true;
			mResult=whiteWin?WHITE_WON:BLACK_WON;
			mListener.showResult(mResult);
			return;
		}
		
		boolean isFull=WuziqiUtil.checkIsFull(WuziqiUtil.whiteArray.size()+WuziqiUtil.blackArray.size());
		if(isFull) {
            mResult=DRAW;
            mListener.showResult(mResult);
        }
	}
	
	//
	protected Point getValidPoint(int x, int y) {
		return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
	}
	
	protected static final String INSTANCE = "instance";
	protected static final String INSTANCE_GAME_OVER = "instance_game_over";
	protected static final String INSTANCE_TURN = "instance_turn";
	protected static final String INSTANCE_WHITE_ARRAY = "instance_white_array";
	protected static final String INSTANCE_BLACK_ARRAY = "instance_black_array";
	
	@Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle=new Bundle();
        bundle.putParcelable(INSTANCE,super.onSaveInstanceState());//注意，系统本来需要保存的数据不能忽视
        bundle.putBoolean(INSTANCE_GAME_OVER,isGameOver);
        bundle.putBoolean(INSTANCE_TURN,isBlack);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY,WuziqiUtil.whiteArray);//Point已经实现了Parcelable接口
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY,WuziqiUtil.blackArray);
        return bundle;
    }
	
	@Override
    protected void onRestoreInstanceState(Parcelable state) {
        //需要判断state是否为我们设置的state类型，如果是则表明有需要自己去恢复的数据
        if(state instanceof Bundle) {
            Bundle bundle=(Bundle)state;
            isGameOver=bundle.getBoolean(INSTANCE_GAME_OVER);
            isBlack=bundle.getBoolean(INSTANCE_TURN);
            WuziqiUtil.whiteArray=bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            WuziqiUtil.blackArray=bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));//恢复保存起来的系统数据，不能忽略
            return;
        }
        super.onRestoreInstanceState(state);
    }
	
	/*
	 * 重新开始
	 */
	public void restart() {
		WuziqiUtil.whiteArray.clear();
		WuziqiUtil.blackArray.clear();
        isGameOver=false;
        invalidate();
    }
	
	/*
	 * 悔棋
	 */
	public void back() {
		
		if (isGameOver==false && (WuziqiUtil.blackArray.size() > 0 || WuziqiUtil.whiteArray.size() > 0) ) {
            if (isBlack) {
            	WuziqiUtil.whiteArray.remove(WuziqiUtil.whiteArray.size() - 1);
            	WuziqiUtil.blackSign();
                isBlack = !isBlack;
            } else {
            	WuziqiUtil.blackArray.remove(WuziqiUtil.blackArray.size() - 1);
                isBlack = !isBlack;
                WuziqiUtil.whiteSign();
            }
            invalidate();
        }
	}
}
