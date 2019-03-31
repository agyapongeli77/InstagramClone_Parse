package com.codepath.agyapong.instagramparse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private LinearLayout signUpViewGroup;
    private TextView tvForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hides status bar on Log in screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // sets content view
        setContentView(R.layout.activity_login);

        // references to views
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        signUpViewGroup = findViewById(R.id.sign_up_linear_layout);
        tvForgotPassword = findViewById(R.id.forgot_password_text_view);

        // The current signed in user is persisted across app restarts
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // take him to the do stuff with the user
            startActivity(new Intent(this, MainActivity.class));
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();

                login(username, password);

            }
        });

        signUpViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intentToSignUp);

            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));

            }
        });
    }

    private void login(String username, String password) {

        if (!username.equals("") && !password.equals("")) {

            ParseUser.logInInBackground(username, password, new LogInCallback() {
                public void done(ParseUser user, ParseException e) {

                    if (user != null) {
                        // Hooray! The user is logged in.
                        gotoMainActivity();
                        Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Username or password is invalid. Please double-check your login details",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(LoginActivity.this, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show();

        }
    }


    private void gotoMainActivity() {
        Log.d(TAG, "Navigating to MainActivityn");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

    }


}
