package com.ihm.game.nodes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.ihm.game.Input;
import com.ihm.game.MainActivity;
import com.ihm.game.maths.Vector2;

public class PlayerNode extends Node2D {

    public int r;
    public float speed = 500; //vitesse max en pixel/sec
    public int color;
    public Vector2 lastDirection = new Vector2(1,0);

    public PlayerNode(int x, int y, int r, int color){
        this.color = color;
        position = new Vector2(x,y);
        this.r = r;
    }

    private float delayTrail = 0.05f;
    private float time = delayTrail;

    @Override
    public void update(float dt) {
        if(Input.getAxis().length()>0)
            lastDirection = Input.getAxis().normalized();
        Vector2 dir = new Vector2(lastDirection.x,lastDirection.y);
        dir.x *= speed*dt;
        dir.y *= speed*dt;
        if(!((position.x<0 && dir.x<0) || (position.x+r> MainActivity.screenSize.x && dir.x>0)))
          position.x += dir.x;
        if(!((position.y<0 && dir.y<0) || (position.y+r> MainActivity.screenSize.y && dir.y>0)))
            position.y += dir.y;
        if(Input.getActionColorValue()!=color){
            color = Input.getActionColorValue();
        }

        //instanciate trail
        if(time>delayTrail){
            System.out.println("SPAWN");
            addChild(new PlayerTrailNode(this, 0.5f,100f));
            time=0;
        }
        else{
            time+=dt;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(color);
        //canvas.drawRect(new Rect((int)position.x,(int)position.y,(int)position.x+w,(int)position.y+h), p);
        canvas.drawCircle((int)position.x,(int)position.y,r,p);
    }
}
