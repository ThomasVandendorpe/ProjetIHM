package com.ihm.game.nodes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ihm.game.Input;
import com.ihm.game.MainActivity;
import com.ihm.game.maths.Vector2;

public class PlayerTrailNode extends Node2D {

    private PlayerNode player = null;
    private int r = 0;
    private float speed = 0;
    private float delay = 0;
    private float startTime = 0;

    public PlayerTrailNode(PlayerNode player, float delay, float speed){
        this.player = player;
        this.r = player.r;
        this.speed = speed;
        this.delay = delay;
        this.position = new Vector2(player.position.x,player.position.y);
    }


    @Override
    public void update(float dt) {
        if(startTime>delay){
            r -= speed*dt;
        }
        else{
            startTime+=dt;
        }
        if(r<=0)
            player.removeChild(this);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(player.color);
        canvas.drawCircle((int)position.x,(int)position.y,r,p);
    }
}
