package com.example.lab6_q1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cook);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView resultTextview = findViewById(R.id.resultTextview);
        Button returnBtn = findViewById(R.id.returnBtn);

        Intent intent = getIntent();
        String ingredients = intent.getStringExtra("ingredients");
        String instructions = intent.getStringExtra("instructions");

        resultTextview.setText(String.format("Ingredients: %s\n\nInstructions: %s",
                ingredients, instructions));

        returnBtn.setOnClickListener(v -> {
            this.finish();
        });
    }
}