package com.bfurns.ziffylink;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bfurns.R;
import com.bfurns.activity.HomeActivity;
import com.bfurns.application.AppClass;
import com.bfurns.utility.CommonClass;
import com.squareup.picasso.Picasso;

/**
 * Created by Mahesh on 28/06/16.
 */
public class SplashScreenActivity extends AppCompatActivity {


    CommonClass commonClass;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.doctor_activity_splash);
        commonClass = new CommonClass(this);
        verifyStoragePermissions(this);

        ImageView  background=(ImageView)findViewById(R.id.image);

        Picasso.with(this)
                .load(R.drawable.doctorsplash)
                .resize(400,800)
                .centerCrop()
                .into(background);

        goTomain();
    }




   private void goTomain() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                if (AppClass.getuserId().length()>0) {

                    Intent loginIntent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(loginIntent);
                    finish();

                } else {
                    Intent loginIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }

            }
        }, 3000);
    }



    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }}
}
