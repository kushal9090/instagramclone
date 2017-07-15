package com.kushal.instagram.mesagescreen;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kushal.instagram.R;
import com.kushal.instagram.models.Following;
import com.kushal.instagram.models.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kusha on 7/9/2017.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {
    private TextView name ;
    private CircleImageView pic;
   // private TextView lastmsg;

    public MessageViewHolder(View itemView) {
        super(itemView);
        pic = (CircleImageView) itemView.findViewById(R.id.pic);
        name = (TextView) itemView.findViewById(R.id.messagenametv);
      //  lastmsg = (TextView) itemView.findViewById((R.id.lastmessage);
    }

    public void bind(Following follow, View.OnClickListener onlik){

        name.setText(follow.getFollowingname());

        if(!TextUtils.isEmpty(follow.getDp())){

            Picasso.with(pic.getContext()).load(follow.getDp()).into(pic);
        }
    }
}
