package com.ihm.game.maths;

public class GMath {

    public static float clamp(float a, float min, float max){
        if(a<min)
            return min;
        if(a>max)
            return max;
        return a;
    }
}
