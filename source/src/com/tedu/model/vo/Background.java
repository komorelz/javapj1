package com.tedu.model.vo;

import com.tedu.manager.ElementFactory;

import javax.swing.*;
import java.awt.*;

public class Background extends SuperElement {
	private ImageIcon icon;
	private int index;
	@Override
	public void showElement(Graphics g) {
		// TODO 自动生成的方法存根
		g.drawImage(icon.getImage(), 0, 0, 480, 480, null);
	}

	@Override
	public void move() {
		// TODO 自动生成的方法存根

	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根

	}

	public Background() {
		super();
	}

	public Background(int x, int y, int w, int h, ImageIcon icon, int index) {
		super(x, y, w, h);
		this.icon = icon;
		this.index = index;
	}
	public static Background createBackground(int index) {
		ImageIcon icon = ElementFactory.ElementLoad.getInstance().getImageMap().get("bg"+index);
		return new Background(0, 0, 480, 480, icon,index);
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public boolean allowpass() {
		// TODO 自动生成的方法存根
		return true;
	}
	
}
