package com.xiao.Util;

import java.awt.*;

public class GameUtils {
    static int width = 15, height = 15;

    //绘制文字
    public static void drawWord(Graphics g, String string, Color color, int size, int x, int y) {
        g.setColor(color);
        g.setFont(new Font("等线", Font.PLAIN, size));
        g.drawString(string, x, y);
    }
}
