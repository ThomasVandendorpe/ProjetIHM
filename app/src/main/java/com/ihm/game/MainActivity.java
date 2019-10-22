package com.ihm.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ihm.game.controllers.AccelerometerController;

public class MainActivity extends Activity {

    public static Point screenSize;
    private SensorManager senSensorManager;
    private AccelerometerController accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        screenSize = new Point();
        getWindowManager(). getDefaultDisplay().getSize(screenSize);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = new AccelerometerController(senSensorManager);

        GameView gv = new GameView(this);
        gv.addController(accelerometer);
        setContentView(gv);
    }

    protected void onPause() {
        super.onPause();
        accelerometer.onPause();
    }

    protected void onResume() {
        super.onResume();
        accelerometer.onResume();
    }
}
