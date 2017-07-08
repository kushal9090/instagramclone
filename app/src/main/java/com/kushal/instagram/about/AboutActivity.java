package com.kushal.instagram.about;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kushal.instagram.R;
import com.kushal.instagram.models.User;

public class AboutActivity extends AppCompatActivity {


    private TextView followingtv , nameTv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        followingtv = (TextView) findViewById(R.id.followtv);
        nameTv = (TextView) findViewById(R.id.nameTV);
        FirebaseAuth auth =  FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User name = dataSnapshot.getValue(User.class);

                nameTv.setText(name.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      followingtv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent f = new Intent(AboutActivity.this , FollowingActivity.class);
              startActivity(f);
          }
      });
    }
}
