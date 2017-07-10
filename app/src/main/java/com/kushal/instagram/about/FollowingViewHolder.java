package com.kushal.instagram.about;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kushal.instagram.R;
import com.kushal.instagram.models.Following;

/**
 * Created by kusha on 7/9/2017.
 */

public class FollowingViewHolder extends RecyclerView.ViewHolder {

    private TextView namefollow , state;
    public FollowingViewHolder(View itemView) {
        super(itemView);
        namefollow = (TextView) itemView.findViewById(R.id.namefollow);
        state = (TextView) itemView.findViewById(R.id.state);

    }

    public void bind(Following follow , View.OnClickListener listner){

        namefollow.setText(follow.getFollowingname());
        state.setText(follow.getState());


    }
}
