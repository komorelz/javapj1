package com.tedu.model.vo;

import com.tedu.manager.ElementManager;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.List;

public class Bomb extends SuperElement{
	private ImageIcon imgR ;
	private ImageIcon imgL ;
	private ImageIcon imgT ;
	private ImageIcon imgD ;
	private ImageIcon imgC ;
	private ImageIcon imgR2 ;
	private ImageIcon imgL2 ;
	private ImageIcon imgT2 ;
	private ImageIcon imgD2 ;
	private int numR ;
	private int numL ;
	private int numT ;
	private int numD ;
	private Calendar cal;
	private boolean flag;
	private int id;
	public Bomb(int x, int y, int w, int h, ImageIcon imgC, ImageIcon imgR, ImageIcon imgL, ImageIcon imgT, ImageIcon imgD,
                ImageIcon imgR2, ImageIcon imgL2, ImageIcon imgT2, ImageIcon imgD2, int numR, int numL, int numT, int numD, int id) {
		super(x, y, w, h);
		this.imgC = imgC;
		this.imgR = imgR;
		this.imgL = imgL;
		this.imgT = imgT;
		this.imgD = imgD;
		this.imgR2 = imgR2;
		this.imgL2 = imgL2;
		this.imgT2 = imgT2;
		this.imgD2 = imgD2;

		this.numR = numR;
		this.numL = numL;
		this.numT = numT;
		this.numD = numD;
		flag = false;
		updateNum();		
		this.cal = Calendar.getInstance();
		this.id = id;
	}



	public static Bomb createBomb(int x,int y,int numR,int numL,int numT,int numD,int id) {


		int w = 40;
		int h = 40;
		String urlC = "img/bomb/C.png";
		String urlR = "img/bomb/R.png";
		String urlL = "img/bomb/L.png";
		String urlT = "img/bomb/T.png";
		String urlD = "img/bomb/D.png";
		String urlR2 = "img/bomb/R2.png";
		String urlL2 = "img/bomb/L2.png";
		String urlT2 = "img/bomb/T2.png";
		String urlD2 = "img/bomb/D2.png";

		ImageIcon imgR =new ImageIcon(urlR);
		ImageIcon imgL =new ImageIcon(urlL);
		ImageIcon imgT =new ImageIcon(urlT);
		ImageIcon imgD =new ImageIcon(urlD);
		ImageIcon imgR2 =new ImageIcon(urlR2);
		ImageIcon imgL2 =new ImageIcon(urlL2);
		ImageIcon imgT2 =new ImageIcon(urlT2);
		ImageIcon imgD2 =new ImageIcon(urlD2);
		ImageIcon imgC = new ImageIcon(urlC);

		return new Bomb(x,y,w,h, imgC,imgR,  imgL,  imgT,  imgD,
				imgR2,  imgL2,  imgT2,  imgD2,numR,numL,numT,numD,id);
	}
	@Override
	public void showElement(Graphics g) {

		g.drawImage(imgC.getImage(), getX(), getY(), getW(),getH(),null);
		for(int i=0;i<numR-1;i++) {
			g.drawImage(imgR2.getImage(), getX()+(i+1)*40, getY(), getW(), getH(), null);

		}
		for(int i=0;i<numL-1;i++) {
			g.drawImage(imgL2.getImage(), getX()-(i+1)*40, getY(), getW(), getH(), null);

		}
		for(int i=0;i<numT-1;i++) {
			g.drawImage(imgT2.getImage(), getX(), getY()-(i+1)*40, getW(), getH(), null);

		}
		for(int i=0;i<numD-1;i++) {
			g.drawImage(imgD2.getImage(), getX(), getY()+(i+1)*40, getW(), getH(), null);

		}
		g.drawImage(imgR.getImage(), getX()+numR*40,getY(),getW(),getH(), null);
		g.drawImage(imgL.getImage(), getX()-numL*40,getY(),getW(),getH(), null);
		g.drawImage(imgT.getImage(), getX(),getY()-numT*40,getW(),getH(), null);
		g.drawImage(imgD.getImage(), getX(),getY()+numD*40,getW(),getH(), null);		
	}


