package com.ihm.game.nodes;

import android.graphics.Color;

import com.ihm.game.MainActivity;

import java.util.ArrayList;
import java.util.Random;

public class RootNode extends Node2D {

    private PlayerNode player;
    private AppleNode apple;

    public RootNode(){
        player = new PlayerNode(100,100, this);
        addChild(player);

        spawnApple();
    }

    public AppleNode getApple(){
        return apple;
    }

    public void spawnApple(){
        if(apple!=null){
            removeChild(apple);
        }
        apple = new AppleNode((int)(Math.random()* (MainActivity.screenSize.x-100)),(int)(Math.random()*(MainActivity.screenSize.y-100)));
        int color = Color.RED;//TODO: meilleur système de couleur
        int r = (int)(Math.random() * 3);
        if(r==1)
            color = Color.GREEN;
        else if (r==2){
            color = Color.BLUE;
        }
        apple.color = color;
        addChild(apple);
    }

}
