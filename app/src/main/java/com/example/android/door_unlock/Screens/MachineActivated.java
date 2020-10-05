package com.example.android.door_unlock.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.door_unlock.R;
import com.example.android.door_unlock.fingerprint.Fingerprint;
import com.example.android.door_unlock.pin.FirstActivity;

public class MachineActivated extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_activated);

        TextView fingerprint = findViewById(R.id.fingerprint);
        fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MachineActivated.this, Fingerprint.class);
                startActivity(intent);
            }
        });

        TextView pin = findViewById(R.id.pin);
        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MachineActivated.this, FirstActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out:
                this.getSharedPreferences("my_prefs",0).edit().clear().apply();
                Intent i = new Intent(MachineActivated.this,Sign_In.class);
                finish();
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
