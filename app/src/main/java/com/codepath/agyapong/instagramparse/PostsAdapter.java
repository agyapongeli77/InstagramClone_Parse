package com.codepath.agyapong.instagramparse;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.agyapong.instagramparse.fragments.ProfileFragment;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private Context context;
    private List<Post> posts;
    private boolean isLikeButtonClicked = true;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup, false);
        Log.d("PostsAdapter", "onCreateViewholder working");

        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder postsViewHolder, int position) {
        Log.d("PostsAdapter", "onBindViewholder working");
        Post post = posts.get(position);
        postsViewHolder.bind(post);

    }

    @Override
    public int getItemCount() {
        Log.d("PostsAdapter", "getCount working" + posts.size());

        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addPosts(List<Post> post) {
        posts.addAll(post);
        notifyDataSetChanged();
    }

    class PostsViewHolder extends RecyclerView.ViewHolder{
        private TextView tvHandle;
        private ImageView ivImage;
        private TextView tvDescription;
        private ImageView ivProfilePicture;
        private TextView tvTimeStamp;
        private ImageView ivComment;
        private ImageView ivLikeImage;
        private TextView tvHandleBesideCaption, tvLikesText;


        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHandle = itemView.findViewById(R.id.tvHandle);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvHandleBesideCaption = itemView.findViewById(R.id.tvHandleBesideCaption);
            ivProfilePicture = itemView.findViewById(R.id.ivUserProfileImage);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            ivComment = itemView.findViewById(R.id.comment_image_view);
            ivLikeImage = itemView.findViewById(R.id.like_image_view);
            tvLikesText = itemView.findViewById(R.id.tvLikes);
        }

        public void bind(final Post post){
            tvHandle.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null){
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            tvDescription.setText(post.getDescription());
            tvHandleBesideCaption.setText(post.getUser().getUsername());
            ParseFile profileImage = post.getProfilePicture();
            if (profileImage != null){
                Glide.with(context).load(profileImage.getUrl()).into(ivProfilePicture);
            }
            tvTimeStamp.setText("Posted on "+post.getTimeStamp());
            ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("profilePicture", post.getProfilePicture());
                    intent.putExtra("userHandle", post.getUser().getUsername());
                    intent.putExtra("caption", post.getDescription());
                    intent.putExtra("creationTime", post.getTimeStamp());

                    context.startActivity(intent);

                }
            });

            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("profilePicture", post.getProfilePicture());
                    intent.putExtra("userHandle", post.getUser().getUsername());
                    intent.putExtra("caption", post.getDescription());
                    intent.putExtra("creationTime", post.getTimeStamp());

                    context.startActivity(intent);
                }
            });

            // change the like image color when  thee user clicks it
            ivLikeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int likesNumberCount = 0;
                    // if isLikeButtonClicked = false and the number of likes is 0,show the plain like icon view
                    // which means the user has not liked the post
                    if (isLikeButtonClicked == false) {

                        ivLikeImage.setImageResource(R.drawable.ufi_heart);
                        tvLikesText.setText(likesNumberCount + " like");
                        isLikeButtonClicked = true;

                        // if the number of likes is 0 hide the entire view(tvLikesText)
                        if (likesNumberCount == 0)
                            tvLikesText.setVisibility(View.GONE);

                    } else{
                        // if isLikeButtonClicked = true and the number of likes is 1,show the view to be the red icon
                        // which indicates that a user has liked the post
                        ivLikeImage.setImageResource(R.drawable.ufi_heart_active);
                        likesNumberCount++;
                        tvLikesText.setText(likesNumberCount + " like");
                        isLikeButtonClicked = false;

                        // if the number of likes is 1 show the entire view(tvLikesText)
                        if (likesNumberCount == 1)
                            tvLikesText.setVisibility(View.VISIBLE);
                    }
                }

            });

            // opens the profile page when the user handle beside the caption is clicked
            tvHandleBesideCaption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openProfileFragment(view);
                }
            });

            // opens the profile page when the user handle beside the profile picture is clicked
            tvHandle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openProfileFragment(view);
                }
            });

            // opens the profile page when the user's profile picture is clicked
            ivProfilePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openProfileFragment(view);
                }
            });
        }

        void openProfileFragment(View view){
            // Horrayy!!! I found how to open a fragment from this adapter class when the userHandle is clicked...Yipiii!!! :)
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment myFragment = new ProfileFragment();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, myFragment).addToBackStack(null).commit();
        }


    }
}
