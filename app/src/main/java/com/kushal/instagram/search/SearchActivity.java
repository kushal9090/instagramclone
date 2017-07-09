package com.kushal.instagram.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.kushal.instagram.R;
import com.kushal.instagram.models.Post;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchs);
        initView();
        showSearchImage();
    }


    private void initView() {
        initRecycler();
    }
    private RecyclerView searchRecycler;
    private void initRecycler() {
        searchRecycler = (RecyclerView) findViewById(R.id.searchRecycler);
        searchRecycler.setLayoutManager(new GridLayoutManager(SearchActivity.this , 4));
    }
    private FirebaseRecyclerAdapter<Post , SearchViewHolder> mAdapter;
    private void showSearchImage() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query postQuery = mDatabase.child("post");

        mAdapter = new FirebaseRecyclerAdapter<Post, SearchViewHolder>(Post.class , R.layout.item_search , SearchViewHolder.class , postQuery) {
            @Override
            protected void populateViewHolder(final SearchViewHolder viewHolder,final Post post,final int position) {

                viewHolder.bind(post, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        };

        searchRecycler.setAdapter(mAdapter);
     }

}
