package com.ihm.game;

import android.app.Activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ihm.game.controllers.VoiceController;


public class MainActivity extends Activity  {

    public static Point screenSize;
    public GameView gv;
    private static TextView tv;

    public static AssetManager assetManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        screenSize = new Point();
        getWindowManager(). getDefaultDisplay().getSize(screenSize);

        assetManager = getAssets();

        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivityAccVoc.class));
            }
        }));

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        }));


        tv = findViewById(R.id.nbCouleurs);
        SeekBar sb = findViewById(R.id.seekBar);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            // When Progress value changed.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                tv.setText("" + (progress+3) + " Couleurs");
            }

            // Notification that the user has started a touch gesture.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            // Notification that the user has finished a touch gesture
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv.setText("" + (progress+3) + " Couleurs");
                Couleur.nbCouleurs = progress+3;
            }
        });

        if(!VoiceController.gotPermission(this))
            VoiceController.askPermission(this);
    }

}
