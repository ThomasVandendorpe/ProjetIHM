package com.ihm.game.maths;

public class Vector2 {

    public float x;
    public float y;

    public Vector2(){
        this.x = 0;
        this.y = 0;
    }

    public Vector2(float x,float y){
        this.x = x;
        this.y = y;
    }

    public float length(){
        return (float) Math.sqrt(x*x+y*y);
    }

    public Vector2 normalized(){
        float l = length();
        if(l<=0)
            return new Vector2();
        return new Vector2(x/l,y/l);
    }

    @Override
    public String toString() {
        return "Vector2("+x+","+y+")";
    }
}
