package com.tedu.manager;

import com.tedu.model.vo.Enemy;
import com.tedu.model.vo.Player;
import com.tedu.model.vo.SuperElement;
import com.tedu.show.StartFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ElementFactory {

	public static SuperElement elementFactory(String name) {
		Map<String, List<String>> playMap = ElementLoad.getInstance().getPlayMap();
		Map<String, List<String>> boxMap = ElementLoad.getInstance().getBoxMap();
		List<String> gamelist = ElementLoad.getInstance().getGameList();
		switch (name) {
			case "onePlayer":
				List<String> list = playMap.get(name);
				String string = list.get(0);
				return Player.createPlayer(string);
			case "twoPlayer":
				List<String> list1 = playMap.get(name);
				String string1 = list1.get(0);
				return Player.createPlayer(string1);
			case "enemy":
				String string2 = gamelist.get(gamelist.size() - 1);
				return Enemy.createEnemy(string2);
			default:
				break;
		}
		return null;
	}

	public static class ElementLoad {
		private Map<String, ImageIcon> imageMap;
		private Map<String, List<String>> playMap;
		private Map<String, List<String>> enemyMap;
		private Map<String, List<String>> boxMap;
		private List<String> gameList;
		private Properties properties;

		private static ElementLoad load;

		private ElementLoad() {
			imageMap = new HashMap<>();
			playMap = new HashMap<>();
			enemyMap = new HashMap<>();
			boxMap = new HashMap<>();
			gameList = new ArrayList<>();
			properties = new Properties();
		}

		public static synchronized ElementLoad getInstance() {
			if (load == null) {
				load = new ElementLoad();
			}
			return load;
		}

		public void readBoxPro() {
			String str = "com/tedu/pro/box" + StartFrame.globalVariable + ".pro";
			InputStream inputStream = ElementLoad.class.getClassLoader().getResourceAsStream(str);
			if (inputStream == null) {
				System.err.println("Failed to load box properties file: " + str);
				return;
			}
			properties.clear();
			try {
				properties.load(inputStream);
				for (Object object : properties.keySet()) {
					String string = properties.getProperty(object.toString());
					List<String> boxs = new ArrayList<>();
					boxs.add(string);
					boxMap.put(object.toString(), boxs);
				}
				System.out.println("Loaded boxMap: " + boxMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void readGamePro() {
			InputStream inputStream = ElementLoad.class.getClassLoader().getResourceAsStream("com/tedu/pro/GameRunA.pro");
			properties.clear();
			try {
				properties.load(inputStream);
				for (Object object : properties.keySet()) {
					String string = properties.getProperty(object.toString());
					gameList.add(string);
				}
				System.out.println(gameList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void readPlayerPro() {
			InputStream inputStream = ElementLoad.class.getClassLoader().getResourceAsStream("com/tedu/pro/play.pro");
			try {
				properties.clear();
				properties.load(inputStream);
				for (Object object : properties.keySet()) {
					String string = properties.getProperty(object.toString());
					List<String> players = new ArrayList<>();
					players.add(string);
					playMap.put(object.toString(), players);
				}
				System.out.println(playMap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void readImagePro() {
			String mapFile = StartFrame.globalVariable == 1 ? "mapA.pro" : "mapB.pro";
			InputStream inputStream = ElementLoad.class.getClassLoader().getResourceAsStream("com/tedu/pro/" + mapFile);
			try {
				properties.clear();
				properties.load(inputStream);
				Set<Object> set = properties.keySet();
				for (Object object : set) {
					String url = properties.getProperty((String) object);
					System.out.println(object + ":" + url);
					imageMap.put(object.toString(), new ImageIcon(url));
				}
				System.out.println(imageMap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void readImageProB() {
			InputStream inputStream = ElementLoad.class.getClassLoader().getResourceAsStream("com/tedu/pro/mapB.pro");
			try {
				properties.load(inputStream);
				Set<Object> set = properties.keySet();
				for (Object object : set) {
					String url = properties.getProperty((String) object);
					System.out.println(object + ":" + url);
					File file = new File(url);
					File[] arrFile = file.listFiles();
					System.out.println(Arrays.toString(arrFile));
				}
				System.out.println(imageMap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public Map<String, ImageIcon> getImageMap() {
			return imageMap;
		}

		public Map<String, List<String>> getPlayMap() {
			return playMap;
		}

		public Map<String, List<String>> getEnemyMap() {
			return enemyMap;
		}

		public List<String> getGameList() {
			return gameList;
		}

		public Map<String, List<String>> getBoxMap() {
			return boxMap;
		}
	}
}