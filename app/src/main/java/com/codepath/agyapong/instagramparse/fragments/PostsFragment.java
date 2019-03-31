package com.codepath.agyapong.instagramparse.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.agyapong.instagramparse.Post;
import com.codepath.agyapong.instagramparse.PostsAdapter;
import com.codepath.agyapong.instagramparse.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {
    private static final String TAG = "PostsFragment";
    protected RecyclerView rvPosts;
    protected PostsAdapter postAdapter;
    protected List<Post> mPosts;
    protected SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "PostsFragment onCreateView method executes");

        return inflater.inflate(R.layout.fragment_posts, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       rvPosts = view.findViewById(R.id.rvPosts);
      // rvPosts.setHasFixedSize(true);

        // create the data source
        mPosts = new ArrayList<>();

        // create the adapter
        postAdapter = new PostsAdapter(getContext(), mPosts);
        // set the adapter to the recycler view
        rvPosts.setAdapter(postAdapter);
        // set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.d(TAG, "PostsFragment onViewCreated method executes");


        Log.d(TAG, "PostsFragment onViewCreated method executes");

        // Lookup the swipe container view
        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }




    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
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
