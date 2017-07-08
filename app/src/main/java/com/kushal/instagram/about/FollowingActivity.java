package com.kushal.instagram.about;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.kushal.instagram.R;
import com.kushal.instagram.models.Following;

public class FollowingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
        initView();
        showFollowing();
    }


    private void initView() {
        initRecycle();
    }
     private RecyclerView recycle;
    private void initRecycle() {

         recycle = (RecyclerView)  findViewById(R.id.followRecycler);
        recycle.setLayoutManager(new LinearLayoutManager(this));

    }
    private FirebaseRecyclerAdapter<Following , FollowingViewHolder> adapte;
    private void showFollowing() {
        DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        Query followQuery = mdatabase.child("follow").child(uid);

        adapte = new FirebaseRecyclerAdapter<Following, FollowingViewHolder>(Following.class , R.layout.item_follow , FollowingViewHolder.class , followQuery) {
            @Override
            protected void populateViewHolder(final FollowingViewHolder viewHolder,final Following follow,final int position) {


                viewHolder.bind(follow, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


            }
        };
        recycle.setAdapter(adapte);
    }

}
