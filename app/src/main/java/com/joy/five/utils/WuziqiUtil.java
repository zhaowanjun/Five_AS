package com.joy.five.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

import com.joy.five.view.RRPanel;

public class WuziqiUtil {
	public static ArrayList<Point> whiteArray = new ArrayList<Point>();
	public static ArrayList<Point> blackArray = new ArrayList<Point>();
	public static Point sign = new Point();
	
	public static void whiteSign() {
		if(whiteArray.size()>0) {
			sign.set(whiteArray.get(whiteArray.size()-1).x,
					whiteArray.get(whiteArray.size()-1).y);
		}
	}
	public static void blackSign() {
		if(blackArray.size()>0) {
			sign.set(blackArray.get(blackArray.size()-1).x,
					blackArray.get(blackArray.size()-1).y);
		}
	}
	
	private static int MAX_COUNT_IN_LINE = 5;

	public static boolean checkFiveInLine(List<Point> points) {
		for (Point p : points) {
			int x = p.x;
			int y = p.y;

			boolean win = checkHorzontal(x, y, points);
			if (win)
				return true;
			win = checkVertical(x, y, points);
			if (win)
				return true;
			win = checkLeftDiagonal(x, y, points);
			if (win)
				return true;
			win = checkRightDiagonal(x, y, points);
			if (win)
				return true;
		}
		return false;
	}

	 /**
     * 判断棋盘是否已满
     */
    public static boolean checkIsFull(int number) {
        if(number== RRPanel.MAX_PIECES_NUMBER) {
            return true;
        }
        return false;
    }
	
	/**
	 * 判断横向
	 */
	private static boolean checkHorzontal(int x, int y, List<Point> points) {
		int count = 1;
		// 判断左边
		for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
			if (points.contains(new Point(x - i, y))) {
				count++;
			} else {
				break;
			}
		}
		if (count == MAX_COUNT_IN_LINE)
			return true;

		// 判断右边
		for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
			if (points.contains(new Point(x + i, y))) {
				count++;
			} else {
				break;
			}
		}
		if (count == MAX_COUNT_IN_LINE)
			return true;
		return false;
	}

	/**
	 * 判断纵向
	 */
	private static boolean checkVertical(int x, int y, List<Point> points) {
		int count = 1;
		// 判断上边
		for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
			if (points.contains(new Point(x, y - i))) {
				count++;
			} else {
				break;
			}
		}
		if (count == MAX_COUNT_IN_LINE)
			return true;

		// 判断下边
		for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
			if (points.contains(new Point(x, y + i))) {
				count++;
			} else {
				break;
			}
		}
		if (count == MAX_COUNT_IN_LINE)
			return true;
		return false;
	}

	/**
	 * 判断左斜
	 */
	private static boolean checkLeftDiagonal(int x, int y, List<Point> points) {
		int count = 1;
		// 判断右上
		for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
			if (points.contains(new Point(x + i, y - i))) {
				count++;
			} else {
				break;
			}
		}
		if (count == MAX_COUNT_IN_LINE)
			return true;

		// 判断左下
		for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
			if (points.contains(new Point(x - i, y + i))) {
				count++;
			} else {
				break;
			}
		}
		if (count == MAX_COUNT_IN_LINE)
			return true;
		return false;
	}

	/**
	 * 判断右斜
	 */
	private static boolean checkRightDiagonal(int x, int y, List<Point> points) {
		int count = 1;
		// 判断左上
		for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
			if (points.contains(new Point(x - i, y - i))) {
				count++;
			} else {
				break;
			}
		}
		if (count == MAX_COUNT_IN_LINE)
			return true;

		// 判断右下
		for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
			if (points.contains(new Point(x + i, y + i))) {
				count++;
			} else {
				break;
			}
		}
		if (count == MAX_COUNT_IN_LINE)
			return true;
		return false;
	}
}
