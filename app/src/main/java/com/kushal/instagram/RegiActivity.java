package com.kushal.instagram;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegiActivity extends AppCompatActivity {
    private EditText email , pass ;
    private Button sigupbtn , loginbtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi);

        email = (EditText) findViewById(R.id.emailtb);
        pass = (EditText) findViewById(R.id.passtb);

        sigupbtn = (Button) findViewById(R.id.signuptbn);
        loginbtn = (Button) findViewById(R.id.loginbtn);

        mAuth = FirebaseAuth.getInstance();

        sigupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupact();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginact();
            }
        });

    }

    private void signupact() {
        String emailad = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(emailad , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(RegiActivity.this, "successful....", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    private void loginact() {
        String emailad = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailad , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegiActivity.this, "successful....", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }


}
