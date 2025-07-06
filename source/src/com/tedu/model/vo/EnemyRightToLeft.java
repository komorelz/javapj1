package com.tedu.model.vo;

import javax.swing.*;

public class EnemyRightToLeft extends Enemy {

	public EnemyRightToLeft() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnemyRightToLeft(int x, int y, int w, int h, ImageIcon icon) {
		super(x, y, w, h, icon);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub
		setX(getX()-5);
		setY(getY()+5);
		if (getX()<0) {
			setVisible(false);
		}
		if (getY()>400) {
			setVisible(false);
		}
	}
	
	
}
