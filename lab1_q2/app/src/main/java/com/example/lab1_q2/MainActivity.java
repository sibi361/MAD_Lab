package com.example.lab1_q2;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

class User {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

public class MainActivity extends AppCompatActivity {
    EditText emailField;
    EditText mobileField;
    EditText passwordField;
    Button submitBtn;

    //final private ArrayList<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //users.add(new User("sam", "john"));

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailField = findViewById(R.id.emailField);
        mobileField = findViewById(R.id.mobileField);
        passwordField = findViewById(R.id.passwordField);
        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(v -> {
            final String givenEmail = emailField.getText().toString();
            final String givenMobile = mobileField.getText().toString();
            final String givenPassword = passwordField.getText().toString();

            if (givenEmail.isEmpty() || givenMobile.isEmpty() || givenPassword.isEmpty()) {
                showToast("All inputs must be supplied.");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(givenEmail).matches()) {
                showToast("Given Email ID is invalid.");
            } else if (!Patterns.PHONE.matcher(givenMobile).matches()) {
                showToast("Given mobile number is invalid.");
            } else {
                showToast(String.format("You are now registered with the Email: %s | Mobile no.: %s | Password: %s",
                        givenEmail, givenMobile, givenPassword.replaceAll(".", "*")));
            }
            /*if (givenUsername.isEmpty() || givenPassword.isEmpty()) {
                showToast("Both username and password must be supplied.");
            } else {
                for (int i = 0; i < users.toArray().length; i++) {
                    if (givenUsername.equals(users.get(i).username) && givenPassword.equals(users.get(i).password)) {
                        showToast(String.format("Welcome %s! You are now logged in.", givenUsername));
                        return;
                    }
                }

                showToast("Invalid credentials.");
            }*/
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}