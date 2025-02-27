package com.example.lab3_q1;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    final private Map<String, Integer> colorMap = new HashMap<>();

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

        colorMap.put("Moccasin", Color.parseColor("#FFE4B5"));
        colorMap.put("NavajoWhite", Color.parseColor("#FFDEAD"));
        colorMap.put("PapayaWhip", Color.parseColor("#FFEFD5"));
        colorMap.put("PeachPuff", Color.parseColor("#FFDAB9"));

        LinearLayout mainLayout = findViewById(R.id.main);
        RadioGroup radioGroup = findViewById(R.id.colorsRadioGroup);

        for (Map.Entry<String, Integer> entry : colorMap.entrySet()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(entry.getKey());
            radioButton.setTag(entry.getValue());
            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            if (selectedRadioButton != null) {
                int color = (int) selectedRadioButton.getTag();
                mainLayout.setBackgroundColor(color);
            }
        });
    }
}