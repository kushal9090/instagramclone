package com.kushal.instagram.story;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.kushal.instagram.R;
import com.kushal.instagram.homescreens.HomeScreenActivity;
import com.kushal.instagram.models.Story;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryScreenActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "story";
    private static int TIME_OUT = 4000; //Time to launch the another activity

    private CircleImageView dp ;
    private ImageView storyPic;
    private TextView name;

    private Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_screen);
        Bundle bundle = getIntent().getExtras();
        story = bundle.getParcelable(EXTRA_DATA);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(StoryScreenActivity.this, HomeScreenActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);

        dp = (CircleImageView) findViewById(R.id.storypic);
        storyPic = (ImageView) findViewById(R.id.displayphoto);
        name = (TextView) findViewById(R.id.name);

        if(!TextUtils.isEmpty(story.getStory())){
            Picasso.with(storyPic.getContext()).load(story.getStory()).into(storyPic);
        }

        if(!TextUtils.isEmpty(story.getDp())){
            Picasso.with(dp.getContext()).load(story.getDp()).into(dp);
        }
        name.setText(story.getName());
    }
}
