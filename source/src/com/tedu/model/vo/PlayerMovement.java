package com.tedu.model.vo;

import com.tedu.manager.ElementManager;
import com.tedu.controller.GameThread;

import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlayerMovement {
    private int speed;
    private boolean reverseWalk;
    private int reverseTime;
    private int startReverseTime;
    private boolean LEFT, RIGHT, UP, DOWN;
    private Rectangle blockRec;
    private int blockX, blockY;
    private boolean isAddSpeed;
    private boolean isMinusSpeed;
    private Calendar cal2, cal3;
    private Player player;

    public PlayerMovement(Player player) {
        this.player = player;
        this.speed = 10;
        this.reverseWalk = false;
        this.reverseTime = 0;
        this.startReverseTime = -1;
        this.LEFT = false;
        this.RIGHT = false;
        this.UP = false;
        this.DOWN = false;
        this.blockRec = new Rectangle(-1, -1, 40, 40);
        this.blockX = -1;
        this.blockY = -1;
        this.isAddSpeed = false;
        this.isMinusSpeed = false;
        this.cal2 = Calendar.getInstance();
        this.cal3 = Calendar.getInstance();
    }

    public void move() {
        if (player.isFlow()) return;

        int x = player.getX();
        int y = player.getY();
        Rectangle playerRect = new Rectangle(x, y, 40, 40);
        ElementManager manager = ElementManager.getInstance();
        List<SuperElement> boxes = manager.getElementList("box");
        List<SuperElement> bubbles = manager.getElementList("bubble");
        List<SuperElement> props = manager.getElementList("prop");

        // 确定实际方向
        boolean actualUp = reverseWalk ? DOWN : UP;
        boolean actualDown = reverseWalk ? UP : DOWN;
        boolean actualLeft = reverseWalk ? RIGHT : LEFT;
        boolean actualRight = reverseWalk ? LEFT : RIGHT;

        if (actualUp) {
            playerRect.setLocation(x, y - speed);
            if (y <= 0) return;
            if (judgeBoxKnock(boxes, playerRect)) return;
            List<SuperElement> blocks = judgeBubbleKnock(bubbles, playerRect);
            if (blocks.size() >= 2) return;
            else if (blocks.size() == 1) {
                SuperElement temp = blocks.get(0);
                int ax = temp.getX();
                int ay = temp.getY();
                if (ax != blockX || ay != blockY) return;
            }
            player.setY(y - speed);
            handleBlockReset(playerRect);
            handlePropKnock(props, playerRect);
            judgePlayerBlock();
        } else if (actualDown) {
            playerRect.setLocation(x, y + speed);
            if (y >= 440) return;
            if (judgeBoxKnock(boxes, playerRect)) return;
            List<SuperElement> blocks = judgeBubbleKnock(bubbles, playerRect);
            if (blocks.size() >= 2) return;
            else if (blocks.size() == 1) {
                SuperElement temp = blocks.get(0);
                int ax = temp.getX();
                int ay = temp.getY();
                if (ax != blockX || ay != blockY) return;
            }
            player.setY(y + speed);
            handleBlockReset(playerRect);
            handlePropKnock(props, playerRect);
            judgePlayerBlock();
        } else if (actualLeft) {
            playerRect.setLocation(x - speed, y);
            if (x <= 0) return;
            if (judgeBoxKnock(boxes, playerRect)) return;
            List<SuperElement> blocks = judgeBubbleKnock(bubbles, playerRect);
            if (blocks.size() >= 2) return;
            else if (blocks.size() == 1) {
                SuperElement temp = blocks.get(0);
                int ax = temp.getX();
                int ay = temp.getY();
                if (ax != blockX || ay != blockY) return;
            }
            player.setX(x - speed);
            handleBlockReset(playerRect);
            handlePropKnock(props, playerRect);
            judgePlayerBlock();
        } else if (actualRight) {
            playerRect.setLocation(x + speed, y);
            if (x >= 440) return;
            if (judgeBoxKnock(boxes, playerRect)) return;
            List<SuperElement> blocks = judgeBubbleKnock(bubbles, playerRect);
            if (blocks.size() >= 2) return;
            else if (blocks.size() == 1) {
                SuperElement temp = blocks.get(0);
                int ax = temp.getX();
                int ay = temp.getY();
                if (ax != blockX || ay != blockY) return;
            }
            player.setX(x + speed);
            handleBlockReset(playerRect);
            handlePropKnock(props, playerRect);
            judgePlayerBlock();
        }
    }

    private boolean judgeBoxKnock(List<SuperElement> boxes, Rectangle player) {
        for (SuperElement superElement : boxes) {
            Rectangle box = new Rectangle(superElement.getX(), superElement.getY(), 40, 40);
            if (box.intersects(player)) {
                return true;
            }
        }
        return false;
    }

    private List<SuperElement> judgeBubbleKnock(List<SuperElement> bubbles, Rectangle player) {
        List<SuperElement> res = new ArrayList<>();
        for (SuperElement superElement : bubbles) {
            Rectangle bubble = new Rectangle(superElement.getX(), superElement.getY(), 40, 40);
            if (bubble.intersects(player)) {
                res.add(superElement);
            }
        }
        return res;
    }

    private void handlePropKnock(List<SuperElement> props, Rectangle playerRect) {
        SuperElement temp = judgePropKnock(props, playerRect);
        if (temp != null) {
            int type = ((Prop) temp).getPropClass();
            this.player.addProp(type);
            temp.setVisible(false);
            ElementManager.getInstance().removeElementByPx(temp.getY(), temp.getX());
        }
    }

    private SuperElement judgePropKnock(List<SuperElement> props, Rectangle player) {
        for (SuperElement superElement : props) {
            Rectangle prop = new Rectangle(superElement.getX(), superElement.getY(), 40, 40);
            if (prop.intersects(player)) {
                return superElement;
            }
        }
        return null;
    }

    private void handleBlockReset(Rectangle player) {
        if (!blockRec.intersects(player)) {
            blockX = -1;
            blockY = -1;
            blockRec.setLocation(blockX, blockY);
        }
    }

    private void judgePlayerBlock() {
        Player otherPlayer = (Player) ElementManager.getInstance().getElementList("play").get(1 - player.getId());
        if (otherPlayer.isFlow()) {
            Rectangle r1 = new Rectangle(player.getX(), player.getY(), 40, 40);
            Rectangle r2 = new Rectangle(otherPlayer.getX(), otherPlayer.getY(), 40, 40);
            if (r1.intersects(r2)) {
                otherPlayer.recover();
                player.addNum(500);
                System.out.println(player);
                System.out.println(otherPlayer);
            }
        }
    }

    public void updateBubbleBlock() {
        int gridX = ((player.getX() + 20) / 40) * 40;
        int gridY = ((player.getY() + 20) / 40) * 40;
        SuperElement element = ElementManager.getInstance().getElementByPx(gridY, gridX);
        if (element instanceof Bubble && ((Bubble) element).getOwnerId() == player.getId()) {
            blockX = gridX;
            blockY = gridY;
            blockRec.setLocation(gridX, gridY);
        }
    }

    public void addSpeed() {
        cal2 = Calendar.getInstance();
        if (!isAddSpeed) {
            isAddSpeed = true;
            speed += 5;
        }
    }

    public void recoverAddSpeed() {
        speed -= 5;
    }

    public void minusSpeed() {
        cal3 = Calendar.getInstance();
        if (!isMinusSpeed) {
            isMinusSpeed = true;
            speed -= 5;
        }
    }

    public void recoverMinusSpeed() {
        speed += 5;
    }

    public void addReverseTime() {
        if (reverseTime == 0) {
            startReverseTime = GameThread.getTime();
        }
        reverseTime += Player.perReverseTime;
        reverseWalk = true;
    }

    public void minusReverseTime() {
        if (reverseTime > 0) {
            reverseTime -= 50;
        }
    }

    public void updateTime() {
        if (isAddSpeed) {
            Calendar now = Calendar.getInstance();
            if (now.getTimeInMillis() - cal2.getTimeInMillis() > 5000) {
                isAddSpeed = false;
                recoverAddSpeed();
            }
        }
        if (isMinusSpeed) {
            Calendar now = Calendar.getInstance();
            if (now.getTimeInMillis() - cal3.getTimeInMillis() > 5000) {
                isMinusSpeed = false;
                recoverMinusSpeed();
            }
        }
    }

    public void update() {
        if (reverseTime > 0) {
            int currentTime = GameThread.getTime();
            if (currentTime >= startReverseTime + reverseTime) {
                reverseTime = 0;
                startReverseTime = -1;
                reverseWalk = false;
            }
        }
        updateBubbleBlock();
    }

    public boolean isLEFT() {
        return LEFT;
    }

    public void setLEFT(boolean LEFT) {
        this.LEFT = LEFT;
    }

    public boolean isRIGHT() {
        return RIGHT;
    }

    public void setRIGHT(boolean RIGHT) {
        this.RIGHT = RIGHT;
    }

    public boolean isUP() {
        return UP;
    }

    public void setUP(boolean UP) {
        this.UP = UP;
    }

    public boolean isDOWN() {
        return DOWN;
    }

    public void setDOWN(boolean DOWN) {
        this.DOWN = DOWN;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isReverseWalk() {
        return reverseWalk;
    }

    public void setReverseWalk(boolean reverseWalk) {
        this.reverseWalk = reverseWalk;
    }

    public int getReverseTime() {
        return reverseTime;
    }

    public void setReverseTime(int reverseTime) {
        this.reverseTime = reverseTime;
    }

    public int getStartReverseTime() {
        return startReverseTime;
    }

    public void setStartReverseTime(int startReverseTime) {
        this.startReverseTime = startReverseTime;
    }

    public int getBlockX() {
        return blockX;
    }

    public void setBlockX(int blockX) {
        this.blockX = blockX;
    }

    public int getBlockY() {
        return blockY;
    }

    public void setBlockY(int blockY) {
        this.blockY = blockY;
    }

    public Rectangle getBlockRec() {
        return blockRec;
    }

    public boolean isAddSpeed() {
        return isAddSpeed;
    }

    public void setAddSpeed(boolean isAddSpeed) {
        this.isAddSpeed = isAddSpeed;
    }

    public boolean isMinusSpeed() {
        return isMinusSpeed;
    }

    public void setMinusSpeed(boolean isMinusSpeed) {
        this.isMinusSpeed = isMinusSpeed;
    }
}