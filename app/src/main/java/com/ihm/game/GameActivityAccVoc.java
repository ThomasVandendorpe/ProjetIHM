package com.ihm.game;

import android.app.Activity;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ihm.game.controllers.AccelerometerController;
import com.ihm.game.controllers.VoiceController;

public class GameActivityAccVoc extends Activity {

    private SensorManager senSensorManager;
    private AccelerometerController accelerometer;
    private VoiceController voiceController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_acc_voc);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = new AccelerometerController(senSensorManager);

        voiceController = new VoiceController(this);

        GameView gv = (GameView) findViewById(R.id.gameView2);
        gv.addController(accelerometer);
        gv.addController(voiceController);
    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.onPause();
        voiceController.disable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.onResume();
        voiceController.enable();
    }
}
