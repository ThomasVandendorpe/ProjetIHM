package com.ihm.game.controllers;

import com.ihm.game.Input;
import com.ihm.game.maths.GMath;
import com.ihm.game.maths.Vector2;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class JoystickController implements Controller {

    private long lastUpdate = 0;
    private float last_x, last_y;
    private static final float THRESHOLD = 0f;
    private static final float MAX_VALUE = 5f;
    private JoystickView joystick = null;

    public JoystickController(JoystickView joystick){
        this.joystick = joystick;
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                Vector2 v = new Vector2(-1,0);
                Vector2 rv = v.rotate(angle);
                if(strength>0) {
                    last_x = rv.x;
                    last_y = rv.y;
                }
                else{
                    last_x = 0;
                    last_y = 0;
                }
            }
        });
    }

    @Override
    public void update() {
        if(Math.abs(last_x)>THRESHOLD)
            Input.setX(-GMath.clamp(last_x,-MAX_VALUE,MAX_VALUE)/MAX_VALUE);
        else
            Input.setX(0);
        if(Math.abs(last_y)>THRESHOLD)
            Input.setY(GMath.clamp(last_y,-MAX_VALUE,MAX_VALUE)/MAX_VALUE);
        else
            Input.setY(0);
    }
}
