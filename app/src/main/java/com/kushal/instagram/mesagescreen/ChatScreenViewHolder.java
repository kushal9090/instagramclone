package com.kushal.instagram.mesagescreen;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kushal.instagram.R;
import com.kushal.instagram.models.Message;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kusha on 7/11/2017.
 */

public class ChatScreenViewHolder  extends RecyclerView.ViewHolder {
    CircleImageView dp ;
    TextView msg;

    public ChatScreenViewHolder(View itemView) {
        super(itemView);
        dp = (CircleImageView) itemView.findViewById(R.id.dp);
        msg = (TextView) itemView.findViewById(R.id.msg);
    }

    public void bind(Message message , View.OnClickListener clickListener){
        if(!TextUtils.isEmpty(message.getDp())){
            Picasso.with(dp.getContext()).load(message.getDp()).into(dp);

        }
         msg.setText(message.getData());
    }
}
