package com.miHoYo.bh3.visualnovels;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.database.ContentObserver;
import android.content.ContentResolver;
import android.view.View;
import android.view.Window;
import android.content.pm.ActivityInfo;
import android.view.Surface;
import org.apache.cordova.CordovaActivity;

public class MainActivity extends CordovaActivity {

    private RotationObserver mRotationObserver;

    private class RotationObserver extends ContentObserver {
        ContentResolver mResolver;

        public RotationObserver(Handler handler) {
            super(handler);
            mResolver = getContentResolver();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            setScreenOrientation();
        }

        public void startObserver() {
            mResolver.registerContentObserver(
                    Settings.System.getUriFor(Settings.System.ACCELEROMETER_ROTATION),
                    false, this
            );
        }

        public void stopObserver() {
            mResolver.unregisterContentObserver(this);
        }
    }

    private void setScreenOrientation() {
        try {
            int rotationEnabled = Settings.System.getInt(getContentResolver(),
                    Settings.System.ACCELEROMETER_ROTATION);
            if (rotationEnabled == 1) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else {
                int currentRotation = getWindowManager().getDefaultDisplay().getRotation();
                if (currentRotation == Surface.ROTATION_90) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else if (currentRotation == Surface.ROTATION_270) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }

        Window window = this.getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );

        mRotationObserver = new RotationObserver(new Handler());
        setScreenOrientation();

        loadUrl(launchUrl);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRotationObserver != null) {
            mRotationObserver.stopObserver();
        }
        if (appView != null) {
            String js = "javascript:(function() {" +
                    "var audios = document.querySelectorAll('audio');" +
                    "for(var i=0;i<audios.length;i++){" +
                    "   if(!audios[i].paused){" +
                    "       audios[i].pause();" +
                    "       audios[i].setAttribute('data-was-playing','true');" +
                    "   }" +
                    "}" +
                    "})()";
            appView.loadUrl(js);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRotationObserver != null) {
            mRotationObserver.startObserver();
            setScreenOrientation();
        }
        if (appView != null) {
            String js = "javascript:(function() {" +
                    "var audios = document.querySelectorAll('audio');" +
                    "for(var i=0;i<audios.length;i++){" +
                    "   if(audios[i].getAttribute('data-was-playing') === 'true'){" +
                    "       audios[i].play().catch(function(e){});" +
                    "       audios[i].removeAttribute('data-was-playing');" +
                    "   }" +
                    "}" +
                    "})()";
            appView.loadUrl(js);
        }
    }
}