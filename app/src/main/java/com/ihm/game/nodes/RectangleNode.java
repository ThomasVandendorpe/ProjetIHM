package com.ihm.game.nodes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ihm.game.Input;
import com.ihm.game.MainActivity;
import com.ihm.game.maths.Vector2;

public class RectangleNode extends Node2D {

    private int w;
    private int h;
    public float speed = 1000; //vitesse max en pixel/sec
    public int color;

    public RectangleNode(int x,int y,int w,int h, int color){
        this.color = color;
        position = new Vector2(x,y);
        this.w = w;
        this.h = h;
    }

    @Override
    public void update(float dt) {
        Vector2 dir = Input.getAxis();
        //dir = dir.normalized();
        dir.x *= speed*dt;
        dir.y *= speed*dt;
        if(!((position.x<0 && dir.x<0) || (position.x+w> MainActivity.screenSize.x && dir.x>0)))
          position.x += dir.x;
        if(!((position.y<0 && dir.y<0) || (position.y+h> MainActivity.screenSize.y && dir.y>0)))
            position.y += dir.y;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(color);
        canvas.drawRect(new Rect((int)position.x,(int)position.y,(int)position.x+w,(int)position.y+h), p);
    }
}
