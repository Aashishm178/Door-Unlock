package com.example.android.door_unlock.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.door_unlock.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Lock_Door extends AppCompatActivity {

    String machine_code;
    boolean gateopened=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock__door);

        SharedPreferences sharedPreferences = this.getSharedPreferences("my_prefs",MODE_PRIVATE);
        machine_code = sharedPreferences.getString("machine code",null);

        Button button = findViewById(R.id.unlock_door);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               buttonpressed(machine_code,true);
                if (gateopened)
                    //Toast.makeText(getApplicationContext(),"Gate is opened",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonpressed(machine_code,false);
                        gateopened = false;
                       // Toast.makeText(getApplicationContext(),"Gate is locked",Toast.LENGTH_SHORT).show();
                    }
                },5000);
            }
        });
    }

    private void buttonpressed(String mac_address, final boolean button) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("admin");
        reference.child(mac_address).setValue(button);
        gateopened = true;
    }
}
