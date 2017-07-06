package com.kushal.instagram.postadd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kushal.instagram.R;
import com.kushal.instagram.homescreens.HomeScreenActivity;
import com.kushal.instagram.homescreens.PostFragment;

public class PostAddActivity extends AppCompatActivity {

    private EditText mPostTitle , mPostPicUri;
    private Button mPostButton;
    private DatabaseReference mdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);

        mPostButton = (Button) findViewById(R.id.postbutton);

        mdata = FirebaseDatabase.getInstance().getReference().child("post").push();

        mPostTitle = (EditText) findViewById(R.id.postTitle);
        mPostPicUri = (EditText) findViewById(R.id.postpicuri);

        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });

    }
    private void post() {

        String title = mPostTitle.getText().toString();
        String picuri = mPostPicUri.getText().toString();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String current_user = mAuth.getCurrentUser().getEmail();

        mdata.child("email").setValue(current_user);
        mdata.child("posttitle").setValue(title);
        mdata.child("picuri").setValue(picuri);
        mPostTitle.setText("");
        mPostPicUri.setText("");
        Intent back = new Intent(PostAddActivity.this , HomeScreenActivity.class);
        startActivity(back);
        Toast.makeText(PostAddActivity.this , "your post has been added" , Toast.LENGTH_LONG).show();
        finish();
        return;
    }
}
