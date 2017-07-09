package com.kushal.instagram.mesagescreen;

import android.content.Intent;
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
import com.kushal.instagram.models.Following;
import com.kushal.instagram.models.User;

public class MessageActivity extends AppCompatActivity {
  //  public static final String EXTRAS_USERS= "follow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    initView();
        showmessages();

    }
    private RecyclerView mRecycler;
    private void initView() {

        initRecycle();
    }

    private void initRecycle() {

        mRecycler = (RecyclerView) findViewById(R.id.messagerecycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
    private  DatabaseReference userdetails;
    private FirebaseRecyclerAdapter<Following , MessageViewHolder> madapter;
    private void showmessages() {

        final DatabaseReference followingnode = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query messageQuery = followingnode.child("follow").child(uid);

        madapter = new FirebaseRecyclerAdapter<Following, MessageViewHolder>(Following.class , R.layout.item_messagelist ,MessageViewHolder.class , messageQuery) {
            @Override
            protected void populateViewHolder(final MessageViewHolder viewHolder, final Following  follow, final int position) {

                viewHolder.bind(follow, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                     //   Intent intent = new Intent(MessageActivity.this ,  MessageScreen.class);
                       // intent.putExtra(MessageScreen.EXTRAS_USER , user);
                        //startActivity(intent);
                    }
                });

            }
        };

        mRecycler.setAdapter(madapter);
    }

}
