package com.kushal.instagram.homescreens;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kushal.instagram.R;
import com.kushal.instagram.models.Story;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kusha on 7/14/2017.
 */



public class StoryHolder extends RecyclerView.ViewHolder {

    CircleImageView mStoryPic;
    TextView mName;
    public StoryHolder(View itemView) {
        super(itemView);
        mStoryPic = (CircleImageView) itemView.findViewById(R.id.storypic);
        mName = (TextView) itemView.findViewById(R.id.storyname);
    }

    public void bind(Story story , View.OnClickListener clickListener){

        if(!TextUtils.isEmpty(story.getDp())){
            Picasso.with(mStoryPic.getContext()).load(story.getDp()).into(mStoryPic);
        }
        mName.setText(story.getName());
    }
}
