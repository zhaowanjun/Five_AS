package com.joy.five.utils;

import android.graphics.Point;

import com.joy.five.utils.WuziqiUtil;
import com.joy.five.view.RJPanel;

public class AI {
	//设定玩家为黑棋，电脑为白棋
	private final static int BLACK = 1;
	private final static int WHITE = 2;
	
	private final static int HUOER_COMPUTER = 60;
	private final static int HUOER_PLAYER = 50;

	private final static int BANHUOER_COMPUTER = 40;
	private final static int BANHUOER_PLAYER = 35;

	private final static int SIER_COMPUTER = 10;
	private final static int SIER_PLAYER = 10;

	private final static int BANSIER_COMPUTER = 10;
	private final static int BANSIER_PLAYER = 10;

	private final static int HUOSAN_COMPUTER = 950;
	private final static int HUOSAN_PLAYER = 700;

	private final static int BANHUOSAN_COMPUTER = 900;
	private final static int BANHUOSAN_PLAYER = 650;

	private final static int SISAN_COMPUTER = 100;
	private final static int SISAN_PLAYER = 100;

	private final static int BANSISAN_COMPUTER = 100;
	private final static int BANSISAN_PLAYER = 100;

	private final static int HUOSI_COMPUTER = 10000;
	private final static int HUOSI_PLAYER = 3500;
	
	private final static int DAHUOSAN_COMPUTER = 6000;
	private final static int DAHUOSAN_PLAYER = 3500;

	private final static int BANHUOSI_COMPUTER = 5000;
	private final static int BANHUOSI_PLAYER = 3000;

	private final static int SISI_COMPUTER = 4000;
	private final static int SISI_PLAYER = 800;

	private final static int BANSISI_COMPUTER = 3600;
	private final static int BANSISI_PLAYER = 750;
	
//	private final static int HUOWU_COMPUTER = 20000;
//	private final static int HUOWU_PLAYER = 15000;

	private final static int BANHUOWU_COMPUTER = 10000;
	private final static int BANHUOWU_PLAYER = 3300;
	
//	private final static int SIWU_COMPUTER = 20000;
//	private final static int SIWU_PLAYER = 15000;
	
	private final static int BANSIWU_COMPUTER = 10000;
	private final static int BANSIWU_PLAYER = 3300;
	
	private final static int CHONGWU_COMPUTER = 30000;
	private final static int CHONGWU_PLAYER = 15000;
	
	public static int saveMaxValue(int[][] chessBoard, int color) {
		int maxValue = 0;
		int x = 0, y = 0 ;
		//遍历整个棋盘
		for(int i = 0; i< RJPanel.MAX_LINE; i++) {
			for(int j = 0; j<RJPanel.MAX_LINE; j++) {
				
				if(!WuziqiUtil.whiteArray.contains(new Point(i,j))&&
				   !WuziqiUtil.blackArray.contains(new Point(i,j)) )	//取空位点
					{
						int value = countValue(new Point(i,j), color);	//得到该点的值
						if(value>maxValue) {
							maxValue = value;
							x=i; y=j;
						}
					}
			}
		}
		chessBoard[x][y] = maxValue;	//将最大值保存在该点上
		return maxValue;
	}

	private static int countValue(Point p, int color) {
		//计算该点分值
		return evaluate(p, color);
	}
	
