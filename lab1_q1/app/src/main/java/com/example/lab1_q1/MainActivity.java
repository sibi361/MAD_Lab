package com.example.lab1_q1;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText firstNameField;
    EditText lastNameField;
    EditText emailIdField;
    EditText mobileField;
    Button submitBtn;

    TextView firstNameDisplay;
    TextView lastNameDisplay;
    TextView emailIdDisplay;
    TextView mobileNoDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstNameField = findViewById(R.id.firstNameField);
        lastNameField = findViewById(R.id.lastNameField);
        emailIdField = findViewById(R.id.emailIdField);
        mobileField = findViewById(R.id.mobileField);
        submitBtn = findViewById(R.id.submitBtn);

        firstNameDisplay = findViewById(R.id.firstNameDisplay);
        lastNameDisplay = findViewById(R.id.lastNameDisplay);
        emailIdDisplay = findViewById(R.id.emailIdDisplay);
        mobileNoDisplay = findViewById(R.id.mobileNoDisplay);

        submitBtn.setOnClickListener(v -> {
            final String givenFirstName = firstNameField.getText().toString();
            final String givenLastName = lastNameField.getText().toString();
            final String givenEmail = emailIdField.getText().toString();
            final String givenMobile = mobileField.getText().toString();

            if (givenFirstName.isEmpty() || givenLastName.isEmpty() || givenEmail.isEmpty() || givenMobile.isEmpty()) {
                showToast("All inputs must be supplied.");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(givenEmail).matches()) {
                showToast("Given Email ID is invalid.");
            } else if (!Patterns.PHONE.matcher(givenMobile).matches()) {
                showToast("Given mobile number is invalid.");
            } else {
                firstNameField.setVisibility(View.GONE);
                lastNameField.setVisibility(View.GONE);
                emailIdField.setVisibility(View.GONE);
                mobileField.setVisibility(View.GONE);
                submitBtn.setVisibility(View.GONE);

                firstNameDisplay.setVisibility(View.VISIBLE);
                lastNameDisplay.setVisibility(View.VISIBLE);
                emailIdDisplay.setVisibility(View.VISIBLE);
                mobileNoDisplay.setVisibility(View.VISIBLE);

                firstNameDisplay.setText(String.format("First name: %s", givenFirstName));
                lastNameDisplay.setText(String.format("Last name: %s", givenLastName));
                emailIdDisplay.setText(String.format("Email ID: %s", givenEmail));
                mobileNoDisplay.setText(String.format("Mobile no.: %s", givenMobile));
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}