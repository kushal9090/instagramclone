package com.kushal.instagram.commentscreen;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kushal.instagram.R;
import com.kushal.instagram.models.Comments;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kusha on 7/10/2017.
 */

public class CommentViewHolder  extends RecyclerView.ViewHolder {

    CircleImageView profilepic;
    TextView commentTV;
    TextView name;
    public CommentViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        profilepic = (CircleImageView) itemView.findViewById(R.id.profile_image_comment);
        commentTV = (TextView) itemView.findViewById(R.id.commentTV);
    }

    public void bind(Comments comments , View.OnClickListener clickListener){

        name.setText(comments.getAuthor());
        commentTV.setText(comments.getComment());
        if(!TextUtils.isEmpty(comments.getProfilePic())){
            Picasso.with(profilepic.getContext()).load(comments.getProfilePic()).into(profilepic);
        }
    }
}
