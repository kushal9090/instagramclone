package com.kushal.instagram.commentscreen;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kushal.instagram.R;
import com.kushal.instagram.models.Comments;
import com.kushal.instagram.models.Post;
import com.kushal.instagram.models.User;

import java.util.ArrayList;
import java.util.List;

public class CommentScreen extends AppCompatActivity {
   public static  String EXTRA_DATA = "post";
    private Post mPost;
    private RecyclerView commnetRecycler;
    private String mPostKey;
    private DatabaseReference mCommentdb;
    private FirebaseAuth mAuth;
    private TextView ok ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_screen);
        Bundle bundle = getIntent().getExtras();
        mPost = bundle.getParcelable(EXTRA_DATA);
        getIntent().removeExtra(EXTRA_DATA);
        getIntent().setAction("");
        getIntent().replaceExtras(new Bundle());
        getIntent().setData(null);
        getIntent().setFlags(0);
        initView();
        loadComments();
    }
    private EditText commentET;
    private Button commentBtn;
    private  void initView(){

        commentET = (EditText) findViewById(R.id.commentET);
        commentBtn = (Button) findViewById(R.id.commentSend);

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadComment();
            }
        });

        initRecycler();
    }

    private void uploadComment() {
        final DatabaseReference post = FirebaseDatabase.getInstance().getReference().child("post");
        post.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                    String obj = objSnapshot.getKey();

                    mCommentdb = FirebaseDatabase.getInstance().getReference().child("comments").child(obj).push();
                   // mCommentdb.child("postkey").setValue(obj);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final String cmntet = commentET.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        final DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User users = dataSnapshot.getValue(User.class);
                //String author , profilePic , commnet ;
                mCommentdb.child("author").setValue(users.getDisplayName());
                mCommentdb.child("profilePic").setValue(users.getProfilePic());
                mCommentdb.child("comment").setValue(cmntet);
                commentET.setText("");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void initRecycler() {

        commnetRecycler = (RecyclerView) findViewById(R.id.commentRecycler);
        commnetRecycler.setLayoutManager(new LinearLayoutManager(this));
    }


 private Post com;
   private FirebaseRecyclerAdapter<Comments , CommentViewHolder> mAdapter;
    private void loadComments() {


        final DatabaseReference commentss= FirebaseDatabase.getInstance().getReference();
        final Query commentQuery = commentss.child("comments").child(mPost.getKey()
        );

        mAdapter = new FirebaseRecyclerAdapter<Comments, CommentViewHolder>(Comments.class , R.layout.item_comment , CommentViewHolder.class , commentQuery) {
            @Override
            protected void populateViewHolder(final CommentViewHolder viewHolder,final Comments comments,final int position) {

                viewHolder.bind(comments, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        };
        commnetRecycler.setAdapter(mAdapter);
    }
}
