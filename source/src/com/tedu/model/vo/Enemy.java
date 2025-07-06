package com.tedu.model.vo;

import com.tedu.manager.ElementFactory;

import javax.swing.*;
import java.awt.*;

public class Enemy extends SuperElement {
	private ImageIcon imageIcon;
	private String name;
	public Enemy() {
		super();

	}

	public Enemy(int x, int y, int w, int h, ImageIcon icon) {
		super(x, y, w, h);
		this.imageIcon = icon;
	}
	public static Enemy createEnemy(String url){
		//enemyA,enemyA,20,170,40,40,10000
		String[] arr = url.split(",");
		int x = Integer.parseInt(arr[2]),
			y = Integer.parseInt(arr[3]),
			w = Integer.parseInt(arr[4]),
			h = Integer.parseInt(arr[5]);
		ImageIcon icon = ElementFactory.ElementLoad.getInstance().getImageMap().get(arr[0]);
		Enemy enemy = null;
		//在配置文件中可以加类型
		switch (arr[0]) {
		case "enemyA":
			enemy = new EnemyLeftToRight(x, y, w, h, icon);
			break;
		case "enemyB":
			enemy = new EnemyRightToLeft(x, y, w, h, icon);
			break;
		default:
			enemy = new Enemy(x, y, w, h, icon);
			break;
		}

		return enemy;
		//return new Enemy(x,y,w,h,icon);
	}
	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(getImageIcon().getImage(), getX(), getY(), getW(),getH(),null);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		setY(getY()+5);
		//到达边界销毁
		if (getY()>400) {
			setVisible(false);
		}
	}

	@Override
	public void destroy() {

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
		return true;
	}



}
