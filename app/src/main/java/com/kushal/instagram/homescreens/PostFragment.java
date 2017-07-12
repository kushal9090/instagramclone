package com.kushal.instagram.homescreens;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.kushal.instagram.MainActivity;
import com.kushal.instagram.R;
import com.kushal.instagram.about.AboutActivity;
import com.kushal.instagram.commentscreen.CommentScreen;
import com.kushal.instagram.mesagescreen.MessageActivity;
import com.kushal.instagram.models.Post;
import com.kushal.instagram.postadd.PostAddActivity;
import com.kushal.instagram.search.SearchActivity;

/**
 * Created by kusha on 7/6/2017.
 */

public class PostFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_fragment , container , false);
    }
    EditText title;
    ImageButton postbtn;
    ImageButton msgBtn;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference postkey = FirebaseDatabase.getInstance().getReference().child("post");
        String key = postkey.getKey();

        initView();
        showPost();
    }
    private void initView() {
        initAbout();
        initPostbtn();
        initRecycler();
        initLogout();
        initMessages();
        initSearch();
    }

    private void initSearch() {

       ImageButton search = (ImageButton) getView().findViewById(R.id.searchIVbtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity() , SearchActivity.class);
                startActivity(i);
            }
        });

    }

    private void initMessages() {
        msgBtn = (ImageButton) getView().findViewById(R.id.imageButtonMsg);
        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dm = new Intent(getActivity() , MessageActivity.class);
                startActivity(dm);
            }
        });
    }

    private void initAbout() {
        ImageButton about = (ImageButton) getView().findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about = new Intent(getActivity() , AboutActivity.class);
                startActivity(about);
            }
        });
    }

    private ImageButton mLogbtn;
    private void initLogout() {

        mLogbtn = (ImageButton) getView().findViewById(R.id.logout);
        mLogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent lgout = new Intent(getActivity() , MainActivity.class);
                startActivity(lgout);
            }
        });
    }

    private void initPostbtn() {

      //   title = (EditText) getView().findViewById(R.id.titleET);

         postbtn = (ImageButton) getView().findViewById(R.id.postbtn);
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // String  post = title.getText().toString().trim();

                //DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("post").push();
                //db.child("posttitle").setValue(post);
                Intent i = new Intent(getActivity() , PostAddActivity.class );
                startActivity(i);

            }
        });

    }


    private RecyclerView mPostRecycler;
    private void initRecycler() {
        mPostRecycler = (RecyclerView) getView().findViewById(R.id.recyclerPost);
        //mPostRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mPostRecycler.setLayoutManager(linearLayoutManager);
    }

    private FirebaseRecyclerAdapter<Post , PostViewHolder> mAdapter;
    private void showPost() {
        DatabaseReference post = FirebaseDatabase.getInstance().getReference();
        Query postQuery = post.child("post");

        mAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class , R.layout.item_post , PostViewHolder.class , postQuery) {
            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder,final Post post,final int position) {


                //comment window..
                 viewHolder.comment.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent comments = new Intent(getActivity() , CommentScreen.class);

                         startActivity(comments);

                     }
                 });


                viewHolder.bindToPost(post, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                    }
                });
            }
        };
        mPostRecycler.setAdapter(mAdapter);

    }


}
