package com.example.android.door_unlock.Screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.android.door_unlock.R;


public class MainActivity extends AppCompatActivity {

    private String username="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = this.getSharedPreferences("my_prefs",MODE_PRIVATE);
        username = preferences.getString("Username",null);
        final ImageView imageView = (ImageView)findViewById(R.id.splash_image);
        final Animation animation1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation animation2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.antirotate);
        final Animation animation3 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);

        imageView.startAnimation(animation2);
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setAnimation(animation3);
                finish();
                if (username == null){
                    Intent intent = new Intent(MainActivity.this,Sign_In.class);
                    startActivity(intent);
                     }
                else {
                    Intent intent = new Intent(MainActivity.this,MachineActivated.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
