package com.tedu.show;

import com.tedu.controller.GameListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame implements ActionListener {
    public static int globalVariable = 10;
    private JPanel contentPane;
    private JComboBox<String> mapSelector;

    public StartFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        setContentPane(contentPane);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setResizable(false);
        setSize(650, 550);
        setTitle("疯狂泡泡");
        this.setLocationRelativeTo(null);

        init();
    }

    public void init(){
        contentPane.setLayout(null);

        // 创建地图选择下拉框
        String[] mapOptions = {"地图 A", "地图 B"}; // 假设有两张地图
        mapSelector = new JComboBox<>(mapOptions);
        mapSelector.setBounds(250, 300, 150, 30); // 设置下拉框位置和大小
        contentPane.add(mapSelector);

        // 创建开始游戏按钮
        JButton jButton = new JButton("开始游戏");
        jButton.setIcon(new ImageIcon("img/bg/startgame.png"));
        jButton.setBounds(200, 420, 270, 80);
        jButton.setContentAreaFilled(false);  // 去掉按钮的背景
        jButton.setBorderPainted(false);      // 去掉按钮的边框
        jButton.setFocusPainted(false);       // 去掉按钮的焦点边框（按下时的外框）
        contentPane.add(jButton);

        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon("img/bg/start.png"));
        jLabel.setBounds(0, 0, 650, 480);
        contentPane.add(jLabel);

        jButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取用户选择的地图
        String selectedMap = (String) mapSelector.getSelectedItem();

        // 隐藏当前界面
        this.setVisible(false);

        // 根据选择的地图加载相应的资源
        if ("地图 A".equals(selectedMap)) {
            globalVariable=1; // 加载地图A的资源
        } else if ("地图 B".equals(selectedMap)) {
            globalVariable=0;// 加载地图B的资源
        }

        // 启动游戏窗口
        GameJFrame gameJFrame = new GameJFrame();
        GameJPanel gameJPanel = new GameJPanel();
        GameListener gameListener = new GameListener();

        gameJFrame.setKeyListener(gameListener);
        gameJFrame.addListener();
        gameJFrame.setJPanel(gameJPanel);
        gameJFrame.addJPanel();
        gameJFrame.start();
    }
}
