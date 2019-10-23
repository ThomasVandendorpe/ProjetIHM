package com.ihm.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ihm.game.controllers.Controller;
import com.ihm.game.nodes.RectangleNode;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private ArrayList<Controller> controllers;

    public RectangleNode player;

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        controllers = new ArrayList<>();

        player = new RectangleNode(100,100,100,100, Color.RED);

        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int weight){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean r = true;
        while(r){
            try{
                thread.setRunning(false);
                thread.join();
                r = false;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//TODO: supprimer -> pas de controller ici
        if(player.color==Color.RED){
            player.color = Color.GREEN;
        }
        else if(player.color==Color.GREEN){
            player.color = Color.BLUE;
        }
        else{
            player.color = Color.RED;
        }
        return super.onTouchEvent(event);
    }

    public void update(float dt){
        Input.reset();
        for(Controller c : controllers)
            c.update();
        player.update(dt);
    }

    @Override
    public void draw(Canvas canvas){
        if(canvas==null)
            return;
        super.draw(canvas);
        canvas.drawColor(Color.DKGRAY);
        player.draw(canvas);
    }

    public void addController(Controller controller){
        controllers.add(controller);
    }
}

