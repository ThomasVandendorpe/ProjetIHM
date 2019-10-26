package com.ihm.game.nodes;

import android.graphics.Canvas;

import com.ihm.game.maths.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;

public class Node2D implements Node{

    public Vector2 position = new Vector2();
    private ArrayList<Node2D> children = new ArrayList<>();
    private LinkedList<Node2D> removeQueue = new LinkedList<>();

    public void updateAll(float dt){
        update(dt);
        for(Node2D c : children){
            c.updateAll(dt);
        }
        if(!removeQueue.isEmpty()){
            for(Node2D c : removeQueue){
                children.remove(c);
            }
        }
    }

    public void drawAll(Canvas canvas){
        draw(canvas);
        for(Node2D c : children){
            c.drawAll(canvas);
        }
    }

    public void addChild(Node2D node){
        children.add(node);
    }

    public void removeChild(Node2D node){
        removeQueue.add(node);
    }

    public void update(float dt){

    }

    public void draw(Canvas canvas){

    }
}
