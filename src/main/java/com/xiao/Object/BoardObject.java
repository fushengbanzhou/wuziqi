package com.xiao.Object;

import com.xiao.Frame.GameFrame;

import java.awt.*;

public class BoardObject extends GameObject {
    public int interval = 40;
    public int size = 16;
    public static int margin = 50;
    public int length = (size - 1) * interval;

    // 棋盘 0：空，1：黑，2：白
    public int[][] board = new int[size][size];

    boolean isBlack=true;

    public BoardObject(GameFrame gameFrame) {
        super(null, margin, margin, gameFrame);
    }

    // 绘制棋盘
    @Override
    public void paintSelf(Graphics g) {
        g.setColor(Color.black);
        for (int i = 0; i < size; i++) {
            g.drawLine(margin, i * interval + margin, margin + length, i * interval + margin);
            g.drawLine(i * interval + margin, margin, i * interval + margin, margin + length);
        }
    }

    // 把任意像素坐标吸附到最近的交叉点
    public int[] snap(int mouseX, int mouseY) {
        int gx = Math.round((float) (mouseX - x) / interval) * interval + x;
        int gy = Math.round((float) (mouseY - y) / interval) * interval + y;
        return new int[]{gx, gy};
    }

    // 判断交叉点是否在棋盘范围内
    public boolean inBounds(int gx, int gy) {
        return gx >= x && gx <= x + length
                && gy >= y && gy <= y + length;
    }
}