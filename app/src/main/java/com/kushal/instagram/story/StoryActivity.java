package com.kushal.instagram.story;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

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
import com.kushal.instagram.R;
import com.kushal.instagram.homescreens.HomeScreenActivity;
import com.kushal.instagram.models.User;

import java.io.IOException;

public class StoryActivity extends AppCompatActivity {
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 234;
    private ImageView addbtn , addImage;

    private ProgressDialog progressDialog;

    private DatabaseReference mStoryDatabse;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storageReference = FirebaseStorage.getInstance().getReference();
        mStoryDatabse = FirebaseDatabase.getInstance().getReference().child("story").push();

        progressDialog = new ProgressDialog(this);

        addbtn = (ImageView) findViewById(R.id.addBtn);
        addImage = (ImageView) findViewById(R.id.storypic);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storyadd();
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
                addImage.setImageBitmap(bitmap);
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


    private void storyadd() {

        progressDialog.setMessage("uploading.....");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StorageReference sRef = storageReference.child("story/"+ System.currentTimeMillis() + "." + getFileExtension(filePath));
        sRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                progressDialog.dismiss();

                String pic = taskSnapshot.getDownloadUrl().toString();
                mStoryDatabse.child("story").setValue(pic);

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DatabaseReference info = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                info.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User users = dataSnapshot.getValue(User.class);

                        mStoryDatabse.child("name").setValue(users.getDisplayName());
                        mStoryDatabse.child("email").setValue(users.getName());
                        mStoryDatabse.child("dp").setValue(users.getProfilePic());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Intent added = new Intent(StoryActivity.this , HomeScreenActivity.class);
                finish();
                startActivity(added);

            }
        });

    }
}
