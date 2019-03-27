package com.codepath.agyapong.instagramparse;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ResetPasswordActivity extends AppCompatActivity {

    private final static String TAG = "ResetActivity";
    private EditText etEmail;
    //private Button btnPasswordReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etEmail = findViewById(R.id.email_to_reset_password_edit_text);

    }

    public void resetPassword(View view){

        ParseUser.requestPasswordResetInBackground(etEmail.getText().toString().trim(), new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // An email was successfully sent with reset instructions.
                    Toast.makeText(ResetPasswordActivity.this,
                            "An email was successfully sent with rest instructions", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "success");
                    e.printStackTrace();
                } else {
                    // Something went wrong. Look at the ParseException to see what's up.
                    Toast.makeText(ResetPasswordActivity.this,
                            "Something went wrong finding email-->" + etEmail.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "failure");
                    e.printStackTrace();
                }
            }

        });


    }


}