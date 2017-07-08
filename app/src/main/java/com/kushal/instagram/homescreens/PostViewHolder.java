package com.kushal.instagram.homescreens;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.kushal.instagram.R;
import com.kushal.instagram.models.Post;
import com.squareup.picasso.Picasso;

/**
 * Created by kusha on 7/6/2017.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {
      private DatabaseReference mdatabase;
      private ImageView imagepost;
      private TextView posttitle;
      private TextView nameTv;
    public PostViewHolder(View itemView) {
        super(itemView);
        nameTv = (TextView) itemView.findViewById(R.id.nametv);
        imagepost = (ImageView) itemView.findViewById(R.id.imagepost);
        posttitle = (TextView) itemView.findViewById(R.id.postTitle);
    }

  public void bindToPost(Post post , View.OnClickListener startCLickListener){

      nameTv.setText(post.getEmail());
      posttitle.setText(post.getPosttitle());

      if(!TextUtils.isEmpty(post.getPicuri())){

          Picasso.with(imagepost.getContext()).load(post.getPicuri()).fit().centerCrop().into(imagepost);
      }
  }
}
