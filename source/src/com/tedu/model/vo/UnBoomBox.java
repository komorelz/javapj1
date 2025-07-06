package com.tedu.model.vo;

import com.tedu.manager.ElementFactory;

import javax.swing.*;
import java.awt.*;

public class UnBoomBox extends Box {

	public UnBoomBox() {
		super();
	}

	public UnBoomBox(int indexX, int indexY, ImageIcon icon) {
		super(indexX, indexY, icon);
		setObjectType(4);
	}
	@Override
	public void showElement(Graphics g) {
		// TODO 自动生成的方法存根
		g.drawImage(getImageIcon().getImage(), getX(), getY(), getW(), getH(), null);
	}
	public static UnBoomBox createBox(String str) {
		//unboombox,imageA,0,0,1
		String[] arr = str.split(",");
		Integer x = Integer.parseInt(arr[2]);
		Integer y = Integer.parseInt(arr[3]);
		GridCell.setGridByIndex(y, x, 4);
		ImageIcon icon = ElementFactory.ElementLoad.getInstance().getImageMap().get(arr[1]);
		UnBoomBox unboomBox =  new UnBoomBox(x,y,icon);
		return unboomBox;
	}
	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		if (!isVisible()) {
			GridCell.setGridByPx(getY(), getX(), 0);
		}
	}
}
