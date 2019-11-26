package com.ihm.game.nodes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.ihm.game.MainActivity;

public class TextNode extends Node2D {

    public String text;
    public int color = Color.BLACK;
    public int size = 300;

    public int x,y;

    private Typeface custom_font;

    public TextNode(String text, int size, int x, int y, int color){
       this(text,size,x,y);
       this.color = color;
    }
    public TextNode(String text, int size, int x, int y){
        this.text = text;
        this.size = size;
        this.x = x;
        this.y = y;
        custom_font = Typeface.createFromAsset(MainActivity.assetManager,  "fonts/riffic.otf");
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(color);
        p.setTypeface(custom_font);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(size);
        int xPos = x;
        int yPos = (int) (y - ((p.descent() + p.ascent()) / 2)) ;
        canvas.drawText(text, xPos, yPos, p);
    }

}