	public void ruin() {
		killPlayer();
		System.out.println("左："+numL+"   右："+numR);
		List<SuperElement> players = ElementManager.getInstance().getElementList("play");
		Player player = (Player)players.get(id);  // 通过id获取对应的玩家

		SuperElement s = ElementManager.getInstance().getElementByPx(getY(), getX());
		if(s!=null) {
			s.setVisible(false);
			ElementManager.getInstance().removeElementByPx(getY(), getX());
			player.addNum(50);
		}

		for(int i=0;i<numR;i++) {
			SuperElement se = ElementManager.getInstance().getElementByPx(getY(), getX()+(i+1)*40);
			if(se!=null) {
				se.setVisible(false);
				ElementManager.getInstance().removeElementByPx(getY(), getX()+(i+1)*40);
				player.addNum(50);
			}
		}
		for(int i=0;i<numL;i++) {
			SuperElement se = ElementManager.getInstance().getElementByPx(getY(), getX()-(i+1)*40);

			if(se!=null) {
				se.setVisible(false);
				ElementManager.getInstance().removeElementByPx(getY(), getX()-(i+1)*40);
				player.addNum(50);
			}
		}
		for(int i=0;i<numT;i++) {
			SuperElement se = ElementManager.getInstance().getElementByPx(getY()-(i+1)*40, getX());
			if(se!=null) {
				se.setVisible(false);
				ElementManager.getInstance().removeElementByPx(getY()-(i+1)*40, getX());
				player.addNum(50);
			}
		}
		for(int i=0;i<numD;i++) {
			SuperElement se = ElementManager.getInstance().getElementByPx(getY()+(i+1)*40, getX());
			if(se!=null) {
				se.setVisible(false);
				ElementManager.getInstance().removeElementByPx(getY()+(i+1)*40, getX());
				player.addNum(50);
			}
		}


	}
	@Override
	public void destroy() {
		if (!isVisible()) {
			GridCell.setGridByPx(getY(), getX(), 0);
			ElementManager.getInstance().removeElementByPx(getY(), getX());
		}
	}

	@Override
	public void move() {
		// TODO 自动生成的方法存根

	}
	public void update() {
		super.update();
		updateTime();

		ruin();
	}
	public void updateImg() {

	}
	public void updateNum() {
		int r =0;
		for(;r<this.numR&&ElementManager.getInstance().isBomb(getY(),getX()+40*(r+1));r++)
			if(ElementManager.getInstance().isBoomBox(getY(),getX()+40*(r+1))) {
				r++;
				break;
			}
		int l =0;
		for(;l<this.numL&&ElementManager.getInstance().isBomb(getY(),getX()-40*(l+1));l++)
			if(ElementManager.getInstance().isBoomBox(getY(),getX()-40*(l+1))) {
				l++;
				break;
			}
		int t =0;
		for(;t<this.numT&&ElementManager.getInstance().isBomb(getY()-40*(t+1),getX());t++)
			if(ElementManager.getInstance().isBoomBox(getY()-40*(t+1),getX())) {
				t++;
				break;
			}
		int d =0;
		for(;d<this.numD&&ElementManager.getInstance().isBomb(getY()+40*(d+1),getX());d++)
			if(ElementManager.getInstance().isBoomBox(getY()+40*(d+1),getX())) {
				d++;
				break;
			}

		this.numR = r;
		this.numL = l;
		this.numT = t;
		this.numD = d;
	}


	public ImageIcon getImgR() {
		return imgR;
	}

	public void setImgR(ImageIcon imgR) {
		this.imgR = imgR;
	}

	public ImageIcon getImgL() {
		return imgL;
	}

	public void setImgL(ImageIcon imgL) {
		this.imgL = imgL;
	}

	public ImageIcon getImgT() {
		return imgT;
	}

	public void setImgT(ImageIcon imgT) {
		this.imgT = imgT;
	}

	public ImageIcon getImgD() {
		return imgD;
	}

