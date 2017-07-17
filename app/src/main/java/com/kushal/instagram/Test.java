package com.kushal.instagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.kushal.instagram.models.Post;
import com.kushal.instagram.models.SingleUserpost;
import com.kushal.instagram.singleuserinfo.SingleUserViewHolder;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Test extends AppCompatActivity {
   public static   String EXTRA_INFO = "users";
    private EditText vet;
    private Button vbtn;
    private Post post;
    private RecyclerView posts;
    private CircleImageView dp;

    private TextView namet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Bundle bundle = getIntent().getExtras();
        post = bundle.getParcelable(EXTRA_INFO);

        namet = (TextView) findViewById(R.id.nametest);
        namet.setText(post.getDisplayName());

        dp =(CircleImageView) findViewById(R.id.dp);
        if(!TextUtils.isEmpty(post.getProfilePic())){
            Picasso.with(dp.getContext()).load(post.getProfilePic()).into(dp);
        }

        posts = (RecyclerView) findViewById(R.id.recyc);
        posts.setLayoutManager(new GridLayoutManager(Test.this , 4));

   load();
    }
    private FirebaseRecyclerAdapter<SingleUserpost , SingleUserViewHolder> mAdapter;
    private void load() {
        DatabaseReference data = FirebaseDatabase.getInstance().getReference();

        String uid = post.getUid();
        Query q = data.child("users_post").child(uid);

        mAdapter = new FirebaseRecyclerAdapter<SingleUserpost, SingleUserViewHolder>(SingleUserpost.class , R.layout.item_single , SingleUserViewHolder.class , q) {
            @Override
            protected void populateViewHolder(final SingleUserViewHolder viewHolder, final SingleUserpost singleUserpost,final int position) {

                viewHolder.bindto(singleUserpost, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        };

        posts.setAdapter(mAdapter);
    }


}
