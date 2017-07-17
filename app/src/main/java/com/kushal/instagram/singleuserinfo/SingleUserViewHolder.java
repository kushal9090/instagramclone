package com.kushal.instagram.singleuserinfo;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.kushal.instagram.R;
import com.kushal.instagram.models.SingleUserpost;
import com.squareup.picasso.Picasso;

/**
 * Created by kusha on 7/17/2017.
 */

public class SingleUserViewHolder extends RecyclerView.ViewHolder{

    ImageView p;
    public SingleUserViewHolder(View itemView) {
        super(itemView);
        p = (ImageView) itemView.findViewById(R.id.pp);
    }

    public void bindto(SingleUserpost singleUserpost , View.OnClickListener clickListener){

        if(!TextUtils.isEmpty(singleUserpost.getPicuri())){
            Picasso.with(p.getContext()).load(singleUserpost.getPicuri()).into(p);
        }
    }
}
