package com.example.lab4_q1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        ArrayList<Integer> correctAnswers = intent.getIntegerArrayListExtra("correctAnswers");
        ArrayList<Integer> givenAnswers = intent.getIntegerArrayListExtra("givenAnswers");
        int questionsCount = 0, answeredCount = 0, correctCount = 0, score;

        if (correctAnswers != null && givenAnswers != null) {
            questionsCount = correctAnswers.size();

            for (int i = 0; i < questionsCount; i++) {
                if (givenAnswers.get(i) > -1) {
                    answeredCount++;
                }

                if (correctAnswers.get(i).equals(givenAnswers.get(i))) {
                    correctCount++;
                }
            }
        }

        score = correctCount * 10;

        TextView resultView = findViewById(R.id.resultView);
        resultView.setText(answeredCount > 0 ?
                String.format("You scored %s freedom points after answering %s / %s questions correctly",
                        score,
                        correctCount,
                        questionsCount)
                : "We cannot evaluate your answers as you have not answered any questions.");

        Button button = (Button) findViewById(R.id.buttonAlert);
        button.setOnClickListener(v -> {
            finish();
        });
    }
}