package com.ihm.game.nodes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ihm.game.GameView;
import com.ihm.game.Input;
import com.ihm.game.MainActivity;

public class GameOverNode extends Node2D {

    private GameView gv;

    public GameOverNode(GameView gv){
        this.gv = gv;
        addChild(new TextNode("Game Over",(int) (170.0*(MainActivity.screenSize.x/950.0)),MainActivity.screenSize.x/2,(int)(MainActivity.screenSize.y*0.3f),Color.RED));
        addChild(new TextNode("Score: "+gv.root.player.score,(int) (120.0*(MainActivity.screenSize.x/950.0)),MainActivity.screenSize.x/2,(int)(MainActivity.screenSize.y*0.4f),Color.YELLOW));
        addChild(new TextNode("Touche l'écran pour rejouer",(int)(60.0*(MainActivity.screenSize.x/950.0)),MainActivity.screenSize.x/2,(int)(MainActivity.screenSize.y*0.6), Color.WHITE));
        System.out.print(MainActivity.screenSize.x);
    }

    @Override
    public void update(float dt) {
        if(Input.isScreenJustTouch()){
            System.out.println("Touch!");
            gv.restart();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setARGB(200,0,0,0);
        canvas.drawRect(new Rect(0,0, MainActivity.screenSize.x,MainActivity.screenSize.y), p);
    }
}
