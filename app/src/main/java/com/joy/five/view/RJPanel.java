package com.joy.five.view;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.joy.five.utils.AI;
import com.joy.five.utils.WuziqiUtil;

public class RJPanel extends RRPanel {
	private static final int COMPUTER_DONE = 100;
	private boolean enable = true;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == COMPUTER_DONE) {
				invalidate();
				enable = true;
			}
		}
	};

	public RJPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(!enable) {
			return false;
		}

		if (isGameOver)
			return false;

		int action = event.getAction();

		if (action == MotionEvent.ACTION_DOWN) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			Point p = getValidPoint(x, y);

			if (WuziqiUtil.blackArray.contains(p)
					|| WuziqiUtil.whiteArray.contains(p)) {
				return false;
			}

			WuziqiUtil.blackArray.add(p);
			WuziqiUtil.blackSign();
			invalidate();

		} else if (action == MotionEvent.ACTION_UP) {
			enable = false;
			if(!WuziqiUtil.checkFiveInLine(WuziqiUtil.blackArray)) {
				new Thread() {
					@Override
					public void run() {
						computerLuozi();
						mHandler.sendEmptyMessage(COMPUTER_DONE);
					}
				}.start();
			}
		}

		System.out.println(1);
		return true;
	}

	private void computerLuozi() {
		final int BLACK = 1;// 黑棋代号
		final int WHITE = 2;// 白棋代号
		int[][] blackBoard = new int[MAX_LINE][MAX_LINE];// 定义黑棋盘的二维数组
		int[][] whiteBoard = new int[MAX_LINE][MAX_LINE];// 定义白棋盘的二维数组
		int blackMaxValue = AI.saveMaxValue(blackBoard, BLACK);// 将黑棋最大点的值保存在对应点上
		int whiteMaxValue = AI.saveMaxValue(whiteBoard, WHITE);// 将白棋最大点的值保存在对应点上
		
		// 遍历整个棋盘找到目前最大值所在位置，在此点放棋子
		LOOP:
		for (int i = 0; i < MAX_LINE; i++) {
			for (int j = 0; j < MAX_LINE; j++) {
				if (blackMaxValue > whiteMaxValue) {
					if (blackBoard[i][j] == blackMaxValue) {
						WuziqiUtil.whiteArray.add(new Point(i,j));
						WuziqiUtil.whiteSign();
						break LOOP;
					}
				} else {
					if (whiteBoard[i][j] == whiteMaxValue) {
						WuziqiUtil.whiteArray.add(new Point(i,j));
						WuziqiUtil.whiteSign();
						break LOOP;
					}
				}
			}
		}
	}

	@Override
	// 悔棋
	public void back() {
		if ((isGameOver == false) && WuziqiUtil.blackArray.size() > 0
				&& WuziqiUtil.whiteArray.size() > 0) {
			WuziqiUtil.blackArray.remove(WuziqiUtil.blackArray.size() - 1);
			WuziqiUtil.whiteArray.remove(WuziqiUtil.whiteArray.size() - 1);
			WuziqiUtil.whiteSign();
			invalidate();
		}
	}
}
