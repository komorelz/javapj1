package com.tedu.controller;

import com.tedu.manager.ElementManager;
import com.tedu.model.vo.Player;
import com.tedu.model.vo.PlayerMovement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class GameListener implements KeyListener {
	private List<?> list;

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		list = ElementManager.getInstance().getElementList("play");
		Player oneplayer = (Player)list.get(0);
		Player twoPlayer = (Player)list.get(1);

		// 获取PlayerMovement实例
		PlayerMovement movement1 = oneplayer.getMovement();
		PlayerMovement movement2 = twoPlayer.getMovement();

		switch (e.getKeyCode()) {
			case 65: // A
				movement1.setLEFT(true);
				break;
			case 87: // W
				movement1.setUP(true);
				break;
			case 68: // D
				movement1.setRIGHT(true);
				break;
			case 83: // S
				movement1.setDOWN(true);
				break;
			case 32: // Space
				oneplayer.setPk(true);
				break;
			case 37: // Left
				movement2.setLEFT(true);
				break;
			case 38: // Up
				movement2.setUP(true);
				break;
			case 39: // Right
				movement2.setRIGHT(true);
				break;
			case 40: // Down
				movement2.setDOWN(true);
				break;
			case 10: // Enter
				twoPlayer.setPk(true);
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		list = ElementManager.getInstance().getElementList("play");
		Player oneplayer = (Player)list.get(0);
		Player twoPlayer = (Player)list.get(1);

		// 获取PlayerMovement实例
		PlayerMovement movement1 = oneplayer.getMovement();
		PlayerMovement movement2 = twoPlayer.getMovement();

		switch (e.getKeyCode()) {
			case 65: // A
				movement1.setLEFT(false);
				break;
			case 87: // W
				movement1.setUP(false);
				break;
			case 68: // D
				movement1.setRIGHT(false);
				break;
			case 83: // S
				movement1.setDOWN(false);
				break;
			case 32: // Space
				oneplayer.setPk(false);
				break;
			case 37: // Left
				movement2.setLEFT(false);
				break;
			case 38: // Up
				movement2.setUP(false);
				break;
			case 39: // Right
				movement2.setRIGHT(false);
				break;
			case 40: // Down
				movement2.setDOWN(false);
				break;
			case 10: // Enter
				twoPlayer.setPk(false);
				break;
		}
	}
}