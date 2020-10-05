package com.example.android.door_unlock.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.door_unlock.R;
import com.example.android.door_unlock.model.DataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Form_Fill_Up extends AppCompatActivity  {

    String first_name,last_name,username,password,reenter_password,machine_code,email_id="null";
    String  regex = "^[a-zA-Z]*";
    Boolean first_name_entered=false,last_name_entered=false,username_entered=false,password_entered=false,machine_code_entered=false;
    Boolean reenter_password_entered=false;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DataModel dataModel = new DataModel();
    NetworkInfo networkInfo;
    EditText username_view;
    int changed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_fill_up);

        Button button = findViewById(R.id.submission_button_form_fill_up);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText first_name_view = findViewById(R.id.first_name_form_fill_up);
                first_name = first_name_view.getText().toString().trim();

                EditText last_name_view = findViewById(R.id.last_name_form_fill_up);
                last_name = last_name_view.getText().toString().trim();

                username_view = findViewById(R.id.user_name_form_fill_up);
                username = username_view.getText().toString().trim();

                EditText password_view = findViewById(R.id.password_form_fill_up);
                password = password_view.getText().toString();

                EditText reenter_password_view = findViewById(R.id.reenter_password_form_fill_up);
                reenter_password = reenter_password_view.getText().toString();

                EditText machine_code_view = findViewById(R.id.machine_code_form_fill_up);
                machine_code = machine_code_view.getText().toString().trim();


                if (first_name.equals("")) {
                    first_name_view.setError("Please Enter First Name");
                }
                else {
                    if(first_name.matches(regex))
                    first_name_entered = true;
                    else Toast.makeText(getApplicationContext(),"Name should Contain Alphabets Only",Toast.LENGTH_SHORT).show();
                }
                if (last_name.equals("")){
                    last_name_view.setError("Please Enter Last Name");
                }
                else {
                    if(last_name.matches(regex))
                    last_name_entered = true;
                    else Toast.makeText(getApplicationContext(),"Name should Contain Alphabets Only",Toast.LENGTH_SHORT).show();
                }


                if(reenter_password.equals("")){
                    reenter_password_view.setError("Please Reenter Password");
                }
                else reenter_password_entered = true;

                if(password.equals("")){
                    password_view.setError("Please Enter Password");
                }
                else password_entered = true;

                if (username.equals("")){
                    username_view.setError("Please Enter Username");
                }
                else username_entered = true;

                if(machine_code.equals("")){
                    machine_code_view.setError("Please Enter Machine Code");
                }
                else machine_code_entered=true;

                if(first_name_entered && last_name_entered && username_entered && password_entered
                && reenter_password_entered &&machine_code_entered )
                {

                    password = password_view.getText().toString();
                    reenter_password = reenter_password_view.getText().toString();

                    if(password.equals(reenter_password) ){
                    firebaseauthentication(username);
                }
                    else {

                        reenter_password_view.setError("Password do not match");
                        Toast.makeText(getApplicationContext(),"PASSWORD DO NOT MATCH",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    private void writeNewUser(String Username,String Id, String first_name,String last_name, String Password,String Machine_code){
        dataModel = new DataModel(Username,Id,first_name,last_name,Password,Machine_code);
        String key = mDatabase.push().getKey();
        mDatabase.child("users").child(key).setValue(dataModel);
        changed = 1;
    }

    private void firebaseauthentication(final String USERNAME){
        if (isNetworkAvailable()) {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference reference = database.getReference().child("users");

            reference.orderByChild("mail_username").equalTo(USERNAME).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (changed == 0) {
                        boolean exits = dataSnapshot.exists();
                        if (exits) {
                            Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_LONG).show();
                            username_view.setError("User exists");
                        } else {
                            writeNewUser(username, email_id, first_name, last_name, password, machine_code);
                            Intent intent = new Intent(Form_Fill_Up.this, Sign_In.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            }
                        }
                    }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }else
            Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_LONG).show();
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            networkInfo = manager.getActiveNetworkInfo();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return networkInfo != null && networkInfo.isConnected();
    }
}


