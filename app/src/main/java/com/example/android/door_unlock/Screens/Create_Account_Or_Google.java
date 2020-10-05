package com.example.android.door_unlock.Screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.door_unlock.R;
import com.example.android.door_unlock.model.DataModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Create_Account_Or_Google extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private static final String TAG2 = "Google";
    private static final String TAG3 = "USER DATA IN DATABASE";
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient mGoogleSignInClient;
    static final int RC_SIGN_IN = 1;
    DataModel dataModel = new DataModel();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__account__or__google);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        SignInButton signInButton =findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent SignInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(SignInIntent,RC_SIGN_IN);

            }
        });

        Button button = findViewById(R.id.create_account);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(Create_Account_Or_Google.this,Form_Fill_Up.class);
                startActivity(i);
            }
        });
    }

    public void  updateUI(GoogleSignInAccount googleSignInAccount)
    {
        if(googleSignInAccount != null) {

            if (googleSignInAccount.getGivenName() != null)
                dataModel.setFirst_name(googleSignInAccount.getGivenName());
            else Log.i(TAG2, "GIVEN NAME NOT AVAILABLE");
            if (googleSignInAccount.getFamilyName() != null)
                dataModel.setLast_name(googleSignInAccount.getFamilyName());
            else Log.i(TAG2, "FAMILY NAME NOT AVAILABLE");
            if (googleSignInAccount.getEmail() != null)
                dataModel.setMail_id(googleSignInAccount.getEmail());
            else Log.i(TAG2, "EMAIL NOT AVAILABLE");
            if (googleSignInAccount.getId() != null)
                dataModel.setMail_username(googleSignInAccount.getId());
            else Log.i(TAG2, "USER ID NOT AVAILABLE");
            dataModel.setPassword("null");
            mGoogleSignInClient.signOut();


            if (!dataModel.getMail_id().equals("") && !dataModel.getMail_username().equals("")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Create_Account_Or_Google.this);
                builder.setTitle("Enter Machine Code");
                final EditText input = new EditText(Create_Account_Or_Google.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataModel.setMachine_code(input.getText().toString().trim());
                        writeNewUser(dataModel.getMail_username(), dataModel.getMail_id(), dataModel.getFirst_name(), dataModel.getLast_name(), dataModel.getPassword(), dataModel.getMachine_code());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            } else {

                Toast.makeText(getApplicationContext(), "SOMETHING WRONG HERE (::) SORRY!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        }catch (ApiException e){

            Log.w(TAG,"SignInResult : failed code = "+ e.getStatusCode());
            updateUI(null);
        }
    }

    private void writeNewUser(String Username,String Id, String first_name,String last_name, String Password,String Machine_code){
        dataModel = new DataModel(Username,Id,first_name,last_name,Password,Machine_code);
        String key = mDatabase.push().getKey();
        mDatabase.child("users").child(key).setValue(dataModel);
        Log.i(TAG3,"YES");
        Intent intent = new Intent(Create_Account_Or_Google.this,MachineActivated.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
