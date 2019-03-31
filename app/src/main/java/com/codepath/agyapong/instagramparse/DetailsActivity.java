package com.codepath.agyapong.instagramparse;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;


public class DetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView ivProfileImage;
    private TextView tvUserHandle, tvCaption, tvCreationTime;
    private EditText etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar = findViewById(R.id.toolbar_details_activity);
        ivProfileImage = findViewById(R.id.ivUserProfileImage_detail_activity);
        tvUserHandle = findViewById(R.id.tvHandle_detail_activity);
        tvCaption = findViewById(R.id.tvDescription_detail_activity);
        tvCreationTime = findViewById(R.id.tvCreationTime_detail_activity);
        etComment = findViewById(R.id.comment_text_view_detail_activity);
        setSupportActionBar(toolbar);


        ParseFile profilePicture = (ParseFile) getIntent().getExtras().get("profilePicture");
        String userHandle = getIntent().getExtras().getString("userHandle");
        String caption  = getIntent().getExtras().getString("caption");
        String creationTime  = getIntent().getExtras().getString("creationTime");


        if (profilePicture != null) {
            Glide.with(this).load(profilePicture.getUrl()).into(ivProfileImage);
        }
        tvUserHandle.setText(userHandle);
        tvCaption.setText(caption);
        tvCreationTime.setText("Posted on "+ creationTime);

    }

    public void backToStreamScreen(View backButtonViewOnToolbar) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
