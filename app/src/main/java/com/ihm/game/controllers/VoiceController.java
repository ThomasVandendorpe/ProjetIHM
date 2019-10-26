package com.ihm.game.controllers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ihm.game.Input;
import com.ihm.game.MainActivity;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class VoiceController implements Controller, RecognitionListener {

    private static final String COULEUR_SEARCH = "couleur";
    private SpeechRecognizer recognizer;

    public VoiceController(MainActivity activity){

        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            return;
        }

        File assetsDir = null;
        try {
            Assets assets = new Assets(activity);
            assetsDir = assets.syncAssets();
        } catch (IOException e) {
        }

        System.out.println(assetsDir.getAbsolutePath());

        try {
            recognizer = SpeechRecognizerSetup.defaultSetup()
                    .setAcousticModel(new File(assetsDir, "fr-fr-ptm"))
                    .setDictionary(new File(assetsDir, "fr.dict"))
                    .setRawLogDir(assetsDir) // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                    .getRecognizer();
            recognizer.addListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create keyword-activation search.
       /* recognizer.addKeyphraseSearch(COULEUR_SEARCH, "rouge");
        recognizer.addKeyphraseSearch(COULEUR_SEARCH, "vert");*/

        // Create grammar-based search for digit recognition
        File digitsGrammar = new File(assetsDir, "nombres.gram");
        recognizer.addGrammarSearch(COULEUR_SEARCH, digitsGrammar);

        /*File phoneticModel = new File(assetsDir, "fr-phone.lm.dmp");
        recognizer.addAllphoneSearch(COULEUR_SEARCH, phoneticModel);*/

        recognizer.startListening(COULEUR_SEARCH);
    }

    @Override
    public void update() {

    }

    @Override
    public void onBeginningOfSpeech() {
        System.out.println("BEGINNING");
    }

    @Override
    public void onEndOfSpeech() {
        System.out.println("END");
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if(hypothesis!=null) {
            System.out.println("PROB:"+hypothesis.getProb());
            System.out.println("PARTIAL: " + hypothesis.getHypstr());
            if(hypothesis.getHypstr().contains("rouge")) {
                Input.setActionColor(Color.RED);
                recognizer.stop();
            }
            else if(hypothesis.getHypstr().contains("vert")) {
                Input.setActionColor(Color.GREEN);
                recognizer.stop();
            }
            else if(hypothesis.getHypstr().contains("bleu")) {
                Input.setActionColor(Color.BLUE);
                recognizer.stop();
            }
        }
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if(hypothesis!=null) {
            System.out.println("RESULT: " + hypothesis.getHypstr());
            recognizer.startListening(COULEUR_SEARCH);
        }
    }

    @Override
    public void onError(Exception e) {
        System.out.println("ERROR");
    }

    @Override
    public void onTimeout() {

    }
}
