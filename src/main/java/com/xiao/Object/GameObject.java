package com.xiao.Object;

import com.xiao.Frame.GameFrame;

import java.awt.*;

public class GameObject {

    String name;
    Image image;
    public int x,y;
    GameFrame frame;
    MediaTracker tracker;

    public GameObject() {

    }

    public GameObject(String name,Image image,int x,int y,GameFrame frame) {
        this.name=name;
        this.image=image;
        this.x=x;
        this.y=y;
        this.frame=frame;
        if (frame != null) {
            tracker = new MediaTracker(frame);
            tracker.addImage(image, 0);
            try {
                tracker.waitForAll();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public GameObject(Image image,int x, int y, GameFrame frame) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.frame = frame;
        if (frame != null) {
            tracker = new MediaTracker(frame);
            tracker.addImage(image, 0);
            try {
                tracker.waitForAll();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void paintSelf(Graphics g){
        g.drawImage(image,x,y,null);
    }
}
