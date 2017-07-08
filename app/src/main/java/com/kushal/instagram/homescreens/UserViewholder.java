package com.kushal.instagram.homescreens;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kushal.instagram.R;
import com.kushal.instagram.models.Following;
import com.kushal.instagram.models.Post;
import com.kushal.instagram.models.User;

/**
 * Created by kusha on 7/8/2017.
 */

public class UserViewholder extends RecyclerView.ViewHolder {

    TextView mEmail;
    TextView mTitle;
    Button mFollow;
    User mUser;
    Following f = new Following();
    public UserViewholder(View itemView) {
        super(itemView);

        mEmail = (TextView) itemView.findViewById(R.id.useremail);
        mTitle = (TextView) itemView.findViewById(R.id.posttitle);
        mFollow = (Button) itemView.findViewById(R.id.follow);

    }

    public void bind(User user , View.OnClickListener stat){

        mEmail.setText(user.getName());

    }
    public  void bindbtn(Following f , View.OnClickListener s){

        mFollow.setText(f.getState());
    }
}
