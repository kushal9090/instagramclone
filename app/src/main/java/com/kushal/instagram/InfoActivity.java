package com.kushal.instagram;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kushal.instagram.homescreens.HomeScreenActivity;
import com.kushal.instagram.models.User;
import com.kushal.instagram.postadd.PostAddActivity;

import java.io.IOException;

public class InfoActivity extends AppCompatActivity {

    private ImageView userpicadd;
    private EditText displayname ;
    private EditText bio ;
    private FloatingActionButton nextbtn;

    private static final int PICK_IMAGE_REQUEST = 234;

    private Uri filePath;
    private StorageReference storageReference;

    private DatabaseReference mdatabase;
     private  FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mAuth = FirebaseAuth.getInstance();
        String userid = mAuth.getCurrentUser().getUid();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
        storageReference = FirebaseStorage.getInstance().getReference();

        userpicadd = (ImageView) findViewById(R.id.userpic);
        displayname = (EditText) findViewById(R.id.displayName);
        bio = (EditText) findViewById(R.id.bio);
        nextbtn =(FloatingActionButton) findViewById(R.id.nextbtn);
        userpicadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInfo();
            }
        });



    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent , PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                userpicadd.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void addInfo() {
       if(TextUtils.isEmpty(displayname.getText())){
           Toast.makeText(this, "Please Enter a display name....", Toast.LENGTH_SHORT).show();
           return;
       }
        if(TextUtils.isEmpty(bio.getText())){
            Toast.makeText(this, "Please Enter bio....", Toast.LENGTH_SHORT).show();
            return;
        }


        StorageReference sRef = storageReference.child("profilepics/"+ System.currentTimeMillis() + "." + getFileExtension(filePath));
        sRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                String display = displayname.getText().toString();
                String about = bio.getText().toString();
                String photo = taskSnapshot.getDownloadUrl().toString();

                //String profilePic , displayName , bio ;
                mdatabase.child("profilePic").setValue(photo);
                mdatabase.child("displayName").setValue(display);
                mdatabase.child("bio").setValue(about);

                FirebaseAuth auth = FirebaseAuth.getInstance();
                String uid = auth.getCurrentUser().getUid();
                final DatabaseReference followPendings = FirebaseDatabase.getInstance().getReference().child("followPendings").child(uid).push();
                followPendings.child("catch").setValue("exception");
                DatabaseReference pendingUsers = FirebaseDatabase.getInstance().getReference().child("users");
                pendingUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User userlist = dataSnapshot.getValue(User.class);
                        String name = userlist.getDisplayName();
                      //  String id = userlist.getUid().toString();

                        followPendings.child("displayName").setValue(name);
                        followPendings.child("uid").setValue(userlist.getUid());



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




                Intent next = new Intent(InfoActivity.this , MainActivity.class);
                startActivity(next);
                finish();
            }
        });


    }
}
