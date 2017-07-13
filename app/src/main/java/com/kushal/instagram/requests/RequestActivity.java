package com.kushal.instagram.requests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kushal.instagram.R;
import com.kushal.instagram.models.Requests;
import com.kushal.instagram.models.User;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        initview();
        loadReq();
    }



    private void initview() {

        initRecycler();
    }
     private RecyclerView reqRecycler;
    private void initRecycler() {
        reqRecycler = (RecyclerView) findViewById(R.id.reqrecycler);
        reqRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
   private FirebaseRecyclerAdapter<Requests , RequestVH> mAdapter;
    private void loadReq() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String cuid = mAuth.getCurrentUser().getUid();
        final DatabaseReference request = FirebaseDatabase.getInstance().getReference();
        Query reqQ = request.child("request").child(cuid);

        mAdapter = new FirebaseRecyclerAdapter<Requests, RequestVH>(Requests.class , R.layout.item_requests , RequestVH.class , reqQ) {
            @Override
            protected void populateViewHolder(final RequestVH viewHolder,final Requests req,final int position) {
                final DatabaseReference postRef = getRef(position);

                final String reqRef = postRef.getKey();

                viewHolder.followback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        request.child("request").child(cuid).child(reqRef).removeValue();

                        DatabaseReference followback = FirebaseDatabase.getInstance().getReference().child("follow").child(cuid).push();
                        followback.child("followingid").setValue(req.getUid());
                        followback.child("followingname").setValue(req.getName());
                        followback.child("dp").setValue(req.getDp());
                        followback.child("state").setValue("following");


                    }
                });

                viewHolder.bind(req, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        };
       reqRecycler.setAdapter(mAdapter);

    }
}
