package com.ihm.game.controllers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ihm.game.Input;
import com.ihm.game.maths.GMath;

public class AccelerometerController implements SensorEventListener, Controller {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int THRESHOLD = 0;
    private static final int MAX_VALUE = 5;

    public AccelerometerController(SensorManager senSensorManager){
        this.senSensorManager = senSensorManager;
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onPause() {
        senSensorManager.unregisterListener(this);
    }

    public void onResume() {
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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
