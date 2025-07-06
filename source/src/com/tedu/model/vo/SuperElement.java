package com.tedu.model.vo;

import java.awt.*;


/*
 * 新建类 继承父类 重写方法 添加到map中的list
 */
public abstract class SuperElement {

	private int x,y;// x , y 画图片的左上角x y
	private int w,h;// 宽 ,高
	private boolean visible;//默认为true 代表存活
	//物体类型 0 没有 1人 2道具 3爆炸箱子 4不可爆炸箱子  5泡泡
	private int objectType;



	// 方法可以有顺序执行 模板模式
	// 父类的引用指向子类的实体对象
	//子类可以重写父类的模板方法
	public void update(){
		move();
		destroy();
	}
	public abstract void showElement(Graphics g);
	public abstract void move();
	public abstract void destroy();

	public abstract boolean allowpass();

	//默认有一个无参构造
	//但如果手写了一个构造方法 则不会有默认的无参构造
	// 父类最好写一个无参构造

	public SuperElement(){
		this.visible = true;
	}

	public SuperElement(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.visible = true;
		this.objectType = 0;
	}
	public boolean gamePK(SuperElement superElement){
		Rectangle r1 = new Rectangle(x, y, w, h);
		Rectangle r2 = new Rectangle(superElement.x, superElement.y, superElement.w, superElement.h);
		//如果矩形有交集 返回true
		return r1.intersects(r2);

	}
	public static boolean gamePK(SuperElement se1,SuperElement se2){
		Rectangle r1 = new Rectangle(se1.x, se1.y, se1.w, se1.h);
		Rectangle r2 = new Rectangle(se2.x, se2.y, se2.w, se2.h);
		//如果矩形有交集 返回true
		return r1.intersects(r2);

	}
	//修改的显示点 默认是标准点 子类重写
	public int getShowY(){
		return getY();
	}
	public int getShowX(){
		return getX();
	}

	//标准点
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getObjectType() {
		return objectType;
	}
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

}
