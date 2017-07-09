package com.kushal.instagram.mesagescreen;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kushal.instagram.R;
import com.kushal.instagram.models.Following;
import com.kushal.instagram.models.User;

/**
 * Created by kusha on 7/9/2017.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {
    private TextView name ;
   // private TextView lastmsg;

    public MessageViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.messagenametv);
      //  lastmsg = (TextView) itemView.findViewById((R.id.lastmessage);
    }

    public void bind(Following follow, View.OnClickListener onlik){

        name.setText(follow.getFollowingname());
    }
}
