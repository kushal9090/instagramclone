package com.kushal.instagram.search;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.kushal.instagram.R;
import com.kushal.instagram.models.Post;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by kusha on 7/9/2017.
 */

public class SearchViewHolder extends RecyclerView.ViewHolder {

    private ImageView searcchpic;

    public SearchViewHolder(View itemView) {
        super(itemView);

        searcchpic  = (ImageView) itemView.findViewById(R.id.searchPic);

    }

    public void bind(Post post  , View.OnClickListener click){

        if(!TextUtils.isEmpty(post.getPicuri())){
            Picasso.with(searcchpic.getContext()).load(post.getPicuri()).into(searcchpic);
        }
    }
}


