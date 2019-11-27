package com.ihm.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ihm.game.controllers.Controller;
import com.ihm.game.nodes.PlayerNode;
import com.ihm.game.nodes.RootNode;
import android.util.AttributeSet;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private ArrayList<Controller> controllers;

    public RootNode root;

    public GameView(Context context){
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init(){
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        controllers = new ArrayList<>();

        root = new RootNode(this);

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
        getHolder().getSurface().release();
    }

    public void update(float dt){
        Input.reset();
        for(Controller c : controllers)
            c.update();
        root.updateAll(dt);
    }

    @Override
    public void draw(Canvas canvas){
        if(canvas==null)
            return;
        super.draw(canvas);
        canvas.drawColor(Color.DKGRAY);
        root.drawAll(canvas);
    }

    public void addController(Controller controller){
        controllers.add(controller);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            Input.setCurrentTouch(true);
        } else if (e.getAction() == android.view.MotionEvent.ACTION_UP) {
            Input.setCurrentTouch(false);
        }
        return true;
    }

    public void restart(){
        root = new RootNode(this);
    }
}

