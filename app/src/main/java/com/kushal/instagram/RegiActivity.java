package com.kushal.instagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kushal.instagram.homescreens.HomeScreenActivity;
import com.kushal.instagram.homescreens.PostFragment;
import com.kushal.instagram.models.Post;

public class RegiActivity extends AppCompatActivity {
    private EditText email , pass ;
    private Button sigupbtn , loginbtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi);

        mPro = new ProgressDialog(this);

        email = (EditText) findViewById(R.id.emailtb);
        pass = (EditText) findViewById(R.id.passtb);

        sigupbtn = (Button) findViewById(R.id.signuptbn);
        loginbtn = (Button) findViewById(R.id.loginbtn);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sigupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               signupact();
              //  Intent intent = new Intent(RegiActivity.this , HomeScreenActivity.class);
                // startActivity(intent);

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

        mPro.setMessage("signing up please wait...");
        mPro.show();
        final String emailad = email.getText().toString().trim();
        String password = pass.getText().toString().trim();



        mAuth.createUserWithEmailAndPassword(emailad , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Post post = new Post();
                if(task.isSuccessful()){

                    mPro.dismiss();
                    String uid = mAuth.getCurrentUser().getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                    mDatabase.child("name").setValue(emailad);
                    mDatabase.child("uid").setValue(uid);
                  // mDatabase.child("posts").push().setValue(post.getPicuri());
                    Intent intent = new Intent(RegiActivity.this , HomeScreenActivity.class);
                    startActivity(intent);


                    Toast.makeText(RegiActivity.this, "successful....", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }else{
                    mPro.dismiss();
                    Toast.makeText(RegiActivity.this, "unsuccessful..", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    private void loginact() {

        mPro.setMessage("please wait..");
        mPro.setCancelable(false);
        mPro.show();
        String emailad = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailad , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mPro.dismiss();
                    Intent intent = new Intent(RegiActivity.this , HomeScreenActivity.class);
                    startActivity(intent);

                    Toast.makeText(RegiActivity.this, "successful....", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }else{
                    mPro.dismiss();
                    Toast.makeText(RegiActivity.this, "unsuccessful....", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }


}
