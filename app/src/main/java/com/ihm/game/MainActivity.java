package com.ihm.game;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ihm.game.controllers.AccelerometerController;
import com.vikramezhil.droidspeech.DroidSpeech;
import com.vikramezhil.droidspeech.OnDSListener;
import com.vikramezhil.droidspeech.OnDSPermissionsListener;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements OnDSListener, OnDSPermissionsListener {

    public static Point screenSize;
    private SensorManager senSensorManager;
    private AccelerometerController accelerometer;
    private DroidSpeech droidSpeech;
    public GameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        screenSize = new Point();
        getWindowManager(). getDefaultDisplay().getSize(screenSize);


        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = new AccelerometerController(senSensorManager);

        this.checkPermission();
        //test speech
        droidSpeech = new DroidSpeech(this, getFragmentManager());
        droidSpeech.setOnDroidSpeechListener(this);
        if(droidSpeech.getContinuousSpeechRecognition() == true)
            droidSpeech.closeDroidSpeechOperations();
        droidSpeech.startDroidSpeechRecognition();

        gv = new GameView(this);
        gv.addController(accelerometer);
        setContentView(gv);

    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.onPause();
        droidSpeech.closeDroidSpeechOperations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.onResume();
        droidSpeech.startDroidSpeechRecognition();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        droidSpeech.closeDroidSpeechOperations();
    }


    // MARK: DroidSpeechListener Methods

    @Override
    public void onDroidSpeechSupportedLanguages(String currentSpeechLanguage, List<String> supportedSpeechLanguages)
    {
        Log.i(TAG, "Current speech language = " + currentSpeechLanguage);
        Log.i(TAG, "Supported speech languages = " + supportedSpeechLanguages.toString());

        if(supportedSpeechLanguages.contains("fr-FR"))
        {
            // Setting the droid speech preferred language as tamil if found
            droidSpeech.setPreferredLanguage("fr-FR");

            // Setting the confirm and retry text in tamil
            droidSpeech.setOneStepVerifyConfirmText("confirm");
            droidSpeech.setOneStepVerifyRetryText("retry");
        }
    }

    @Override
    public void onDroidSpeechRmsChanged(float rmsChangedValue)
    {
        // Log.i(TAG, "Rms change value = " + rmsChangedValue);
    }

    @Override
    public void onDroidSpeechLiveResult(String liveSpeechResult)
    {
        if(liveSpeechResult.contains("vert")||liveSpeechResult.contains("verre")||liveSpeechResult.contains("vers")){
            gv.player.color = Color.GREEN;
        }
        if(liveSpeechResult.contains("rouge")){
            gv.player.color = Color.RED;
        }
        if(liveSpeechResult.contains("bleu")){
            gv.player.color = Color.BLUE;
        }
        droidSpeech.closeDroidSpeechOperations();
        droidSpeech.startDroidSpeechRecognition();
        Log.i(TAG, "Live speech result = " + liveSpeechResult);
    }

    @Override
    public void onDroidSpeechFinalResult(String finalSpeechResult)
    {
        Log.i(TAG,"final");
    }

    @Override
    public void onDroidSpeechClosedByUser()
    {
        Log.i(TAG,"close");
    }

    @Override
    public void onDroidSpeechError(String errorMsg)
    {
        // Speech error
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        Log.i(TAG,errorMsg);

    }

    // MARK: DroidSpeechPermissionsListener Method

    @Override
    public void onDroidSpeechAudioPermissionStatus(boolean audioPermissionGiven, String errorMsgIfAny)
    {
        Log.i("DEBUG", "PASSAGE");
        if(audioPermissionGiven)
        {

        }
        else
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
            if(errorMsgIfAny != null)
            {
                // Permissions error
                Toast.makeText(this, errorMsgIfAny, Toast.LENGTH_LONG).show();
            }

        }
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
        }

    }
}
