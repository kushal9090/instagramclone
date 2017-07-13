package com.kushal.instagram.mesagescreen;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.kushal.instagram.R;
import com.kushal.instagram.models.Following;
import com.kushal.instagram.models.Message;
import com.kushal.instagram.models.User;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageScreen extends AppCompatActivity {

    public static final String EXTRA_USERS = "follow";

    private TextView name ;
    private EditText messageEt;
    private FloatingActionButton fab;

    private Following following;
     private RecyclerView mRecycler;
    private DatabaseReference mAddmessage;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_screen);
        Bundle bundle = getIntent().getExtras();
        following = bundle.getParcelable(EXTRA_USERS);
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);

        mAuth = FirebaseAuth.getInstance();

        name = (TextView) findViewById(R.id.name);
        name.setText(following.getFollowingname());

        mRecycler = (RecyclerView) findViewById(R.id.msgRecycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        messageEt = (EditText) findViewById(R.id.messageET);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendMessage();
            }
        });

        loadChat();
    }
    public void getCurrentTime(View view) {


    }



    private User mUser;
    private void sendMessage() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDate =   mdformat.format(calendar.getTime());

           String dp1 = following.getDp().toString();
        String  msg = messageEt.getText().toString();
        String receivingUid = following.getFollowingid().toString();
        String senderUid = mAuth.getCurrentUser().getUid();
        mAddmessage = FirebaseDatabase.getInstance().getReference().child("message").child(senderUid).child(receivingUid).push();
        mAddmessage.child("data").setValue(msg);
        mAddmessage.child("senderUid").setValue(senderUid);
        mAddmessage.child("receiversUid").setValue(receivingUid);
        mAddmessage.child("time").setValue(strDate);


        //now at receivers side the message should display
        mAddmessage = FirebaseDatabase.getInstance().getReference().child("message").child(receivingUid).child(senderUid).push();
        mAddmessage.child("data").setValue(msg);
        mAddmessage.child("dp").setValue(dp1);
        mAddmessage.child("time").setValue(strDate);


        messageEt.setText("");
    }
 private FirebaseRecyclerAdapter<Message , RecyclerView.ViewHolder> mAdapter;
    private void loadChat() {
        String current_user = mAuth.getCurrentUser().getUid();
        String receivingUid = following.getFollowingid().toString();
        DatabaseReference m = FirebaseDatabase.getInstance().getReference();
        Query mQuery = m.child("message").child(current_user).child(receivingUid);

        mAdapter = new FirebaseRecyclerAdapter<Message, RecyclerView.ViewHolder>(Message.class , R.layout.item_chat , RecyclerView.ViewHolder.class
        , mQuery) {
            private final int TYPE_INCOMING = 1;
            private final int TYPE_OUTGOING = 2;


            @Override
            protected void populateViewHolder(final RecyclerView.ViewHolder viewHolder,final Message message,final int position) {
                if (messageFromCurrentUser(message)) {
                    populateOutgoingViewHolder((OutgoingViewHolder) viewHolder, message);
                } else {
                    populateIncomingViewHolder((IncomingViewHolder) viewHolder, message);
                }

            }
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view;
                switch (viewType) {
                    case TYPE_INCOMING:

                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatincoming, parent, false);
                        return new IncomingViewHolder(view);

                    case TYPE_OUTGOING:
                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
                        return new OutgoingViewHolder(view);
                }
                return super.onCreateViewHolder(parent, viewType);
            }

            private void populateIncomingViewHolder(IncomingViewHolder viewHolder, Message message) {
                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToMessage(message, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {

                    }
                });

            }

            private void populateOutgoingViewHolder(OutgoingViewHolder viewHolder, Message message) {

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToMessage(message, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {

                    }
                });

            }
            @Override
            public int getItemViewType(int position) {
                super.getItemViewType(position);
                Message message = getItem(position);

                if (messageFromCurrentUser(message)) {
                    return TYPE_OUTGOING;
                }

                return TYPE_INCOMING;
            }

            private boolean messageFromCurrentUser(Message message) {
                String currentUid = mAuth.getCurrentUser().getUid();
                if (currentUid.equalsIgnoreCase(message.getSenderUid())) {
                    return true;
                }
                return false;
            }
            class IncomingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                CircleImageView dpin;
                TextView msgincome , time;


                public IncomingViewHolder(View v) {
                    super(v);
                    dpin = (CircleImageView) v.findViewById(R.id.dp);
                    msgincome = (TextView) v.findViewById(R.id.msgincoming);
                    time = (TextView) v.findViewById(R.id.time);
                }

                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                    }
                }
                public void bindToMessage(Message message, View.OnClickListener starClickListener) {

                    if (!TextUtils.isEmpty(message.getDp())) {
                        Picasso.with(dpin.getContext()).load(following.getDp()).into(dpin);
                    }
                    msgincome.setText(message.getData());
                    time.setText(message.getTime());


                }

            }


            class OutgoingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                TextView time;
                TextView msg;

                public OutgoingViewHolder(View v) {
                    super(v);
                    msg = (TextView) v.findViewById(R.id.msg);
                    time = (TextView) v.findViewById(R.id.time);

                }

                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                    }
                }
                public void bindToMessage(Message message, View.OnClickListener starClickListener) {

                    msg.setText(message.getData());
                    time.setText(message.getTime());


                }


            }

        };
        mRecycler.setAdapter(mAdapter);


    }

}
