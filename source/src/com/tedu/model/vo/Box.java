package com.tedu.model.vo;

import javax.swing.*;
import java.awt.*;


public class Box extends SuperElement {
	private int indexX,indexY;
	private ImageIcon imageIcon;
	
	public Box() {
		// TODO 自动生成的构造函数存根
	}



	public Box(int indexX, int indexY, ImageIcon icon) {
		super();
		this.indexX = indexX;
		this.indexY = indexY;
		this.imageIcon = icon;

		setX(indexX*40);
		setY(indexY*40);
		setW(40);
		setH(40);
	}


	@Override
	public void showElement(Graphics g) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void move() {
		// TODO 自动生成的方法存根

	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根

	}



	public int getIndexX() {
		return indexX;
	}



	public void setIndexX(int indexX) {
		this.indexX = indexX;
	}



	public int getIndexY() {
		return indexY;
	}



	public void setIndexY(int indexY) {
		this.indexY = indexY;
	}



	public ImageIcon getImageIcon() {
		return imageIcon;
	}



	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}
	@Override
	public boolean allowpass() {
		// TODO 自动生成的方法存根
		return false;
	}
}
