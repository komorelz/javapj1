package com.tedu.controller;

import com.tedu.manager.ElementManager;
import com.tedu.model.vo.Audio;
import com.tedu.model.vo.Player;
import com.tedu.model.vo.SuperElement;
import com.tedu.model.vo.audioPlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

// java���̳� ��ʵ�� ͨ���ڲ����ֲ����̳е�ȱ��

public class GameThread extends Thread{
	//计时数据
	private static int time ;
	private boolean flag=true;
	//重构老项目
	@Override
	public void run() {
		//这个循环控制游戏整体进度
//		while(flag){
			// 死循环 状态变量进行控制
			//1.加载地图 人物
			loadElement();
			//2.显示地图人物（流程 自动化（移动，碰撞））
			time = 0;
			loadBGM();
			runGame();
			//3.结束地图



			try {
				TimeUnit.MILLISECONDS.sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		}
	}
	//控制进度 但是作为控制 请不要接触load 只能通过元素管理器访问元素
	public void loadElement(){
		ElementManager.getInstance().load();
	}
	public void runGame(){
		//这个循环控制每个关卡 地图中玩的状态
		ElementManager manager = ElementManager.getInstance();
		while(flag){

			Map<String, List<SuperElement> > map = manager.getMap();
			Set<String> set = map.keySet();
			List<String> temp = new ArrayList<>();
			temp.addAll(set);
			//迭代器在遍历的过程中，迭代器中的元素不可以变化(增加或减少)
			for (int i=temp.size()-1; i>=0 ; i--) {
				List<SuperElement> list = map.get(temp.get(i));
				for (int j = 0; j < list.size(); j++) {
					SuperElement superElement = list.get(j);
					superElement.update();
					if (!superElement.isVisible()) {
						manager.removeElementByPx(superElement.getY(), superElement.getX());
						list.remove(j);

					}
				}
			}
			//使用一个独立的方法来进行判定
			PK();

			//游戏的流程控制
			linkGame();


			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//死亡 通关状态 结束runGame方法
			overGame();


			time++; //一秒钟增加10
		}
	}

	public void PK() {
		// TODO Auto-generated method stub
		List<SuperElement> players = ElementManager.getInstance().getElementList("play");
		List<SuperElement> enemys = ElementManager.getInstance().getElementList("enemylist");
		//进行比较
		listPK(players, enemys);
	}
	public void listPK(List<SuperElement> list1,List<SuperElement> list2){
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				if (list1.get(i).gamePK(list2.get(j))) {
					list2.get(j).setVisible(false);

				}
			}
		}
	}
	public void overGame(){
		Player player1 = (Player)(ElementManager.getInstance().getElementList("play").get(0));
		Player player2 = (Player)(ElementManager.getInstance().getElementList("play").get(1));
		if(player1.getNum()>=1000||player2.getNum()>=1000) {
			flag = false;
			new Thread() {
				public void run() {
					new audioPlay(Audio.OVER).player();
				}
			}.start();
		}

	}
	//游戏的流程控制
	public void linkGame(){
//		Map< String , List<SuperElement> > map =
//				ElementManager.getInstance().getMap();
//		List<SuperElement> enemys = map.get("enemylist");
//		//一秒钟增加一个敌机
//		if (time%10 == 0) {
//			enemys.add(Enemy.createEnemy(""));
//		}
		ElementManager.getInstance().linkGame(time);
	}
	public static int getTime() {
		return time;
	}
	public static void setTime(int time) {
		GameThread.time = time;
	}

	private void loadBGM() {
		new Thread() {
			public void run() {
				while(flag) {
					audioPlay play = new audioPlay(Audio.BGM);
					play.player();
					if(!flag) {
						play.stop();
					}
				}

			}
		}.start();

	}
	//敌机的创建
	
	
}
