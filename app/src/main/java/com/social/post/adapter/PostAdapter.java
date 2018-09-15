package com.social.post.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.social.post.R;
import com.social.post.listener.PostClickListener;
import com.social.post.model.PostModel;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context mContext;
    private ArrayList<PostModel> modelArrayList;
    private LayoutInflater inflater;
    private PostClickListener listener;

    public PostAdapter(Context mContext, ArrayList<PostModel> modelArrayList, PostClickListener listener) {
        this.mContext = mContext;
        this.modelArrayList = modelArrayList;
        this.listener = listener;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostModel postModel = modelArrayList.get(position);
        if (null != postModel) {
            Glide.with(mContext).load(postModel.getImageUri()).into(holder.imageViewPost);
            holder.tvTitle.setText(postModel.getTitle());
            holder.tvDescription.setText(postModel.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return (!modelArrayList.isEmpty() ? modelArrayList.size() : 0);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPost;
        private TextView tvTitle, tvDescription;

        PostViewHolder(View itemView) {
            super(itemView);
            imageViewPost = itemView.findViewById(R.id.imageViewPost);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPostClick(modelArrayList.get(getAdapterPosition()));
                }
            });
        }
    }
}
