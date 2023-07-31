package com.example.task1_flashlightapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton imageButton;
    boolean flashOn = false;
    boolean cameraFlash = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.imageflash);

        cameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cameraFlash){
                        if(flashOn){
                            flashOn = false;
                            imageButton.setImageResource(R.drawable.flashlight_off);
                            cameraFlashLight(false);
                            Toast.makeText(MainActivity.this, "Flashlight is OFF", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            flashOn = true;
                            imageButton.setImageResource(R.drawable.flashlight_on);
                            cameraFlashLight(true);
                            Toast.makeText(MainActivity.this, "Flashlight is ON", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(MainActivity.this, "No flash available on your device", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void cameraFlashLight(boolean flash){
        CameraManager cameraManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            assert cameraManager != null;
            String cameraId = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && flash==false)
                cameraManager.setTorchMode(cameraId, false);
            else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && flash==true)
                cameraManager.setTorchMode(cameraId, true);
        }
        catch(CameraAccessException e){
            Log.e("Camera Problem", "Cannot turn off camera flashlight");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPause()
    {
        super.onPause();
        Toast.makeText(MainActivity.this, "Flashlight is OFF", Toast.LENGTH_SHORT).show();
        flashOn = false;
        imageButton.setImageResource(R.drawable.flashlight_off);
        cameraFlashLight(false);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(MainActivity.this, "Flashlight is ON", Toast.LENGTH_SHORT).show();
        flashOn = false;
        imageButton.setImageResource(R.drawable.flashlight_on);
        cameraFlashLight(false);
    }
}