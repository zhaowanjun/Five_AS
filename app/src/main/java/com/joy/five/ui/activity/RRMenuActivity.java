package com.joy.five.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.joy.five.R;
import com.joy.five.ui.ResultListener;
import com.joy.five.view.RRPanel;

public class RRMenuActivity extends Activity {
	protected RRPanel mPanel;
    protected Button mRestart;
    protected Button mBack;
    protected Button mQuit;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_rr);
        mPanel= (RRPanel) findViewById(R.id.id_rrpanel);
        mRestart= (Button) findViewById(R.id.id_restart);
        mBack = (Button) findViewById(R.id.id_back);
        mQuit = (Button) findViewById(R.id.id_quit);

        mRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanel.restart();
            }
        });
        
        mBack.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		mPanel.back();
        	}
        });
        
        mQuit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        mPanel.setListener(new ResultListener() {
            @Override
            public void showResult(int result) {
                String text=(result==RRPanel.DRAW)?("和棋!"):(result==RRPanel.WHITE_WON?"白棋胜利!":"黑棋胜利!");
                
                AlertDialog.Builder builder=new AlertDialog.Builder(RRMenuActivity.this);
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setTitle("对战结果:");
                builder.setMessage(text);
                builder.setNegativeButton("再来一局", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPanel.restart();
                    }
                });
                builder.setPositiveButton("返回主界面", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	finish();
                    	mPanel.restart();
                    }
                });
                builder.show();
            }
        });
        
    }
}