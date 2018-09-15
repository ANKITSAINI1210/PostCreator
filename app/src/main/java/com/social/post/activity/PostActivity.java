package com.social.post.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.social.post.R;
import com.social.post.adapter.PostAdapter;
import com.social.post.db.DataBaseHandler;
import com.social.post.listener.PostClickListener;
import com.social.post.model.PostModel;

public class PostActivity extends AppCompatActivity implements PostClickListener {

    private RecyclerView recyclerView;
    private DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setUpToolbar();
        init();
        setUpFab();
    }

    private void setUpFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, CreatePostActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (null != dataBaseHandler.getAllPost() && !dataBaseHandler.getAllPost().isEmpty()) {
            recyclerView.setAdapter(new PostAdapter(this, dataBaseHandler.getAllPost(), this));
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerPost);
        dataBaseHandler = new DataBaseHandler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpRecycler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostClick(PostModel postModel) {

    }
}
