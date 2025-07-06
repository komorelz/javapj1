package com.tedu.model.vo;

import com.tedu.manager.ElementFactory;
import com.tedu.manager.ElementManager;
import com.tedu.controller.GameThread;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.List;

public class Player extends SuperElement {
    private int hp;
    private int num;
    private ImageIcon img;
    private ImageIcon bombIcon;
    private boolean pk;
    private int moveX;
    private int moveY;
    private ElementManager manager;
    private int currentBubble, maxBubble;
    private int bubblePower;
    private int id;
    public static int perReverseTime;
    private boolean isFlow;
    private int startFlowTime;
    private Calendar cal;
    private PlayerMovement movement;

    static {
        perReverseTime = 50;
    }

    public Player(int x, int y, int w, int h, ImageIcon imageIcon, ImageIcon bombIcon, int id) {
        super(x, y, w, h);
        this.img = imageIcon;
        this.bombIcon = bombIcon;
        this.id = id;
        this.hp = 1;
        this.num = 0;
        this.pk = false;
        this.currentBubble = 0;
        this.maxBubble = 3;
        this.bubblePower = 1;
        this.manager = ElementManager.getInstance();
        this.isFlow = false;
        this.startFlowTime = -1;
        this.cal = Calendar.getInstance();
        this.movement = new PlayerMovement(this);
        setObjectType(1);
    }

    public static Player createPlayer(String str) {
        String[] arr = str.split(",");
        int x = Integer.parseInt(arr[2]),
                y = Integer.parseInt(arr[3]),
                w = Integer.parseInt(arr[4]),
                h = Integer.parseInt(arr[5]),
                id = Integer.parseInt(arr[6]);
        ImageIcon icon = ElementFactory.ElementLoad.getInstance().getImageMap().get(arr[0]);
        ImageIcon bombIcon = ElementFactory.ElementLoad.getInstance().getImageMap().get(arr[1]);
        return new Player(x, y, w, h, icon, bombIcon, id);
    }

    @Override
    public void showElement(Graphics g) {
        if (isFlow) {
            g.drawImage(bombIcon.getImage(),
                    getX(), getY(),
                    getX() + getW(), getY() + getH(),
                    0 + 80 * moveX, 6,
                    80 + 80 * moveX, 70,
                    null);
        } else {
            g.drawImage(img.getImage(),
                    getX(), getY(),
                    getX() + getW(), getY() + getH(),
                    26 + 100 * moveX, 40 + 100 * moveY,
                    74 + 100 * moveX, 100 + 100 * moveY,
                    null);
        }
    }

    @Override
    public void move() {
        movement.move();
    }

    @Override
    public void update() {
        super.update();
        addBubble();
        updateImage();
        updateTime();
        movement.update();
    }

    public void addBubble() {
        if (!pk || isFlow) return;
        if (currentBubble >= maxBubble) return;

        List<SuperElement> list = manager.getElementList("bubble");
        SuperElement superElement = manager.getElementByPx(((getY() + 20) / 40) * 40, ((getX() + 20) / 40) * 40);
        if (superElement != null && superElement instanceof Bubble) return;

        Bubble bubble = Bubble.createBubble(
                ((getX() + 20) / 40) * 40,
                ((getY() + 20) / 40) * 40,
                new ImageIcon("img/play/asd.png"),
                id,
                bubblePower
        );
        list.add(bubble);

        new Thread(() -> new audioPlay(Audio.ADD).player()).start();

        for (int i = 0, size = list.size(); i < size; i++) {
            Bubble bubble2 = (Bubble) list.get(i);
            manager.setElementByPx(bubble2.getY(), bubble2.getX(), bubble2);
        }

        currentBubble++;
        pk = false;
        movement.updateBubbleBlock();
    }

    public void bombByBubble(int attackerId) {
        if (hp >= 2) {
            hp--;
        } else {
            setFlow(true);
            if (attackerId == id) num -= 200;
            setStartFlowTime(GameThread.getTime());
            cal = Calendar.getInstance();
        }
    }

    public void addProp(int type) {
        Player otherPlayer = null; // 在方法开头声明 otherPlayer
        if (type == 5 || type == 8) {
            otherPlayer = (Player) manager.getElementList("play").get(1 - id);
        }
        switch (type) {
            case 1:
                maxBubble++;
                break;
            case 2:
                bubblePower++;
                break;
            case 3:
                hp++;
                break;
            case 4:
                movement.addReverseTime();
                break;
            case 5:
                otherPlayer.movement.addReverseTime();
                break;
            case 6:
                movement.minusReverseTime();
                break;
            case 7:
                movement.addSpeed();
                break;
            case 8:
                otherPlayer.movement.minusSpeed();
                break;
        }
        System.out.println(this);
    }

    public void updateImage() {
        if (isFlow) {
            moveX = (++moveX) % 4;
        } else {
            if (movement.isUP()) {
                moveY = movement.getReverseTime() > 0 ? 0 : 3;
            } else if (movement.isLEFT()) {
                moveY = movement.getReverseTime() > 0 ? 2 : 1;
            } else if (movement.isRIGHT()) {
                moveY = movement.getReverseTime() > 0 ? 1 : 2;
            } else if (movement.isDOWN()) {
                moveY = movement.getReverseTime() > 0 ? 3 : 0;
            }
            moveX = (++moveX) % 4;
        }
    }

    public void updateTime() {
        if (isFlow) {
            Calendar now = Calendar.getInstance();
            if (now.getTimeInMillis() - cal.getTimeInMillis() > 5000) {
                recover();
            }
        }
        movement.updateTime();
    }

    public void recover() {
        setFlow(false);
    }

    @Override
    public void destroy() {
        if (!isVisible()) {
            GridCell.setGridByPx(getY(), getX(), 0);
            manager.removeElementByPx(getY() / 40, getX() / 40);
        }
    }

    @Override
    public boolean allowpass() {
        return true;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void addNum(int num1) {
        this.num += num1;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }

    public boolean isPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public int getCurrentBubble() {
        return currentBubble;
    }

    public void setCurrentBubble(int currentBubble) {
        this.currentBubble = currentBubble;
    }

    public int getMaxBubble() {
        return maxBubble;
    }

    public void setMaxBubble(int maxBubble) {
        this.maxBubble = maxBubble;
    }

    public int getBubblePower() {
        return bubblePower;
    }

    public void setBubblePower(int bubblePower) {
        this.bubblePower = bubblePower;
    }

    public boolean isFlow() {
        return isFlow;
    }

    public void setFlow(boolean isFlow) {
        this.isFlow = isFlow;
    }

    public int getStartFlowTime() {
        return startFlowTime;
    }

    public void setStartFlowTime(int startFlowTime) {
        this.startFlowTime = startFlowTime;
    }

    public int getId() {
        return id;
    }

    public PlayerMovement getMovement() {
        return movement;
    }

    @Override
    public String toString() {
        return "Player [speed=" + movement.getSpeed() + ", id=" + id + "]";
    }
}