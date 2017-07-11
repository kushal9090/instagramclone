package com.kushal.instagram.homescreens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kushal.instagram.R;
import com.kushal.instagram.models.Following;
import com.kushal.instagram.models.Post;
import com.kushal.instagram.models.User;

import static android.view.View.GONE;

/**
 * Created by kusha on 7/6/2017.
 */

public class MessageFragment extends Fragment {
    public static final String EXTRAS_USER= "user";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.message_fragment , container , false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        showUsers();
    }

    @Override
    public void onStart() {
        super.onStart();
        Following following = new Following();
        UserViewholder uv = new UserViewholder(getView());

        if(following.getState() != null){
            uv.mFollow.setVisibility(GONE);
        }else{

        }

    }

    private void initView() {

        initRecyclerView();
    }
  private RecyclerView userrecycle;
    private void initRecyclerView() {

         userrecycle = (RecyclerView) getView().findViewById(R.id.usersrecycler);
        userrecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
  private  Following fol;
    private FirebaseRecyclerAdapter<User , UserViewholder> adapter;
    private void showUsers() {

        DatabaseReference usernode = FirebaseDatabase.getInstance().getReference();
        Query query = usernode.child("users");

        adapter = new FirebaseRecyclerAdapter<User, UserViewholder>(User.class , R.layout.item_uers , UserViewholder.class , query) {
            @Override
            protected void populateViewHolder(final UserViewholder viewHolder,final User user,final int position) {
               viewHolder.mFollow.setOnClickListener(new View.OnClickListener() {

                   @Override
                   public void onClick(final View view) {
                       FirebaseAuth auth = FirebaseAuth.getInstance();
                       String uid = auth.getCurrentUser().getUid();
                       DatabaseReference friends = FirebaseDatabase.getInstance().getReference().child("follow").child(uid).push();
                       friends.child("followingid").setValue(user.getUid());
                       friends.child("followingname").setValue(user.getDisplayName());
                       friends.child("dp").setValue(user.getProfilePic());
                       friends.child("state").setValue("following");

                       //.....
                      // viewHolder.mTitle.setText(fol.getState()+"you");
                       friends.addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                               Following following = dataSnapshot.getValue(Following.class);
                               if (following.getState() != null) {
                                   viewHolder.mFollow.setVisibility(view.GONE);
                               }
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });

                   }
               });


                viewHolder.bind(user, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        };

        userrecycle.setAdapter(adapter);
    }

}
