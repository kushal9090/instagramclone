package com.kushal.instagram.requests;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kushal.instagram.R;
import com.kushal.instagram.models.Requests;

/**
 * Created by kusha on 7/13/2017.
 */

public class RequestVH extends RecyclerView.ViewHolder {

     private TextView name;
     Button followback;
    public RequestVH(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.namereq);
        followback = (Button) itemView.findViewById(R.id.followback);

     }
     public void bind(Requests req , View.OnClickListener clickListener){
         name.setText(req.getName());
     }
}
