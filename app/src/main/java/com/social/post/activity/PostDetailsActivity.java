package com.social.post.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.social.post.R;
import com.social.post.model.PostModel;

public class PostDetailsActivity extends AppCompatActivity {

    private PostModel postModel;
    private ImageView imageViewPost;
    private TextView tvTitle, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        if (null != getIntent() && null != getIntent().getExtras()) {
            postModel = getIntent().getExtras().getParcelable("postDetails");
        }
        init();
        setUp();
        setUpToolbar();
    }

    private void init() {
        imageViewPost = findViewById(R.id.imageViewPost);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
    }

    private void setUp() {
        if (null != postModel) {
            Glide.with(PostDetailsActivity.this).load(Uri.parse(postModel.getImageUri())).into(imageViewPost);
            tvTitle.setText(postModel.getTitle());
            tvDescription.setText(postModel.getDescription());
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(R.string.post_details);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
