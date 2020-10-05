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

public class ReenterActivity extends AppCompatActivity {


    String pin_from_first_screen;
    String finalpin = "";
    String newpin = "";
    EditText editText;
    public static final String SETTING = "SETTING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reenter);

        Bundle intent = getIntent().getExtras();
        try {
            pin_from_first_screen = intent.getString("final_pin_to_be_passed");
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        TextView textView1 = findViewById(R.id.number_1_second_screen);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("1");
            }
        });

        TextView textView2 = findViewById(R.id.number_2_second_screen);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("2");
            }
        });

        TextView textView3 = findViewById(R.id.number_3_second_screen);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("3");
            }
        });

        TextView textView4 = findViewById(R.id.number_4_second_screen);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("4");
            }
        });

        TextView textView5 = findViewById(R.id.number_5_second_screen);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("5");
            }
        });

        TextView textView6 = findViewById(R.id.number_6_second_screen);
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("6");
            }
        });

        TextView textView7 = findViewById(R.id.number_7_second_screen);
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("7");
            }
        });

        TextView textView8 = findViewById(R.id.number_8_second_screen);
        textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("8");
            }
        });

        TextView textView9 = findViewById(R.id.number_9_second_screen);
        textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("9");
            }
        });

        TextView textView0 = findViewById(R.id.number_0_second_screen);
        textView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setText("0");
            }
        });

        TextView delete = findViewById(R.id.delete_second_screen);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeText();
            }
        });

        TextView ok = findViewById(R.id.ok_second_screen);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (finalpin.equals(pin_from_first_screen))
                {
                    Intent i = new Intent(ReenterActivity.this, Lock_Door.class);
                    finish();
                    editText.setText("");
                    SharedPreferences settings = getSharedPreferences(SETTING,MODE_PRIVATE);
                    SharedPreferences.Editor editor;
                    editor = settings.edit();
                    editor.putString("password",finalpin);
                    editor.apply();
                    finalpin="";
                    startActivity(i);
                }
                else {

                    Toast.makeText(getApplicationContext(),"PIN DO NOT MATCH",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setText(String pin) {
        if (finalpin.length() < 6) {
            finalpin = finalpin + pin;
            editText = findViewById(R.id.edit_view_second_screen);
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
