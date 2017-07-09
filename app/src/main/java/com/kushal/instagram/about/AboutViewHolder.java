package com.kushal.instagram.about;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.kushal.instagram.R;
import com.kushal.instagram.models.SingleUserpost;
import com.squareup.picasso.Picasso;

/**
 * Created by kusha on 7/9/2017.
 */

public class AboutViewHolder extends RecyclerView.ViewHolder {
    ImageView mImageV;
    public AboutViewHolder(View itemView) {
        super(itemView);

        mImageV = (ImageView) itemView.findViewById(R.id.aboutpic);
    }

    public void bind(SingleUserpost  singleUserpost, View.OnClickListener start){
        if (!TextUtils.isEmpty(singleUserpost.getPicuri())){
            Picasso.with(mImageV.getContext()).load(singleUserpost.getPicuri()).into(mImageV);
        }
    }
}
