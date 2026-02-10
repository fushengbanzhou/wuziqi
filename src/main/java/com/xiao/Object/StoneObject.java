package com.xiao.Object;

import java.awt.*;

public class StoneObject extends GameObject {
    public Color color;
    public int radius;
    public StoneObject(int x, int y, int radius,Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }
}
