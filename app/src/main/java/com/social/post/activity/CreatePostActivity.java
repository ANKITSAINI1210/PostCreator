package com.social.post.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.social.post.R;
import com.social.post.db.DataBaseHandler;
import com.social.post.model.PostModel;
import com.theartofdev.edmodo.cropper.CropImage;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CreatePostActivity";

    private ImageView imageView;
    private RelativeLayout imageContainer;
    private EditText edtTitle, edtDescription;
    private Button btnPost;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        init();
        setListener();
        setUpToolbar();

    }

    private void init() {
        imageView = findViewById(R.id.imageView);
        imageContainer = findViewById(R.id.imageContainer);
        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        btnPost = findViewById(R.id.btnPost);
    }

    private void setListener() {
        btnPost.setOnClickListener(this);
        imageContainer.setOnClickListener(this);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(R.string.create_post);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageContainer: {
                onSelectImageClick();
                break;
            }
            case R.id.btnPost: {
                if (!isValidate()) {
                    PostModel model = new PostModel();
                    model.setImageUri(imageUri.toString());
                    model.setTitle(edtTitle.getText().toString());
                    model.setDescription(edtDescription.getText().toString());
                    DataBaseHandler handler = new DataBaseHandler(this);
                    handler.savePost(model);
                    finish();
                }
                break;
            }
        }
    }

    @SuppressLint("NewApi")
    public void onSelectImageClick() {
        if (CropImage.isExplicitCameraPermissionRequired(this)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
        } else {
            CropImage.startPickImageActivity(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "CAMERA_CAPTURE_PERMISSIONS granted");
                CropImage.startPickImageActivity(this);
            } else {
                Toast.makeText(this, R.string.permissions_not_granted, Toast.LENGTH_LONG).show();
                Log.d(TAG, "CAMERA_CAPTURE_PERMISSIONS not granted");
            }
        }
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (imageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "PICK_IMAGE_PERMISSIONS granted");
            } else {
                Toast.makeText(this, R.string.permissions_not_granted, Toast.LENGTH_LONG).show();
                Log.d(TAG, "PICK_IMAGE_PERMISSIONS not granted");
            }
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = CropImage.getPickImageResultUri(this, data);
            Glide.with(this).load(imageUri).into(imageView);
        }
    }


    private boolean isValidate() {
        if (imageUri == null) {
            Toast.makeText(this, "Please select image.", Toast.LENGTH_LONG).show();
            return true;
        } else if (TextUtils.isEmpty(edtTitle.getText().toString())) {
            Toast.makeText(this, "Please enter the title.", Toast.LENGTH_LONG).show();
            return true;
        } else if (TextUtils.isEmpty(edtDescription.getText().toString())) {
            Toast.makeText(this, "Please enter the description.", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}
