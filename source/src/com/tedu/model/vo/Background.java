package com.tedu.model.vo;

import com.tedu.manager.ElementFactory;
import com.tedu.show.StartFrame;

import javax.swing.*;
import java.awt.*;

public class Background extends SuperElement {
	private ImageIcon icon;
	private int index;

	@Override
	public void showElement(Graphics g) {
		g.drawImage(icon.getImage(), 0, 0, 480, 480, null);
	}

	@Override
	public void move() {
		// No movement for background
	}

	@Override
	public void destroy() {
		// No destruction logic needed
	}

	public Background() {
		super();
	}

	public Background(int x, int y, int w, int h, ImageIcon icon, int index) {
		super(x, y, w, h);
		this.icon = icon;
		this.index = index;
	}

	public static Background createBackground() {
		// Use globalVariable from StartFrame to determine the background index
		int bgIndex = StartFrame.globalVariable == 1 ? 3 : 4; // Map A uses bg3, Map B uses bg4
		ImageIcon icon = ElementFactory.ElementLoad.getInstance().getImageMap().get("bg" + bgIndex);
		if (icon == null) {
			System.err.println("Failed to load background image: bg" + bgIndex);
		}
		return new Background(0, 0, 480, 480, icon, bgIndex);
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	@Override
	public boolean allowpass() {
		return true;
	}
}