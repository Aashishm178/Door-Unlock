package com.example.android.door_unlock.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.door_unlock.R;
import com.example.android.door_unlock.model.DataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sign_In extends AppCompatActivity {

    String email_address_sign_in="";
    String email_password_sign_in="";
    String machine_code;
    EditText editText_email_address;
    EditText editText_email_password;
    Boolean email_entered=false,password_entered=false;
    NetworkInfo networkInfo;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in);

        progressDialog = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        progressDialog.setTitle("Authenticating.....");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);


        TextView no_account_yet = findViewById(R.id.no_account_yet);
        no_account_yet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Sign_In.this,Create_Account_Or_Google.class);
                startActivity(i);

            }
        });

        Button button = findViewById(R.id.login_button_sign_in);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText_email_address = findViewById(R.id.email_address_sign_in);
                email_address_sign_in = editText_email_address.getText().toString();
                if (!email_address_sign_in.equals(""))
                    email_entered=true;

                editText_email_password = findViewById(R.id.email_password_sign_in);
                email_password_sign_in = editText_email_password.getText().toString();
                if (!email_password_sign_in.equals(""))
                    password_entered=true;

                if (email_entered && password_entered)
                {
                    //Toast.makeText(getApplicationContext(),"Both are entered",Toast.LENGTH_SHORT).show();
                    progressDialog.show();
                    firebaseauthentication(email_address_sign_in,email_password_sign_in);
                }

                if(!email_entered) {
                    editText_email_address.setError("Enter username");
                    editText_email_address.setHint("Enter username");
                }

                if(!password_entered) {
                    editText_email_password.setError("Enter password");
                    editText_email_password.setHint("Enter password");
                }
            }
        });
    }

    private void firebaseauthentication(final String USERNAME, final String PASSWORD){
        if (isNetworkAvailable()) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference().child("users");

            reference.orderByChild("mail_username").equalTo(USERNAME).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            DataModel dataModel = dataSnapshot1.getValue(DataModel.class);
                            System.out.print(dataModel);
                            try {
                                if (dataModel.getPassword().equals(PASSWORD)) {
                                    progressDialog.dismiss();
                                    SharedPreferences sp = getSharedPreferences("my_prefs",Context.MODE_PRIVATE);
                                    sp.edit().putString("Username",USERNAME).apply();
                                    machine_code = dataModel.getMachine_code();
                                    Intent intent = new Intent(Sign_In.this,MachineActivated.class);
                                    sp.edit().putString("machine code",dataModel.getMachine_code()).apply();
                                    startActivity(intent);
                                    //Toast.makeText(getApplicationContext(), "User Exist", Toast.LENGTH_SHoRT).show();
                                } else{
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_SHORT).show();
                                }
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else{
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "User do not exist", Toast.LENGTH_SHORT).show();}
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }else{
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_SHORT).show();
        }
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


