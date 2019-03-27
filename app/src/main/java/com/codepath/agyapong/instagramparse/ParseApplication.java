package com.codepath.agyapong.instagramparse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    private static final String APP_ID = "elija-instagram-codepath";
    private static final String MASTER_KEY = "CodepathInstagramParse";
    private static final String SERVER_URL = "http://elija-instagram-codepath.herokuapp.com/parse";

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(APP_ID) // should correspond to APP_ID env variable
                .clientKey(MASTER_KEY)  // set explicitly unless clientKey is explicitly configured on Parse server
                .server(SERVER_URL).build());

    }
}
