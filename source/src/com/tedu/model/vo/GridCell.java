package com.tedu.model.vo;

import java.awt.Rectangle;

public class GridCell {
	// 0 没有 1人 2道具 3爆炸箱子 4不可爆炸箱子  5泡泡
	public static int[][] grid = new int[12][12];
	static {
		int i,j;
		for (i = 0; i < grid.length; i++) {
			for(j = 0;j <grid[i].length; j++) {
				grid[i][j]= 0; 
			}
		}
	}
	
	
	public static int getTypeByIndex(int row,int col) {
		return grid[row][col];
	}
	public static int getTypeByPx(int row,int col) {
		return grid[row/40][col/40];
	} 
	public static boolean isAllowByIndex(int row,int col) {
		int type = getTypeByIndex(row, col);
		return type<=2;
	}
	public static boolean isAllowByPx(int row,int col) {
		int type = getTypeByPx(row, col);
		return type<=2;
	}
	public static void setGridByPx(int row,int col,int type) {
		grid[row/40][col/40] = type;
	}
	public static void setGridByIndex(int row,int col,int type) {
		grid[row][col] = type;
	}
	
	public static void cout() {
		int i,j;
		for (i = 0; i < 12; i++) {
			for(j = 0;j <12; j++) {
				System.out.print(" "+grid[i][j]);
			}
			System.out.println();
		}
	}
}
