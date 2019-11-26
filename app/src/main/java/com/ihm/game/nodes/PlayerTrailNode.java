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

    private float activatedDelay = 0.2f;
    private boolean activated = false;
    private boolean hasBeenActivated = false;

    public PlayerTrailNode(PlayerNode player, float delay, float speed){
        this.player = player;
        this.r = player.rayon;
        this.speed = speed;
        this.delay = delay;
        this.position = new Vector2(player.position.x,player.position.y);
    }


    @Override
    public void update(float dt) {
        if(player.root.isGameOver)
            return;

        if(!activated && !hasBeenActivated && startTime>activatedDelay) {
            activated = true;
            hasBeenActivated = true;
        }

        if(startTime>delay){
            r -= speed*dt;
            if(activated)
                activated = false;
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

    public boolean canKill(){
        return activated;
    }

    public int getRayon(){
        return r;
    }
}
