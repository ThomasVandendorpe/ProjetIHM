package com.ihm.game;

import android.app.Activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.ihm.game.controllers.JoystickController;
import com.ihm.game.controllers.TouchController;
import com.ihm.game.maths.Vector2;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_game);

        JoystickView joystick = (JoystickView) findViewById(R.id.joystick);

        GameView gv = (GameView) findViewById(R.id.gameView);
        gv.addController(new JoystickController(joystick));
        gv.addController(new TouchController());

    }
}
