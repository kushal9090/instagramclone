package com.kushal.instagram.homescreens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.kushal.instagram.mesagescreen.MessageViewHolder;
import com.kushal.instagram.models.Following;
import com.kushal.instagram.models.Post;
import com.kushal.instagram.models.Requests;
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
 private String status;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      final UserViewholder u = new UserViewholder(getView());



        status = "follow";

        initView();
        showUsers();
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
                DatabaseReference k = getRef(position);
                final String confirmKey = k.getKey();



               viewHolder.mFollow.setOnClickListener(new View.OnClickListener() {

                   @Override
                   public void onClick(final View view) {



                           FirebaseAuth auth = FirebaseAuth.getInstance();
                           final String uid = auth.getCurrentUser().getUid();

                           final DatabaseReference friends = FirebaseDatabase.getInstance().getReference().child("follow").child(uid).child(user.getUid());
                           friends.child("followingid").setValue(user.getUid());
                           friends.child("followingname").setValue(user.getDisplayName());
                           friends.child("dp").setValue(user.getProfilePic());

                           friends.child("state").setValue("following").addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {


                                   if (task.isSuccessful()) {
                                       status = "pending";
                                       String reqID = user.getUid().toString();
                                       final DatabaseReference request = FirebaseDatabase.getInstance().getReference().child("request").child(reqID).child(uid);
                                       //request.child("from").setValue(uid);
                                       request.child("uid").setValue(uid);
                                       request.child("state").setValue("pending").addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void aVoid) {


                                                   viewHolder.mFollow.setText("Following");
                                               
                                           }
                                       });
                                       DatabaseReference current_user = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                                       current_user.addValueEventListener(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                               User current = dataSnapshot.getValue(User.class);
                                               String currentname = current.getDisplayName();
                                               request.child("name").setValue(currentname);

                                               String dp = current.getProfilePic();
                                               request.child("dp").setValue(dp);
                                           }

                                           @Override
                                           public void onCancelled(DatabaseError databaseError) {

                                           }
                                       });


                                   }
                               }
                           });
                           DatabaseReference counts = FirebaseDatabase.getInstance().getReference().child("counts").child(user.getUid());
                           counts.child("nnumber").setValue("x");


                           //.....
                           // viewHolder.mTitle.setText(fol.getState()+"you");


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
