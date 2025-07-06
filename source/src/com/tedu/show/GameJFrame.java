package com.tedu.show;

import com.tedu.controller.GameThread;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameJFrame extends JFrame {


    private KeyListener keyListener;
    private MouseListener mouseListener;
    private MouseMotionListener mouseMotionListener;
    private JPanel jPanel;

    public GameJFrame() {
        super();
        init();
    }

    //初始化方法： 构造方法无法被继承，init方法可以 （被重写）
    public void init(){
        this.setTitle("疯狂泡泡");
        this.setSize(650, 550);
        //this.setBounds(1200, 400, 300, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //this.setBackground(Color.black);


    }
    //绑定监听
    public void addListener(){
        if (keyListener!=null) {
            this.addKeyListener(keyListener);
        }
        if (mouseListener!=null) {
            this.addMouseListener(mouseListener);
        }
        if (mouseMotionListener!=null) {
            this.addMouseMotionListener(mouseMotionListener);
        }
    }
    //画板绑定
    public void addJPanel(){
        if (jPanel!=null) {
            this.add(jPanel);
        }
        //else throw new RuntimeException("游戏初始加载失败");
    }

    public void start(){
        //窗体显示
        this.setVisible(true);
        //线程启动
        //界面刷新 线程启动
        //判断jpanel 是否实现runnable接口
        if (jPanel instanceof Runnable) {
            new Thread((Runnable)jPanel).start();
        }
        GameThread gameThread = new GameThread();
        gameThread.start();

    }


    //set注入
    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }
    public void setJPanel(JPanel jPanel){
        this.jPanel = jPanel;
    }


}
