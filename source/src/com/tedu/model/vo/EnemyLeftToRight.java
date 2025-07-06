package com.tedu.model.vo;

import javax.swing.*;
import java.awt.*;

public class EnemyLeftToRight  extends Enemy{

	public EnemyLeftToRight() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnemyLeftToRight(int x, int y, int w, int h, ImageIcon icon) {
		super(x, y, w, h, icon);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub
		setX(getX()+5);
		if (getX()>300) {
			setVisible(false);
		}
	}
	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		super.showElement(g);
	}
	
}
