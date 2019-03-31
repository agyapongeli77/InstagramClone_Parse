package com.codepath.agyapong.instagramparse.fragments;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.codepath.agyapong.instagramparse.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import static android.support.v7.widget.GridLayoutManager.DEFAULT_SPAN_COUNT;

public class ProfileFragment extends PostsFragment {

    private static final String TAG = "ProfileFragment";

    @Override
    protected void queryPosts() {

        int numberOfColumns = 2;
        rvPosts.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));



        ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.d(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                // clear the existing data
                postAdapter.clear();
                // show the data we just received
                postAdapter.addPosts(posts);
                //notify the adapter
                postAdapter.notifyDataSetChanged();
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.d(TAG, "Post: " + post.getDescription() + " username: "
                            + post.getUser().getUsername());

                }

            }
        });

    }
}
