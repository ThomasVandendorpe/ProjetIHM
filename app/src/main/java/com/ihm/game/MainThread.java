package com.ihm.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
    public static final int MAX_FPS = 30;
    public double averageFPS = 0;
    public SurfaceHolder surfaceHolder;
    public GameView gameView;
    public boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(Boolean running){
        this.running = running;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime = 0;
        long timeLastFrame = 0;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    double delta = (System.currentTimeMillis()-timeLastFrame)/1000.0;
                    this.gameView.update((float)delta);
                    this.gameView.draw(canvas);
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally{
                if(canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            timeLastFrame = System.currentTimeMillis();
            timeMillis = (System.nanoTime()-startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try{
                if(waitTime>0){
                    this.sleep(waitTime);
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount==MAX_FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
}
