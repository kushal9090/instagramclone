package com.kushal.instagram.about;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kushal.instagram.R;
import com.kushal.instagram.models.Following;
import com.kushal.instagram.models.SingleUserpost;
import com.kushal.instagram.models.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutActivity extends AppCompatActivity {

    private CircleImageView profileimg;
    private TextView followingtv , nameTv , countFollowing , bio , editprofile;
    private RecyclerView aboutRecycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
         
        
        initRecycler();
        showUserPics();
        bio = (TextView) findViewById(R.id.bio);
        profileimg = (CircleImageView) findViewById(R.id.profile_image);
        followingtv = (TextView) findViewById(R.id.followtv);
        countFollowing = (TextView) findViewById(R.id.countFollowing);
        nameTv = (TextView) findViewById(R.id.nameTV);
        editprofile = (TextView) findViewById(R.id.editprofiletv);
        FirebaseAuth auth =  FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User name = dataSnapshot.getValue(User.class);
                bio.setText(name.getBio());
                nameTv.setText(name.getDisplayName());
               if (!TextUtils.isEmpty(name.getProfilePic())){
                   Picasso.with(profileimg.getContext()).load(name.getProfilePic()).into(profileimg);
               }
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
     editprofile.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent kk = new Intent(AboutActivity.this , EditProfileActivity.class);
             startActivity(kk);

         }
     });

    }


    private void initRecycler() {
        aboutRecycle = (RecyclerView) findViewById(R.id.aboutRecycle);
        aboutRecycle.setLayoutManager(new GridLayoutManager(AboutActivity.this , 4));
    }

    private FirebaseRecyclerAdapter<SingleUserpost , AboutViewHolder> adap;
    private void showUserPics() {
        DatabaseReference userpostnode = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query singleuserpost = userpostnode.child("users_post").child(uid);

        adap = new FirebaseRecyclerAdapter<SingleUserpost, AboutViewHolder>(SingleUserpost.class , R.layout.item_about , AboutViewHolder.class , singleuserpost) {
            @Override
            protected void populateViewHolder(final AboutViewHolder viewHolder,final SingleUserpost singleUserpost,final int position) {

                viewHolder.bind(singleUserpost, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        };

      aboutRecycle.setAdapter(adap);
    }
}
