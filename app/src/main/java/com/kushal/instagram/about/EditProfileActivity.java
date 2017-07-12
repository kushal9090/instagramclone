package com.kushal.instagram.about;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kushal.instagram.InfoActivity;
import com.kushal.instagram.MainActivity;
import com.kushal.instagram.R;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView userpicadd;
    private EditText displayname ;
    private EditText bio ;
    private FloatingActionButton fabup , fabname , fabbio , back;

    private static final int PICK_IMAGE_REQUEST = 234;

    private Uri filePath;
    private StorageReference storageReference;

    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mAuth = FirebaseAuth.getInstance();
        String userid = mAuth.getCurrentUser().getUid();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
        storageReference = FirebaseStorage.getInstance().getReference();

        displayname = (EditText) findViewById(R.id.displaynamechanged);
        bio = (EditText) findViewById(R.id.biochange);
        userpicadd = (ImageView) findViewById(R.id.profilepicchange);
        userpicadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();;
            }
        });


        fabup = (FloatingActionButton) findViewById(R.id.fabup);
        fabbio = (FloatingActionButton) findViewById(R.id.fabbio);
        fabname = (FloatingActionButton) findViewById(R.id.fabname);
        back = (FloatingActionButton) findViewById(R.id.back);

        fabup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDp();
            }
        });

        fabname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateName();
            }
        });
        fabbio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              updateBio();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(EditProfileActivity.this , AboutActivity.class);
                startActivity(back);
                finish();
            }
        });
    }

    private void updateName() {
        String name = displayname.getText().toString();

        mdatabase.child("displayName").setValue(name);
        Toast.makeText(this, "successfully changed", Toast.LENGTH_SHORT).show();
    }
    private void updateBio() {
        String bo = bio.getText().toString();

        mdatabase.child("bio").setValue(bo);
        Toast.makeText(this, "successfully changed", Toast.LENGTH_SHORT).show();
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

    private void updateDp() {


        StorageReference sRef = storageReference.child("profilepics/"+ System.currentTimeMillis() + "." + getFileExtension(filePath));
        sRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String photo = taskSnapshot.getDownloadUrl().toString();

                mdatabase.child("profilePic").setValue(photo);

                Toast.makeText(EditProfileActivity.this, "your profile pic has been changed..", Toast.LENGTH_SHORT).show();
                return;
            }
        });


    }
}
