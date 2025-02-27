package com.example.lab4_q1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.*;
import java.util.stream.IntStream;

class Question {
    String question;
    ArrayList<String> options;
    int correctOptionIdx;

    Question(String q, ArrayList<String> a, int idx) {
        question = q;
        options = a;
        correctOptionIdx = idx;
    }
}

public class MainActivity extends AppCompatActivity {
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

        final List<Question> questions = new ArrayList<Question>(List.of(
                new Question("India got independence in which year?", new ArrayList<String>(List.of("1901", "1950", "1993", "1947")), 3),
                new Question("How many circles in a dozen?", new ArrayList<String>(List.of("10", "11", "12", "13")), 2),
                new Question("How many players are in one cricket team", new ArrayList<String>(List.of("2", "4", "11", "21")), 2),
                new Question("How many days are in a week?", new ArrayList<String>(List.of("3", "4.5", "7", "44")), 2)
        ));
        final List<RadioGroup> questionRadioGroups = new ArrayList<RadioGroup>();

        LinearLayout mainLayout = findViewById(R.id.main);

        IntStream.range(0, questions.size()).forEach(idx -> {
            Question q = questions.get(idx);

            TextView tv = new TextView(this);
            tv.setText(String.format("%s. %s", idx + 1, q.question));
            tv.setPadding(100, 50, 0, 0);
            mainLayout.addView(tv);

            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setPadding(100, 20, 0, 0);
            for (String option : q.options) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(option);
                radioButton.setTag(option);
                radioGroup.addView(radioButton);
            }
            mainLayout.addView(radioGroup);
            questionRadioGroups.add(radioGroup);
        });

        Button button = new Button(this);
        button.setText("Submit");
        button.setOnClickListener(v -> {
            final List<Integer> correctAnswers = new ArrayList<Integer>();
            for (Question question : questions) {
                correctAnswers.add(question.correctOptionIdx);
            }

            final List<Integer> givenAnswers = new ArrayList<Integer>();

            for (RadioGroup questionGroup : questionRadioGroups) {
                int checkedRadioButtonId = questionGroup.getCheckedRadioButtonId();

                if (checkedRadioButtonId != -1) {
                    RadioButton selectedRadioButton = questionGroup.findViewById(checkedRadioButtonId);
                    final int selectedIdx = questionGroup.indexOfChild(selectedRadioButton);
                    givenAnswers.add(selectedIdx);
                } else {
                    givenAnswers.add(-1);
                }
            }

//            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
//            intent.putIntegerArrayListExtra("correctAnswers", new ArrayList<Integer>(correctAnswers));
//            intent.putIntegerArrayListExtra("givenAnswers", new ArrayList<Integer>(givenAnswers));
//            startActivity(intent);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
            alertDialogBuilder.setTitle("Confirmation");
            alertDialogBuilder.setMessage("Are you sure you want to submit answers for evaluation?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putIntegerArrayListExtra("correctAnswers", new ArrayList<Integer>(correctAnswers));
                    intent.putIntegerArrayListExtra("givenAnswers", new ArrayList<Integer>(givenAnswers));
                    startActivity(intent);
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();
        });
        mainLayout.addView(button);
    }
}