	public void setImgD(ImageIcon imgD) {
		this.imgD = imgD;
	}

	public ImageIcon getImgC() {
		return imgC;
	}

	public void setImgC(ImageIcon imgC) {
		this.imgC = imgC;
	}

	public ImageIcon getImgR2() {
		return imgR2;
	}

	public void setImgR2(ImageIcon imgR2) {
		this.imgR2 = imgR2;
	}

	public ImageIcon getImgL2() {
		return imgL2;
	}

	public void setImgL2(ImageIcon imgL2) {
		this.imgL2 = imgL2;
	}

	public ImageIcon getImgT2() {
		return imgT2;
	}

	public void setImgT2(ImageIcon imgT2) {
		this.imgT2 = imgT2;
	}

	public ImageIcon getImgD2() {
		return imgD2;
	}

	public void setImgD2(ImageIcon imgD2) {
		this.imgD2 = imgD2;
	}

	public int getNumR() {
		return numR;
	}

	public void setNumR(int numR) {
		this.numR = numR;
	}

	public int getNumL() {
		return numL;
	}

	public void setNumL(int numL) {
		this.numL = numL;
	}

	public int getNumT() {
		return numT;
	}

	public void setNumT(int numT) {
		this.numT = numT;
	}

	public int getNumD() {
		return numD;
	}

	public void setNumD(int numD) {
		this.numD = numD;
	}
	public void killPlayer1() {
		List<SuperElement> list =
				ElementManager.getInstance().getElementList("play");
		if(list.size()==0) return ;
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getX()>=this.getX()-40*numL
					&&list.get(i).getX()<=this.getX()+40*(numR+1)
					&&list.get(i).getY()>=this.getY()-40*(numT)
					&&list.get(i).getY()<=this.getY()+40*(numD+1)) {
				Player player = (Player)list.get(i);
//				player.bombByBubble();

			}
		}
	}
	public void killPlayer() {
		if(flag) return ;
		List<SuperElement> list = ElementManager.getInstance().getElementList("play");
		for(int i=0;i<2;i++) {
			Player player = (Player)list.get(i);
			Rectangle rectangle = new Rectangle(getX()-40*numL,getY(),40*(numL+1+numR),40);
			Rectangle playerRec = new Rectangle(player.getX(),player.getY(),40,40);
			if (playerRec.intersects(rectangle)) {
				player.bombByBubble(this.id);
				continue;
			}
			rectangle = new Rectangle(getX(),getY()-numT*40,40,40*(numD+numT));
			if (playerRec.intersects(rectangle)) {
				player.bombByBubble(this.id);
			}
		}
		flag =true;
		//		List<SuperElement> list =
		//				ElementManager.getInstance().getElementList("play");
		//		if(list.size()==0) return ;
		//		for(int i=0;i<list.size();i++) {
		//			if(     list.get(i).getX()>=this.getX()-40*numL
		//					&&list.get(i).getX()<this.getX()
		//					&&list.get(i).getY()>=this.getY()
		//					&&list.get(i).getY()<this.getY()+40
		//
		//					||list.get(i).getX()>=this.getX()
		//					&&list.get(i).getX()<this.getX()+40
		//					&&list.get(i).getY()>=this.getY()-40*(numT)
		//					&&list.get(i).getY()<=this.getY()+40*numD
		//
		//					||list.get(i).getX()>=this.getX()+40
		//					&&list.get(i).getX()<=this.getX()+numR*40
		//					&&list.get(i).getY()>=this.getY()
		//					&&list.get(i).getY()<this.getY()+40) {
		////				list.get(i).setVisible(false);
		//				Player player = (Player)list.get(i);
		//				player.bombByBubble();
		//			}
		//		}
	}

	@Override
	public boolean allowpass() {
		// TODO 自动生成的方法存根
		return true;
	}
	public void updateTime() {
		Calendar calendar = Calendar.getInstance();
		if (calendar.getTime().getTime()-500>this.cal.getTime().getTime()) {
			setVisible(false);
		}
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	@Override
	public String toString() {
		return "Bomb [id=" + id + "]";
	}

}
