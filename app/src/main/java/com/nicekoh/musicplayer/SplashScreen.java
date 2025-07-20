package com.nicekoh.musicplayer;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;


public class SplashScreen extends AppCompatActivity {

    int a=1;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_spash);
        Fullscreen();/*
        MobileAds.initialize(this);*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkPermission()) {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
                requestPermissions();
            }
        }, 2000);

    }

    public void Fullscreen() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(getVisibility());

    }

    private int getVisibility() {
        return 5894;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fullscreen();
    }

    private boolean checkPermission() {
        int music, video;
        int result;


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            music = ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_MEDIA_AUDIO);

        } else {
            result = ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            return (result == PackageManager.PERMISSION_GRANTED);
        }
        return (video == PackageManager.PERMISSION_GRANTED && music == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions() {
/*
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity1.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},30);

        } else
*/
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.POST_NOTIFICATIONS}, 10);

        }
        else
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {

            if(permissionChecker(grantResults))
                start();

            else if(a<=2 && !permissionChecker(grantResults)) {
                Toast.makeText(SplashScreen.this, "Permission is Required !!!", Toast.LENGTH_LONG).show();
                requestPermissions();
                a++;
            }
            else
                openSetting();

        }

            /* else if (a>2 && (!shouldShowRequestPermissionRationale(permissions[0]) && !shouldShowRequestPermissionRationale(permissions[1]))) {
                openSetting();
            }*/

    }
    public void start(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean checkApiLevel33(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            return true;
        else
            return false;
    }

    public boolean permissionChecker(int[] grantResults) {
        if (checkApiLevel33()) {
            return (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED);
        }
        else
            return grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }


    public void openSetting() {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            //  Uri uri=Uri.fromParts("package",MainActivity1.this.getPackageName(),null);
            intent.setData(Uri.parse("package:" + this.getPackageName()));
            // MainActivity1.this.startActivityForResult(intent,11);
            Toast toast;
            toast = Toast.makeText(this, "Please give the PERMISSION from settings to Play Videos and Musics", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            startActivity(intent);
        } finally {
            finish();
        }

    }
}
