package com.codepath.agyapong.instagramparse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignUp;
    private LinearLayout logInViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = findViewById(R.id.email_edit_text);
        etUsername = findViewById(R.id.username_edit_text);
        etPassword = findViewById(R.id.password_edit_text);
        btnSignUp = findViewById(R.id.sign_up_button);
        logInViewGroup = findViewById(R.id.log_in_linear_layout);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                final String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signUp(email, username, password);



            }
        });

        logInViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    private void signUp(final String email, final String username, String password) {

        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("SignUpActivity", "SUCCESS");
                    Toast.makeText(SignUpActivity.this, "success", Toast.LENGTH_SHORT).show();

                    // navigate to new activity if the user signed in properly
                    gotoMainActivity();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.d("SignUpActivity", "FAILURE"  + username + email);
                    // e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, "FAILURE" + username + email, Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void gotoMainActivity() {
        Log.d("SignUpActivity", "Navigating to MainActivity");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}

