package com.ihm.game;


import android.graphics.Color;

import com.ihm.game.maths.Vector2;

public class Input {

    private static Input instance = null;

    private float x=0;
    private float y=0;

    private boolean touch = false;
    private boolean touchBuffer = false;
    private boolean currentTouch = false;

    private int color = Couleur.COULEURS[0];

    private Input(){

    }

    private static Input getInstance(){
        if(instance==null)
            instance = new Input();
        return instance;
    }

    public static void reset(){
        getInstance().x = 0;
        getInstance().y = 0;
        getInstance().touch = getInstance().touchBuffer;
        getInstance().touchBuffer = false;
    }

    public static Vector2 getAxis() {
        return new Vector2(getInstance().x,getInstance().y);
    }

    public static void setX(float x) {
        getInstance().x = x;
    }

    public static void setY(float y) {
        getInstance().y = y;
    }

    public static void setActionColor(int color){
        getInstance().color = color;
    }

    public static int getActionColorValue(){
        return getInstance().color;
    }

    public static boolean isScreenJustTouch(){
        return getInstance().touch;
    }

    public static void setCurrentTouch(boolean b){
        getInstance().currentTouch = b;
        if(b){
            getInstance().touchBuffer = true;
        }
    }
}
