package com.example.android.door_unlock.fingerprint;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.android.door_unlock.Screens.Lock_Door;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerprintHandler(Context context) {
        this.context = context;
    }

    public void startAuth(FingerprintManager manager,FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        manager.authenticate(cryptoObject,cancellationSignal,0,this,null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        Toast.makeText(context,"Fingerprint Authentication error\n" + errString,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        Toast.makeText(context,"Fingerprint Authentication help\n" + helpString,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        //Toast.makeText(context,"Fingerprint Authentication succeeded.",Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context, Lock_Door.class));
        ((Activity)context).finish();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context,"Fingerprint Authentication failed.",Toast.LENGTH_LONG).show();
    }
}
