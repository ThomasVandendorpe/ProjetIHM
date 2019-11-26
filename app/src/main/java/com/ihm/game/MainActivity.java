package com.ihm.game;

import android.app.Activity;
import android.content.Context;

import android.content.res.AssetManager;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.ihm.game.controllers.AccelerometerController;
import com.ihm.game.controllers.VoiceController;

public class MainActivity extends Activity  {

    public static Point screenSize;
    private SensorManager senSensorManager;
    private AccelerometerController accelerometer;
    private VoiceController voiceController;
    public static GameView gv;

    public static AssetManager assetManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        screenSize = new Point();
        getWindowManager(). getDefaultDisplay().getSize(screenSize);

        assetManager = getAssets();

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = new AccelerometerController(senSensorManager);

        voiceController = new VoiceController(this);

        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gv = new GameView(MainActivity.this);
                gv.addController(accelerometer);
                gv.addController(voiceController);
                setContentView(gv);
            }
        }));



    }


    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.onResume();
    }
}
