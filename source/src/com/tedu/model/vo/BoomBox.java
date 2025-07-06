package com.tedu.model.vo;

import com.tedu.manager.ElementFactory;
import com.tedu.manager.ElementManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class BoomBox extends Box {
	private int propType;//道具类型 0 无 其他有
	private Random random;
	public BoomBox() {
		// TODO 自动生成的构造函数存根
	}

	public BoomBox(int indexX, int indexY, ImageIcon icon, int propType) {
		super(indexX, indexY,icon);
		this.propType = propType;
		setObjectType(3);
//		setVisible(false);
		random = new Random();
		// TODO 自动生成的构造函数存根
	}
	public static BoomBox createBox(String str) {
		//boombox,imageA,0,0,1
		String[] arr = str.split(",");
		Integer x = Integer.parseInt(arr[2]);
		Integer y = Integer.parseInt(arr[3]);
		Integer type = Integer.parseInt(arr[4]);
		GridCell.setGridByIndex(y, x, 3);
		ImageIcon icon = ElementFactory.ElementLoad.getInstance().getImageMap().get(arr[1]);
		BoomBox boomBox =  new BoomBox(x,y,icon,type);

		return boomBox;
	}
	public void addProp(int type) {
		List<SuperElement> props = ElementManager.getInstance().getElementList("prop");
		Prop prop = Prop.createProp(getX(), getY(), type);
		ElementManager.getInstance().getElementList("prop").add(prop);
		//ElementManager.getInstance().setElementByPx(prop.getY(), prop.getX(), prop);
	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		//箱子销毁
		if (!isVisible()) {


//			GridCell.setGridByPx(getY(), getX(), 0);
			ElementManager.getInstance().removeElementByPx(getY(), getX());
			int type = random.nextInt(9);
//			addProp(5);
			if (type!=0) {
				addProp(type);
			}
		}
	}
	@Override
	public void showElement(Graphics g) {
		// TODO 自动生成的方法存根
		g.drawImage(getImageIcon().getImage(), getX(), getY(), getW(), getH(), null);
	}

	@Override
	public String toString() {
		return "BoomBox [propType=" + propType + ", getIndexX()=" + getIndexX() + ", getIndexY()=" + getIndexY()
				+ ", getImageIcon()=" + getImageIcon() + ", getShowY()=" + getShowY() + ", getShowX()=" + getShowX()
				+ ", getX()=" + getX() + ", getY()=" + getY() + ", isVisible()=" + isVisible() + ", getW()=" + getW()
				+ ", getH()=" + getH() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	@Override
	public boolean allowpass() {
		// TODO 自动生成的方法存根
		return false;
	}
}
