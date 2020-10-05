package com.example.android.door_unlock.pin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.door_unlock.Screens.Lock_Door;
import com.example.android.door_unlock.R;

public class FirstActivity extends AppCompatActivity {
    String finalpin = "";
    String newpin = "";
    int final_pin_length = 0;
    EditText editText;
    String pin="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        SharedPreferences preferences = this.getSharedPreferences("SETTING",MODE_PRIVATE);
        pin = preferences.getString("password",null);

        TextView textView1 = findViewById(R.id.number_1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setText("1");
            }
        });

        TextView textView2 = findViewById(R.id.number_2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("2");
            }
        });

        TextView textView3 = findViewById(R.id.number_3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("3");
            }
        });

        TextView textView4 = findViewById(R.id.number_4);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("4");
            }
        });

        TextView textView5 = findViewById(R.id.number_5);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("5");
            }
        });

        TextView textView6 = findViewById(R.id.number_6);
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("6");
            }
        });

        TextView textView7 = findViewById(R.id.number_7);
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("7");
            }
        });

        TextView textView8 = findViewById(R.id.number_8);
        textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("8");
            }
        });

        TextView textView9 = findViewById(R.id.number_9);
        textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("9");
            }
        });

        TextView textView0 = findViewById(R.id.number_0);
        textView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("0");
            }
        });

        TextView delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeText();
            }
        });

        TextView ok_button = findViewById(R.id.ok);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final_pin_length = finalpin.length();
                if ( pin!= null) {
                    if (finalpin.equals(pin)) {
                        editText.setText("");
                        finalpin = "";
                        Intent i = new Intent(FirstActivity.this, Lock_Door.class);
                        finish();
                        startActivity(i);
                    } else {

                        Toast.makeText(getApplicationContext(), "PIN DO NOT MATCH", Toast.LENGTH_LONG).show();
                    }

                } else {

                    if (final_pin_length >= 1) {
                        Intent i = new Intent(FirstActivity.this, ReenterActivity.class);
                        i.putExtra("final_pin_to_be_passed", finalpin);
                        editText.setText("");
                        finalpin = "";
                        finish();
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Enter a Pin", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }



    private void setText(String pin) {
        if (finalpin.length() < 6) {
            finalpin = finalpin + pin;
            editText = findViewById(R.id.edit_text_first_screen);
            editText.setText(finalpin);

        }
    }

    private void removeText() {
        if (finalpin.length() != 0) {
            newpin = finalpin.substring(0, finalpin.length() - 1);
            finalpin = "";
            setText(newpin);
        }
    }

}
