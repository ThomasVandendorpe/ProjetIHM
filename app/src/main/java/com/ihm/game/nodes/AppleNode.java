package com.ihm.game.nodes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ihm.game.maths.Vector2;

public class AppleNode extends Node2D {

    private final int width = 100;
    private final int height = 100;

    public Rect rect = null;
    public int color = Color.RED;

    public AppleNode(int x,int y){
        this.position = new Vector2(x,y);
        rect = new Rect(x,y,x+width,y+height);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(color);
        canvas.drawRect(rect,p);
    }

}
