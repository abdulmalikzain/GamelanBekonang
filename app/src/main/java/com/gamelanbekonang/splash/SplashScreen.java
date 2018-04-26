package com.gamelanbekonang.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gamelanbekonang.MainActivity;
import com.gamelanbekonang.R;

public class SplashScreen extends AppCompatActivity {
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        iv = findViewById(R.id.iv);
        Animation myanim = new AnimationUtils().loadAnimation(this,R.anim.mytransitiont);
        iv.startAnimation(myanim);
<<<<<<< HEAD
        final Intent i = new Intent(this, MainActivity.class);
=======
        final Intent i = new Intent(SplashScreen.this, MainActivity.class);
>>>>>>> coba
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000) ;
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }

            }
        };
        timer.start();
    }
}
