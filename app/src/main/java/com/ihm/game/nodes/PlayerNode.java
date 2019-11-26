package com.ihm.game.nodes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.ihm.game.Input;
import com.ihm.game.MainActivity;
import com.ihm.game.maths.Vector2;

public class PlayerNode extends Node2D {

    public int rayon = 30;
    public float speed = 500; //vitesse max en pixel/sec
    public int color = Color.RED;
    public int size = 1;
    public float maxAngle = 10;


    public Vector2 lastInputDirection = new Vector2(1,0);
    public Vector2 lastDirection = new Vector2(1,0);

    private RootNode root;

    public PlayerNode(int x, int y, RootNode root){
        position = new Vector2(x,y);
        setSize(1);
        setSpeed(speed);
        this.root = root;
    }

    private float delayTrail = 0.05f;
    private float time = delayTrail;
    private float trailLifeTime = 0;

    @Override
    public void update(float dt) {

        checkCollisions();

        if(Input.getAxis().length()>0)
            lastInputDirection = Input.getAxis().normalized();
        Vector2 dir = new Vector2(lastInputDirection.x,lastInputDirection.y);

        float angle = dir.angleInDegree(lastDirection);
        if(angle>maxAngle && angle<180-maxAngle || angle<-(180-maxAngle)){
            dir = lastDirection.rotate(maxAngle);
        }
        else if (angle<-maxAngle  && angle>-(180-maxAngle) || angle>(180-maxAngle)){
            dir = lastDirection.rotate(-maxAngle);
        }
        lastDirection.x = dir.x;
        lastDirection.y = dir.y;

        dir.x *= speed*dt;
        dir.y *= speed*dt;
        position.x += dir.x;
        position.y += dir.y;
        if(position.x<0)
            position.x = MainActivity.screenSize.x;
        if(position.x > MainActivity.screenSize.x)
            position.x = 0;
        if(position.y<0)
            position.y = MainActivity.screenSize.y;
        if(position.y > MainActivity.screenSize.y)
            position.y = 0;
        if(Input.getActionColorValue()!=color){
            color = Input.getActionColorValue();
        }

        //instanciate trail
        if(time>delayTrail){
            addChild(new PlayerTrailNode(this, trailLifeTime,100f));
            time=0;
        }
        else{
            time+=dt;
        }

    }

    /**
     * size==1 => trail de environ 100pixel
     * @param size
     */
    public void setSize(int size){
        this.size = size;
        trailLifeTime = size/10f;
    }

    public void setSpeed(float speed){
        this.speed = speed;
        this.delayTrail = (rayon*0.5f)/speed;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(color);
        //canvas.drawRect(new Rect((int)position.x,(int)position.y,(int)position.x+w,(int)position.y+h), p);
        canvas.drawCircle((int)position.x,(int)position.y,rayon,p);
    }

    private void checkCollisions(){
        Rect rect = new Rect((int)position.x-rayon,(int)position.y-rayon,(int)position.x+rayon,(int)position.y+rayon);
        AppleNode apple = root.getApple();
        if(rect.right >= apple.rect.left &&
                rect.left <= apple.rect.right &&
                rect.bottom >= apple.rect.top &&
                rect.top <= apple.rect.bottom){
            if(apple.color == color) {
                root.spawnApple();
                setSize(size + 2);
                setSpeed(speed + 25f);
            }
        }
    }
}
