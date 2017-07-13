package com.kushal.instagram.postadd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.kushal.instagram.R;

import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {
    
    private EditText phoneET;
    private Button phoneBtn;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone);
        
        phoneET = (EditText) findViewById(R.id.phonET);
        phoneBtn = (Button) findViewById(R.id.phonebtn);
        
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneOTP();
            }
        });
    }


    private void phoneOTP() {

        String phonenumber = phoneET.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
        };
    }
}
