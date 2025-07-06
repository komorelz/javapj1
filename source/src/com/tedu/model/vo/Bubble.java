package com.tedu.model.vo;

import com.tedu.manager.ElementManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Bubble extends SuperElement {
	private ImageIcon img;
	private int moveX;
	private int id;
	private Calendar cal;
	private int power;
	public int getOwnerId() {
		return this.id; // 使用现有的 id 字段
	}
	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(img.getImage(),
				getX(), getY(), //屏幕左上角坐标
				getX()+getW(), getY()+getH(), //屏幕右下角坐标
				0+32*moveX,6, //图片左上角坐标      60 0
				32+32*(moveX),46, //图片右下角坐标  120 60

				null);
		// 0 6  32  46
		// 32 6  64   46


//		15 20 55 60
//		85 20 125 60
//		155 20 195 60
	}
	public static Bubble createBubble(int x,int y,ImageIcon icon,int id,int power){

		return new Bubble(x, y, 40, 40, icon,id,power);


	}
	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		if (!isVisible()) {


			new Thread() {
				public void run() {
					new audioPlay(Audio.BOOM).player();
				}
			}.start();

			GridCell.setGridByPx(getY(), getX(), 0);
			ElementManager.getInstance().removeElementByPx(getY(), getX());

			Player player = (Player)ElementManager.getInstance().getElementList("play").get(id);
			int current = player.getCurrentBubble();
			player.setCurrentBubble(current-1);


			List<SuperElement> list = ElementManager.getInstance().getElementList("bomb");
			if (list == null) {
				list = new ArrayList<SuperElement>();
			}
			Bomb bomb = Bomb.createBomb(getX(), getY(),power,power,power,power,id);
			list.add(bomb);
			System.out.println("add bomb"+bomb);
			ElementManager.getInstance().setElementByPx(getY(), getX(), bomb);
		}
	}

	public ImageIcon getImg() {
		return img;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}

	public Bubble() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bubble(int x, int y, int w, int h, ImageIcon icon, int id, int power) {
		super(x, y, w, h);
		this.img = icon;
		setObjectType(5);
		this.cal = Calendar.getInstance();
		this.id = id;
		this.power = power;
//		GridCell.setGridByPx(getY(), getX(), 5);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
		updateTime();
		updateImage();
	}
	public void updateTime() {
		Calendar cal = Calendar.getInstance();
		if(cal.getTime().getTime()-this.cal.getTime().getTime()>2000) {
			this.setVisible(false);
			this.destroy();
		}
	}
	private void updateImage() {
		// TODO Auto-generated method stub
		moveX = (++moveX)%4;
	}

	@Override
	public boolean allowpass() {
		// TODO 自动生成的方法存根
		return false;
	}

}
