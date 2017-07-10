package com.kushal.instagram.postadd;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kushal.instagram.R;
import com.kushal.instagram.homescreens.HomeScreenActivity;
import com.kushal.instagram.homescreens.PostFragment;
import com.kushal.instagram.models.Post;
import com.kushal.instagram.models.User;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;

public class PostAddActivity extends AppCompatActivity {

    private EditText mPostTitle , mPicuri ;
    private Button mUpload;
    private Button mPostButton;
    private DatabaseReference mdata;
    private ImageView mPostPicUri;
    private ProgressDialog mProgress;
    private Button mPostbtn2;
    //constant to track image chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;

    private Uri filePath;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);

        mPostbtn2 = (Button) findViewById(R.id.postbtn2);
        mPostButton = (Button) findViewById(R.id.postbutton);
        mProgress = new ProgressDialog(this);

        mdata = FirebaseDatabase.getInstance().getReference().child("post").push();

        storageReference = FirebaseStorage.getInstance().getReference();
        mPostTitle = (EditText) findViewById(R.id.postTitle);
     mPicuri = (EditText) findViewById(R.id.picuri);
        mPostPicUri = (ImageView) findViewById(R.id.postpicuri);
        mUpload = (Button) findViewById(R.id.upload);
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });
        mPostbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postTwo();
            }
        });
    }
    FirebaseUser fuser;

    private void postTwo() {


        String pic = mPicuri.getText().toString();
        String title = mPostTitle.getText().toString();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
       String current_user = mAuth.getCurrentUser().getEmail();
       DatabaseReference data2 = FirebaseDatabase.getInstance().getReference().child("post").push();
        data2.child("email").setValue(current_user);
        data2.child("posttitle").setValue(title);
        data2.child("picuri").setValue(pic);

        mPostTitle.setText("");

        //particular user post..
        String currentuser_uid = mAuth.getCurrentUser().getUid();
        DatabaseReference userpost = FirebaseDatabase.getInstance().getReference().child("users_post").child(currentuser_uid).push();
        userpost.child("email").setValue(current_user);
        userpost.child("posttitle").setValue(title);
        userpost.child("picuri").setValue(pic);


        //mPostPicUri.setText(" ");
        Intent back = new Intent(PostAddActivity.this , HomeScreenActivity.class);
        startActivity(back);
        Toast.makeText(PostAddActivity.this , "your post has been added" , Toast.LENGTH_LONG).show();
        finish();
        return;
    }
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent , PICK_IMAGE_REQUEST);
    }

    //converts to bitmap
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();


        }
    }
    //gets image extension
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


   private User user;
    private void post() {


        mProgress.setMessage("Uploading.....");
        mProgress.setCancelable(false);
        mProgress.show();
        //String title = mPostTitle.getText().toString();

      //  String picuri = mPostPicUri.getText().toString();

        //getting the storage reference
        StorageReference sRef = storageReference.child("uploads/"+ System.currentTimeMillis() + "." + getFileExtension(filePath));
        sRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                mProgress.dismiss();
                 String photo = taskSnapshot.getDownloadUrl().toString();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();


                //profile pic
                String uid =  mAuth.getCurrentUser().getUid();
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User name = dataSnapshot.getValue(User.class);
                        mdata.child("profilePic").setValue(name.getProfilePic());
                        mdata.child("displayName").setValue(name.getDisplayName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                 //............



                String current_user = mAuth.getCurrentUser().getEmail();
                String title = mPostTitle.getText().toString();


                mdata.child("email").setValue(current_user);
                mdata.child("posttitle").setValue(title);
                mdata.child("picuri").setValue(photo);
                //  mdata.child("picuri").setValue(picuri);
                mPostTitle.setText("");
                //mPostPicUri.setText(" ");


                //particular user post..
                String currentuser_uid = mAuth.getCurrentUser().getUid();
                DatabaseReference userpost = FirebaseDatabase.getInstance().getReference().child("users_post").child(currentuser_uid).push();
                userpost.child("email").setValue(current_user);
                userpost.child("posttitle").setValue(title);
                userpost.child("picuri").setValue(photo);



                Intent back = new Intent(PostAddActivity.this , HomeScreenActivity.class);
                startActivity(back);
                Toast.makeText(PostAddActivity.this , "your post has been added" , Toast.LENGTH_LONG).show();
                finish();
                return;
            }
        });


       /* FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String current_user = mAuth.getCurrentUser().getEmail();

        mdata.child("email").setValue(current_user);
        mdata.child("posttitle").setValue(title);
      //  mdata.child("picuri").setValue(picuri);
        mPostTitle.setText("");
        //mPostPicUri.setText(" ");
        Intent back = new Intent(PostAddActivity.this , HomeScreenActivity.class);
        startActivity(back);
        Toast.makeText(PostAddActivity.this , "your post has been added" , Toast.LENGTH_LONG).show();
        finish();
        return;*/
    }


}
