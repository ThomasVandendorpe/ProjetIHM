package com.ihm.game.controllers;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.ihm.game.Couleur;
import com.ihm.game.Input;

public class TouchController implements Controller {

    private FrameLayout frame = null;
    private int currentColor = 0;

    public TouchController(){
    }

    @Override
    public void update() {
        if(Input.isScreenJustTouch()){
            currentColor++;
            if(currentColor>=Couleur.nbCouleurs)
                currentColor=0;
            Input.setActionColor(Couleur.COULEURS[currentColor]);
        }
    }
}
