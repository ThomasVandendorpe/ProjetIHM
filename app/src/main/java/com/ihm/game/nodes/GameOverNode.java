package com.ihm.game.nodes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ihm.game.Input;
import com.ihm.game.MainActivity;

public class GameOverNode extends Node2D {

    public GameOverNode(){
        addChild(new TextNode("Game Over",120,MainActivity.screenSize.x/2,(int)(MainActivity.screenSize.y*0.4f),Color.RED));
        addChild(new TextNode("Touche l'écran pour rejouer",60,MainActivity.screenSize.x/2,(int)(MainActivity.screenSize.y*0.6), Color.WHITE));
        System.out.print(MainActivity.screenSize.x);
    }

    @Override
    public void update(float dt) {
        if(Input.isScreenJustTouch()){
            System.out.println("Touch!");
            MainActivity.gv.restart();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setARGB(200,0,0,0);
        canvas.drawRect(new Rect(0,0, MainActivity.screenSize.x,MainActivity.screenSize.y), p);
    }
}
