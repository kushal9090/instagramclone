package com.kushal.instagram.singleuserinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.kushal.instagram.R;
import com.kushal.instagram.models.Post;
import com.kushal.instagram.models.SingleUserpost;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingleUserInfoActivity extends AppCompatActivity {

    public static  String EXTRA_INFO = "users_post";
    private RecyclerView singleRecycler;
    private Post post ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user_info);

        //catching parcables
        Bundle bundle = getIntent().getExtras();
        post = bundle.getParcelable(EXTRA_INFO);


        loadData();
        initView();
    }

    private void initView() {
        initData();
        initRecycler();
    }
    private TextView nameTV;
    private CircleImageView profilepic;
    private void initData() {
        nameTV = (TextView) findViewById(R.id.nameSingle);
        nameTV.setText(post.getUid());

        profilepic = (CircleImageView) findViewById(R.id.dp);
        if(!TextUtils.isEmpty(post.getProfilePic())){
            Picasso.with(profilepic.getContext()).load(post.getPicuri()).into(profilepic);
        }


    }

    private void initRecycler() {
        singleRecycler = (RecyclerView) findViewById(R.id.singleRecycler);
        singleRecycler.setLayoutManager(new GridLayoutManager(SingleUserInfoActivity.this , 4 ));

    }
    private FirebaseRecyclerAdapter<SingleUserpost , SingleUserViewHolder> mAdapter;
    private void loadData(){
        DatabaseReference data = FirebaseDatabase.getInstance().getReference();

        String uid = post.getUid();
        Query query = data.child("users_post").child(uid);

        mAdapter = new FirebaseRecyclerAdapter<SingleUserpost, SingleUserViewHolder>(
                SingleUserpost.class,
                R.layout.item_single,
                SingleUserViewHolder.class ,
                query
        ) {
            @Override
            protected void populateViewHolder(final SingleUserViewHolder viewHolder,final SingleUserpost singleUserpost,final int position) {

                viewHolder.bindto(singleUserpost, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }


        };

        singleRecycler.setAdapter(mAdapter);
    }

}
