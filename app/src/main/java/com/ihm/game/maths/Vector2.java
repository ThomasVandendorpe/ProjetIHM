package com.ihm.game.maths;

import java.util.Vector;

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

    public float angleInRadian(Vector2 v){
        return (float) (Math.atan2(v.y,v.x) - Math.atan2(y,x))*-1f;
    }

    public float angleInDegree(Vector2 v){
        return (float) (angleInRadian(v)*180.0/Math.PI);
    }

    public Vector2 rotate(float angleInDegree){
        float angleInRadian = (float) (angleInDegree*Math.PI/180.0);
        return new Vector2((float)(x*Math.cos(angleInRadian)-y*Math.sin(angleInRadian)),(float)(x*Math.sin(angleInRadian)+y*Math.cos(angleInRadian)));
    }

    @Override
    public String toString() {
        return "Vector2("+x+","+y+")";
    }

    public float distanceTo(Vector2 v){
        return new Vector2(v.x-x,v.y-y).length();
    }
}
