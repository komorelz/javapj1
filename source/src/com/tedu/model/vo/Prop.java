package com.tedu.model.vo;

import com.tedu.manager.ElementFactory;

import javax.swing.*;
import java.awt.*;

public class Prop extends SuperElement {
	private ImageIcon icon;
	private int propClass;//道具类型
	private int moveX;
	public Prop() {
		// TODO 自动生成的构造函数存根
	}

	public Prop(int x, int y, int w, int h, ImageIcon icon, int type) {
		super(x, y, w, h);
		this.icon = icon;
		this.propClass = type;
		setObjectType(2);
	}
	public static Prop createProp(int x,int y,int type) {
		String url = "imageProp"+type;
		ImageIcon icon = ElementFactory.ElementLoad.getInstance().getImageMap().get(url);
		Prop prop = new Prop(x, y, 40, 40, icon, type);
//		ElementManager.getInstance().setElementByPx(y, x, prop);
	//	System.out.println("set constructor prop"+prop.getPropClass()+"y= "+y+"x= "+x);
		return prop;
	}
	@Override
	public void showElement(Graphics g) {
		// TODO 自动生成的方法存根
		//g.drawImage(icon.getImage(), getX(), getY(), getW(), getH(), null);
		g.drawImage(icon.getImage(), getX(), getY(), getX()+getW(), getY()+getH(),
				0+32*moveX, 0,
				32+32*moveX, 50,
				null);
		//0 0 32 50
		// 32 0 64 50
	}

	@Override
	public void move() {
		moveX = (++moveX)%4;
	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根

	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public int getPropClass() {
		return propClass;
	}

	public void setPropClass(int type) {
		this.propClass = type;
	}
	@Override
	public boolean allowpass() {
		// TODO 自动生成的方法存根
		return true;
	}
}
