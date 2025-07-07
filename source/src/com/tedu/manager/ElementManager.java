package com.tedu.manager;

import com.tedu.model.vo.*;
import com.tedu.controller.GameThread;

import java.util.*;

public class ElementManager {
	// 集合 NPC元素 场景元素
	Map<String, List<SuperElement>> map;
	SuperElement[][] objects;

	// 初始化
	protected void init() {
		map = new HashMap<>();
		List<SuperElement> plays = new ArrayList<>();
		map.put("play", plays);
		List<SuperElement> playfires1 = new ArrayList<>();
		map.put("bubble", playfires1);
		List<SuperElement> enemys = new ArrayList<>();
		map.put("enemylist", enemys);
		List<SuperElement> playfires = new ArrayList<>();
		map.put("playfire", playfires);
		List<SuperElement> boxs = new ArrayList<>();
		map.put("box", boxs);
		List<SuperElement> props = new ArrayList<>();
		map.put("prop", props);
		List<SuperElement> backgrounds = new ArrayList<>();
		map.put("background", backgrounds);
		List<SuperElement> bombs = new ArrayList<>();
		map.put("bomb", bombs);
		objects = new SuperElement[12][12];
	}

	// 得到一个完整的map集合
	public Map<String, List<SuperElement>> getMap() {
		return map;
	}

	// 得到一个元素的集合
	public List<SuperElement> getElementList(String key) {
		return map.get(key);
	}

	// 单例：需要一个唯一的引用
	private static ElementManager elementManager;

	// 构造方法私有化，只有本类中可以new
	private ElementManager() {
		init();
	}

	static {
		if (elementManager == null) {
			elementManager = new ElementManager();
		}
	}

	// 提供外部访问的唯一入口
	public static ElementManager getInstance() {
		return elementManager;
	}

	// set
	public void setElementByPx(int row, int col, SuperElement superElement) {
		objects[row / 40][col / 40] = superElement;
	}

	public void setElementByIndex(int row, int col, SuperElement superElement) {
		objects[row][col] = superElement;
	}

	// get
	public SuperElement getElementByPx(int row, int col) {
		if (row < 0 || row > 440 || col < 0 || col > 440)
			return null;
		else
			return objects[row / 40][col / 40];
	}

	public SuperElement getElementByIndex(int row, int col) {
		return objects[row][col];
	}

	// 移除
	public SuperElement removeElementByPx(int row, int col) {
		SuperElement superElement = getElementByPx(row, col);
		setElementByPx(row, col, null);
		return superElement;
	}

	public SuperElement removeElementByIndex(int row, int col) {
		SuperElement superElement = getElementByIndex(row, col);
		setElementByIndex(row, col, null);
		return superElement;
	}

	// 资源加载
	public void load() {
		ElementFactory.ElementLoad elementLoad = ElementFactory.ElementLoad.getInstance();
		elementLoad.readImagePro();
		elementLoad.readPlayerPro();
		elementLoad.readGamePro();
		elementLoad.readBoxPro();
		Background background = Background.createBackground(); // 修改为无参数调用
		map.get("background").add(background);
		Player player = (Player) ElementFactory.elementFactory("onePlayer");
		map.get("play").add(player);
		player = (Player) ElementFactory.elementFactory("twoPlayer");
		map.get("play").add(player);
		System.out.println("ljr123" + map.get("play").size());

		List<SuperElement> boxs = map.get("box");
		Map<String, List<String>> boxMap = elementLoad.getBoxMap();
		Set<String> keys = boxMap.keySet();
		for (String string : keys) {
			String temp = boxMap.get(string).get(0);
			String[] arr = temp.split(",");
			if (arr[0].equals("boombox")) {
				Box box = BoomBox.createBox(temp);
				boxs.add(box);
				setElementByPx(box.getY(), box.getX(), box);
			} else {
				Box box = UnBoomBox.createBox(temp);
				boxs.add(box);
				setElementByPx(box.getY(), box.getX(), box);
			}
		}
	}

	// 控制流程
	public void linkGame(int time) {
		List<SuperElement> players = map.get("play");
		if (players.size() < 2) return;

		Player player1 = (Player) players.get(0);
		Player player2 = (Player) players.get(1);

		if (time % 10 == 0) {
			System.out.println(player1);
			System.out.println();
			System.out.println(player2);
		}
	}

	public boolean isBomb(int row, int col) {
		SuperElement superElement = getElementByPx(row, col);
		if (superElement == null)
			return true;
		int type = superElement.getObjectType();
		return type != 4;
	}

	public boolean isBoomBox(int row, int col) {
		SuperElement superElement = getElementByPx(row, col);
		if (superElement == null)
			return false;
		int type = superElement.getObjectType();
		return type == 3;
	}
}