package com.ihm.game.nodes;

import android.graphics.Color;

import com.ihm.game.Couleur;
import com.ihm.game.GameView;
import com.ihm.game.MainActivity;
import com.ihm.game.maths.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class RootNode extends Node2D {

    public PlayerNode player;
    private AppleNode apple;
    private GameView gv;

    public boolean isGameOver = false;

    public RootNode(GameView gv){
        this.gv = gv;
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

        Vector2 newpos = new Vector2((int)(Math.random()* (MainActivity.screenSize.x-100)),(int)(Math.random()*(MainActivity.screenSize.y-100)));
        while(newpos.distanceTo(player.position)<player.rayon*20){
            newpos = new Vector2((int)(Math.random()* (MainActivity.screenSize.x-100)),(int)(Math.random()*(MainActivity.screenSize.y-100)));
        }
        apple = new AppleNode((int)newpos.x,(int)newpos.y,MainActivity.screenSize);
        int color = Color.RED;//TODO: meilleur système de couleur
        int r = (int)(Math.random() * Couleur.nbCouleurs);
        color = Couleur.COULEURS[r];
        apple.color = color;
        addChild(apple);
    }

    public void gameOver(){
        if(!isGameOver) {
            addChild(new GameOverNode(gv));
            isGameOver = true;
        }
    }

}