	/*
	 * 	活：代表几个子是相连的，中间没有空格，两端都至少有一个空格。
	 *  半活：代表几个子不是相连的，几个子中间有一个空格，两端都至少有一个空格。
	 *	死：代表几个子是相连的，中间没有空格，但有一端紧挨着对方的棋子或有一端正好在棋盘的边界。
	 *	半死：代表几个子不是相连的，几个子中间有一个空格，而且一端紧挨着对方的棋子或有一端正好在棋盘的边界。
	 *	每个位置的分数的计算方式是各个方向的分数相加，最后找出电脑棋型和玩家棋型的分数的最高的位置为电脑的下棋点下棋。
	 */
	static int evaluate(Point p, int color) {
		int value = 0;
		
		for (int i = 1; i <= 8; i++) { // 8个方向
			
			//冲五	1111*  111*1  11*11
			if ( (getLine(p, i, -1) == color
            	&&getLine(p, i, -2) == color
                &&getLine(p, i, -3) == color
                &&getLine(p, i, -4) == color) ||
	                
				 (getLine(p, i, +1) == color
				&&getLine(p, i, -1) == color
				&&getLine(p, i, -2) == color
				&&getLine(p, i, -3) == color) ||
                
				 (getLine(p, i, +2) == color
				&&getLine(p, i, +1) == color
				&&getLine(p, i, -1) == color
				&&getLine(p, i, -2) == color) )
	            {
					if(color==BLACK) {
						value += CHONGWU_PLAYER; 
					}
					if(color==WHITE) {
						value += CHONGWU_COMPUTER;
					}
					continue;
	            }    
			
			 //半活五		010111*0  01011*10  0101*110  010*1110  0*011110  01*01110  0*101110
            //			011011*0  01101*10  0110*110
            if ( (getLine(p, i, +1) == 0
					&&getLine(p, i, -1) == color
					&&getLine(p, i, -2) == color
	            	&& getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0
	                && getLine(p, i, -5) == color
	                && getLine(p, i, -6) == 0) ||
	                
	                (getLine(p, i, +2) == 0
	    			&&getLine(p, i, +1) == color
					&&getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == 0) ||
	                
	                (getLine(p, i, +3) == 0
	    	        && getLine(p, i, +2) == color
	            	&& getLine(p, i, +1) == color
	                && getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) ||
	                
	                (getLine(p, i, +4) == 0
	    	        && getLine(p, i, +3) == color
	            	&& getLine(p, i, +2) == color
	                && getLine(p, i, +1) == color
	                && getLine(p, i, -1) == 0
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0) ||
	                
	                (getLine(p, i, +5) == 0
	    	        && getLine(p, i, +4) == color
	            	&& getLine(p, i, +3) == color
	                && getLine(p, i, +2) == color
	                && getLine(p, i, +1) == 0
	                && getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0) ||
	                
	                (getLine(p, i, +6) == 0
	    	        && getLine(p, i, +5) == color
	            	&& getLine(p, i, +4) == color
	                && getLine(p, i, +3) == color
	                && getLine(p, i, +2) == 0
	                && getLine(p, i, +1) == color
	                && getLine(p, i, -1) == 0) ||
	         //---------------------------------------
	                (getLine(p, i, +1) == 0
	    	        && getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == color
	                && getLine(p, i, -6) == 0) ||
	                
	                (getLine(p, i, +2) == 0
	    	        && getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == 0) ||
	                
	                (getLine(p, i, +3) == 0
	    	        && getLine(p, i, +2) == color
	            	&& getLine(p, i, +1) == color
	                && getLine(p, i, -1) == 0
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) )
		            {
						if(color==BLACK) {
							value += BANHUOWU_PLAYER; 
						}
						if(color==WHITE) {
							value += BANHUOWU_COMPUTER;
						}
						continue;
		            } 
            
          //半死五		010111*#.  01011*1#.  0101*11#.  010*111#.  01*0111#.  0*10111#.
            //			011011*#.  01101*1#.  0110*11#.  011*011#.  01*1011#.  0*11011#.
            //			011101*#.  01110*1#.  0111*01#.  011*101#.  01*1101#.  0*11101#.
            //			011110*#.  0*01111#.
            if ( ((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0
	                && getLine(p, i, -5) == color
	                && getLine(p, i, -6) == 0) ||
	                
	             ((getLine(p, i, +2) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == color
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == 0) ||
	                
	             ((getLine(p, i, +3) != color && getLine(p, i, +1) != 0)
            		&& getLine(p, i, +2) == color
            		&& getLine(p, i, +1) == color
            		&& getLine(p, i, -1) == color
            		&& getLine(p, i, -2) == 0
            		&& getLine(p, i, -3) == color
            		&& getLine(p, i, -4) == 0) ||
	                
	             ((getLine(p, i, +4) != color && getLine(p, i, +1) != 0)
            		&& getLine(p, i, +3) == color
            		&& getLine(p, i, +2) == color
            		&& getLine(p, i, +1) == color
            		&& getLine(p, i, -1) == 0
            		&& getLine(p, i, -2) == color
            		&& getLine(p, i, -3) == 0) ||
	                
	             ((getLine(p, i, +5) != color && getLine(p, i, +1) != 0)
            		&& getLine(p, i, +4) == color
            		&& getLine(p, i, +3) == color
            		&& getLine(p, i, +2) == color
            		&& getLine(p, i, +1) == 0
            		&& getLine(p, i, -1) == color
            		&& getLine(p, i, -2) == 0) ||
	                
	             ((getLine(p, i, +6) != color && getLine(p, i, +1) != 0)
            		&& getLine(p, i, +5) == color
            		&& getLine(p, i, +4) == color
            		&& getLine(p, i, +3) == color
            		&& getLine(p, i, +2) == 0
            		&& getLine(p, i, +1) == color
            		&& getLine(p, i, -1) == 0) ||
	          //---------------------------------------------------------------
              //    011011*#  01101*1#  0110*11#  011*011#  01*1011#  0*11011#
        			((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == color
	                && getLine(p, i, -6) == 0) ||
        	                
        	        ((getLine(p, i, +2) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == 0) ||
	                
	             ((getLine(p, i, +3) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +2) == color
	            	&& getLine(p, i, +1) == color
	                && getLine(p, i, -1) == 0
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) ||
	                
	             ((getLine(p, i, +4) != color && getLine(p, i, +1) != 0)
            		&& getLine(p, i, +3) == color
            		&& getLine(p, i, +2) == color
            		&& getLine(p, i, +1) == 0
            		&& getLine(p, i, -1) == color
            		&& getLine(p, i, -2) == color
            		&& getLine(p, i, -3) == 0) ||
	                
	             ((getLine(p, i, +5) != color && getLine(p, i, +1) != 0)
            		&& getLine(p, i, +4) == color
            		&& getLine(p, i, +3) == color
            		&& getLine(p, i, +2) == 0
            		&& getLine(p, i, +1) == color
            		&& getLine(p, i, -1) == color
            		&& getLine(p, i, -2) == 0) ||
	                
	             ((getLine(p, i, +6) != color && getLine(p, i, +1) != 0)
            		&& getLine(p, i, +5) == color
            		&& getLine(p, i, +4) == color
            		&& getLine(p, i, +3) == 0
            		&& getLine(p, i, +2) == color
            		&& getLine(p, i, +1) == color
            		&& getLine(p, i, -1) == 0) ||
            	//--------------------------------------------------------
            	//   011101*#  01110*1#  0111*01#  011*101#  01*1101#  0*11101#
    				((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == 0
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == color
	                && getLine(p, i, -6) == 0) ||
	                
        	        ((getLine(p, i, +2) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == 0
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == 0) ||
	                
	             ((getLine(p, i, +3) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +2) == color
	            	&& getLine(p, i, +1) == 0
	                && getLine(p, i, -1) == color
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) ||
	                
	             ((getLine(p, i, +4) != color && getLine(p, i, +1) != 0)
            		&& getLine(p, i, +3) == color
            		&& getLine(p, i, +2) == 0
            		&& getLine(p, i, +1) == color
            		&& getLine(p, i, -1) == color
            		&& getLine(p, i, -2) == color
            		&& getLine(p, i, -3) == 0) ||
	                
	             ((getLine(p, i, +5) != color && getLine(p, i, +1) != 0)
            		&& getLine(p, i, +4) == color
            		&& getLine(p, i, +3) == 0
            		&& getLine(p, i, +2) == color
            		&& getLine(p, i, +1) == color
            		&& getLine(p, i, -1) == color
            		&& getLine(p, i, -2) == 0) ||
	                
	             ((getLine(p, i, +6) != color && getLine(p, i, +1) != 0)
            		&& getLine(p, i, +5) == color
            		&& getLine(p, i, +4) == 0
            		&& getLine(p, i, +3) == color
            		&& getLine(p, i, +2) == color
            		&& getLine(p, i, +1) == color
            		&& getLine(p, i, -1) == 0) ||
	                //0*01111#
	             ((getLine(p, i, +6) != color && getLine(p, i, +1) != 0)
            		&& getLine(p, i, +5) == color
            		&& getLine(p, i, +4) == color
            		&& getLine(p, i, +3) == color
            		&& getLine(p, i, +2) == color
            		&& getLine(p, i, +1) == 0
            		&& getLine(p, i, -1) == 0) )
	            {
					if(color==BLACK) {
						value += BANSIWU_PLAYER; 
					}
					if(color==WHITE) {
						value += BANSIWU_COMPUTER;
					}
					continue;
	            }
			
            // 活四       0111*0  011*10
            if ( (getLine(p, i, +1) == 0
            	&&getLine(p, i, -1) == color
            	&&getLine(p, i, -2) == color
                &&getLine(p, i, -3) == color
                &&getLine(p, i, -4) == 0) ||
                
                 (getLine(p, i, +2) == 0
            	&&getLine(p, i, +1) == color
            	&&getLine(p, i, -1) == color
                &&getLine(p, i, -2) == color
                &&getLine(p, i, -3) == 0) )
	            {
	            	if(color==BLACK) {
						value += HUOSI_PLAYER; 
					}
	            	if(color==WHITE){
						value += HUOSI_COMPUTER;
					}
	            	continue;
	            }
            
          //半活四		01011*0  0101*10  010*110 
            //			01*0110  0*10110
            //			01110*0
            if ( (getLine(p, i, +1) == 0
	            	&& getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == 0) ||
	                
	                (getLine(p, i, +2) == 0
	    	        && getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) ||
	                
	                (getLine(p, i, +3) == 0
	    	        && getLine(p, i, +2) == color
	            	&& getLine(p, i, +1) == color
	                && getLine(p, i, -1) == 0
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0) ||
	                
	                (getLine(p, i, +4) == 0
	    	        && getLine(p, i, +3) == color
	            	&& getLine(p, i, +2) == color
	                && getLine(p, i, +1) == 0
	                && getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0) ||
	                
	                (getLine(p, i, +5) == 0
	    	        && getLine(p, i, +4) == color
	            	&& getLine(p, i, +3) == color
	                && getLine(p, i, +2) == 0
	                && getLine(p, i, +1) == color
	                && getLine(p, i, +2) == 0) ||
	                
	                (getLine(p, i, +1) == 0
	    	        && getLine(p, i, -1) == 0
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == 0) )
		            {
						if(color==BLACK) {
							value += BANHUOSI_PLAYER; 
						}
						if(color==WHITE) {
							value += BANHUOSI_COMPUTER;
						}
						continue;
		            } 
            
          //死四		0111*#  011*1#  01*11#  0*111#
            if ( ((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) ||
	              
	              ((getLine(p, i, +2) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == color
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0) ||
		              
	              ((getLine(p, i, +3) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +2) == color
	            	&& getLine(p, i, +1) == color
	                && getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0) ||
		              
	              ((getLine(p, i, +4) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +3) == color
	            	&& getLine(p, i, +2) == color
	                && getLine(p, i, +1) == color
	                && getLine(p, i, -1) == 0) )
	            {
					if(color==BLACK) {
						value += SISI_PLAYER; 
					}
					if(color==WHITE) {
						value += SISI_COMPUTER;
					}
					continue;
	            }
            
          //半死四		01011*#.  0101*1#.  010*11#.  01*011#.  0*1011#.
            //			01101*#.  0110*1#.  011*01#.  01*101#.  0*1101#.
            //			01110*#.  0*0111#.
            if ( ((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == 0) ||
	                
            	((getLine(p, i, +2) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) ||
	                
            	((getLine(p, i, +3) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +2) == color
	            	&& getLine(p, i, +1) == color
	                && getLine(p, i, -1) == 0
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0) ||
	                
            	((getLine(p, i, +4) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +3) == color
	            	&& getLine(p, i, +2) == color
	                && getLine(p, i, +1) == 0
	                && getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0) ||
	                
            	((getLine(p, i, +5) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +4) == color
	            	&& getLine(p, i, +3) == color
	                && getLine(p, i, +2) == 0
	                && getLine(p, i, +1) == color
	                && getLine(p, i, -1) == 0) ||
	          //----------------------------------------------------------
	          //	01101*#  0110*1#  011*01#  01*101#  0*1101#
            	((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == 0
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == 0) ||
	                
            	((getLine(p, i, +2) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == 0
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) ||
	                
            	((getLine(p, i, +3) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +2) == color
	            	&& getLine(p, i, +1) == 0
	                && getLine(p, i, -1) == color
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0) ||
	                
            	((getLine(p, i, +4) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +3) == color
	            	&& getLine(p, i, +2) == 0
	                && getLine(p, i, +1) == color
	                && getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0) ||
	                
            	((getLine(p, i, +5) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +4) == color
	            	&& getLine(p, i, +3) == 0
	                && getLine(p, i, +2) == color
	                && getLine(p, i, +1) == color
	                && getLine(p, i, -1) == 0) ||
	         //	  01110*#
            	((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == 0
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == 0) ||
	                //0*0111#
	            ((getLine(p, i, +5) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +4) == color
	            	&& getLine(p, i, +3) == color
	                && getLine(p, i, +2) == color
	                && getLine(p, i, +1) == 0
	                && getLine(p, i, -1) == 0)
            		)
	            {
					if(color==BLACK) {
						value += BANSISI_PLAYER; 
					}
					if(color==WHITE) {
						value += BANSISI_COMPUTER;
					}
					continue;
	            }
            
            // 活三       011*0   01*10
            if ( (getLine(p, i, +1) == 0
               && getLine(p, i, -1) == color
               && getLine(p, i, -2) == color
               && getLine(p, i, -3) == 0) ||
                    
                 (getLine(p, i, +2) == 0
    		   && getLine(p, i, +1) == color
        	   && getLine(p, i, -1) == color
               && getLine(p, i, -2) == 0) )
                {
	            	if(color==BLACK) {
						value += HUOSAN_PLAYER; 
					}
	            	if(color==WHITE){
						value += HUOSAN_COMPUTER;
					}
	            	continue;
                }
            
         // 大活三       0011*00   001*100
            if ( (getLine(p, i, +2) == 0
               && getLine(p, i, +1) == 0
               && getLine(p, i, -1) == color
               && getLine(p, i, -2) == color
               && getLine(p, i, -3) == 0
               && getLine(p, i, -4) == 0) ||
                    
                 (getLine(p, i, +3) == 0
               && getLine(p, i, +2) == 0
    		   && getLine(p, i, +1) == color
        	   && getLine(p, i, -1) == color
               && getLine(p, i, -2) == 0
               && getLine(p, i, -3) == 0) )
                {
	            	if(color==BLACK) {
						value += DAHUOSAN_PLAYER; 
					}
	            	if(color==WHITE){
						value += DAHUOSAN_COMPUTER;
					}
	            	continue;
                }
            
          //半活三		0101*0  010*10
            //			0110*0
            if ( (getLine(p, i, +1) == 0
					&&getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == 0
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) ||
	                
	                (getLine(p, i, +2) == 0
					&&getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == 0
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0) ||
	                
	                (getLine(p, i, +1) == 0
					&&getLine(p, i, -1) == 0
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) )
		            {
						if(color==BLACK) {
							value += BANHUOSAN_PLAYER; 
						}
						if(color==WHITE) {
							value += BANHUOSAN_COMPUTER;
						}
						continue;
		            } 
            
          //死三		011*#  01*1#  0*11#
            if ( ((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0) ||
	       
                 ((getLine(p, i, +2) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0) ||
	     	       
                 ((getLine(p, i, +3) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +2) == color
	            	&& getLine(p, i, +1) == color
	                && getLine(p, i, -1) == 0) )
	            {
					if(color==BLACK) {
						value += SISAN_PLAYER; 
					}
					if(color==WHITE) {
						value += SISAN_COMPUTER;
					}
					continue;
	            }
            
          //半死三		0101*#.  010*1#.  01*01#.  0*101#.
            //			0110*#.  0*011#
            if ( ((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == 0
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) ||
	                
                 ((getLine(p, i, +2) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == 0
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0) ||
	                
            	((getLine(p, i, +3) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +2) == color
	            	&& getLine(p, i, +1) == 0
	                && getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0) ||
	                
            	((getLine(p, i, +4) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +3) == color
	            	&& getLine(p, i, +2) == 0
	                && getLine(p, i, +1) == color
	                && getLine(p, i, -1) == 0) ||
	          //----------------------------------------------------------
            	((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == 0
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) ||
	                //0*011#
            	((getLine(p, i, +4) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +3) == color
	            	&& getLine(p, i, +2) == color
	                && getLine(p, i, +1) == 0
	                && getLine(p, i, -1) == 0)
            		)
	            {
					if(color==BLACK) {
						value += BANSISAN_PLAYER; 
					}
					if(color==WHITE) {
						value += BANSISAN_COMPUTER;
					}
					continue;
	            }
            
         // 活二       0*10
            if ( (getLine(p, i, +2) == 0
               && getLine(p, i, +1) == color
               && getLine(p, i, -1) == 0) )
                {
	            	if(color==BLACK) {
						value += HUOER_PLAYER; 
					}
	            	if(color==WHITE){
						value += HUOER_COMPUTER;
					}
	            	continue;
                }
            
          //半活二		010*0
            if ( (getLine(p, i, +1) == 0
					&&getLine(p, i, -1) == 0
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0) )
		            {
						if(color==BLACK) {
							value += BANHUOER_PLAYER; 
						}
						if(color==WHITE) {
							value += BANHUOER_COMPUTER;
						}
						continue;
		            } 
            
          //死二		01*#  0*1#
            if ( ((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == 0) ||
	       
	             ((getLine(p, i, +2) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == 0) )
	            {
					if(color==BLACK) {
						value += SIER_PLAYER; 
					}
					if(color==WHITE) {
						value += SIER_COMPUTER;
					}
					continue;
	            }
            
          //半死二		010*#  
            //			#10*0
            if ( ((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == 0
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0) ||
	          //----------------------------------------------------------
	             (getLine(p, i, +1) == 0
					&& getLine(p, i, -1) == 0
	            	&& getLine(p, i, -2) == color
	                && (getLine(p, i, -3) != color && getLine(p, i, +1) != 0)) )
	            {
					if(color==BLACK) {
						value += BANSIER_PLAYER; 
					}
					if(color==WHITE) {
						value += BANSIER_COMPUTER;
					}
					continue;
	            }
            
/*           
          //活五	01111*0  0111*10  011*110	*代表当前空位置    0代表其他空位置
			if ( (getLine(p, i, +1) == 0
				&&getLine(p, i, -1) == color
            	&&getLine(p, i, -2) == color
                &&getLine(p, i, -3) == color
                &&getLine(p, i, -4) == color
                &&getLine(p, i, -5) == 0) ||
	                
				 (getLine(p, i, +2) == 0
				&&getLine(p, i, +1) == color
				&&getLine(p, i, -1) == color
				&&getLine(p, i, -2) == color
				&&getLine(p, i, -3) == color
				&&getLine(p, i, -4) == 0) ||
                
				 (getLine(p, i, +3) == 0
				&&getLine(p, i, +2) == color
				&&getLine(p, i, +1) == color
				&&getLine(p, i, -1) == color
				&&getLine(p, i, -2) == color
				&&getLine(p, i, -3) == 0) )
	            {
					if(color==BLACK) {
						value += HUOWU_PLAYER; 
					}
					if(color==WHITE) {
						value += HUOWU_COMPUTER;
					}
					continue;
	            }
*/
            
/*
            //死五		01111*#  0111*1#  011*11#  01*111#  0*1111#
            if ( ((getLine(p, i, +1) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, -1) == color
	            	&& getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == color
	                && getLine(p, i, -5) == 0) ||
	      
	                ((getLine(p, i, +2) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +1) == color
	            	&& getLine(p, i, -1) == color
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == color
	                && getLine(p, i, -4) == 0) ||
	      
	                ((getLine(p, i, +3) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +2) == color
	            	&& getLine(p, i, +1) == color
	                && getLine(p, i, -1) == color
	                && getLine(p, i, -2) == color
	                && getLine(p, i, -3) == 0) ||
	      
	                ((getLine(p, i, +4) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +3) == color
	            	&& getLine(p, i, +2) == color
	                && getLine(p, i, +1) == color
	                && getLine(p, i, -1) == color
	                && getLine(p, i, -2) == 0) ||
	      
	                ((getLine(p, i, +5) != color && getLine(p, i, +1) != 0)
					&& getLine(p, i, +4) == color
	            	&& getLine(p, i, +3) == color
	                && getLine(p, i, +2) == color
	                && getLine(p, i, +1) == color
	                && getLine(p, i, -1) == 0) )
	            {
					if(color==BLACK) {
						value += SIWU_PLAYER; 
					}
					if(color==WHITE) {
						value += SIWU_COMPUTER;
					}
					continue;
	            }
 */
            
		}
		return value;
	}
	
	//得到8个不同方向的棋子颜色
	static int getLine(Point p, int i, int j) { // p：当前点;   i：方向;   j：坐标相对值 
        int x = p.x, y = p.y;
        switch (i) {	//对8个方向的处理
           case 1 :
               x = x + j;
               break;
           case 2 :
               x = x + j;
               y = y + j;
               break;
           case 3 :
               y = y + j;
               break;
			case 4 :
               x = x - j;
               y = y + j;
               break;
			case 5 :
               x = x - j;
               break;
			case 6 :
               x = x - j;
               y = y - j;
               break;
			case 7 :
               y = y - j;
               break;
           case 8 :
               x = x + j;
               y = y - j;
       }
        
       if (x < 0 || y < 0 || x >= RJPanel.MAX_LINE || y >= RJPanel.MAX_LINE) { // 越界处理  返回-1
           return -1;
       }
       
       if(WuziqiUtil.blackArray.contains(new Point(x,y))) {
    	   return BLACK;
       } else if(WuziqiUtil.whiteArray.contains(new Point(x,y))) {
    	   return WHITE;
       } else {
    	   return 0;
       }
	}
	
}
