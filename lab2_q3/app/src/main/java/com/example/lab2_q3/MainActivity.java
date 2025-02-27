package com.example.lab2_q3;

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

        colorMap.put("Red", Color.RED);
        colorMap.put("Green", Color.GREEN);
        colorMap.put("Blue", Color.BLUE);
        colorMap.put("Yellow", Color.YELLOW);
        colorMap.put("Purple", Color.parseColor("#800080"));
        colorMap.put("Cyan", Color.CYAN);
        colorMap.put("Magenta", Color.MAGENTA);
        colorMap.put("Black", Color.BLACK);
        colorMap.put("White", Color.WHITE);
        colorMap.put("Orange", Color.parseColor("#FFA500"));
        colorMap.put("Pink", Color.parseColor("#FFC0CB"));
        colorMap.put("Brown", Color.parseColor("#A52A2A"));
        colorMap.put("Gray", Color.GRAY);
        colorMap.put("LightBlue", Color.parseColor("#ADD8E6"));
        colorMap.put("Teal", Color.parseColor("#008080"));
        colorMap.put("Lime", Color.parseColor("#00FF00"));
        colorMap.put("Indigo", Color.parseColor("#4B0082"));
        colorMap.put("Violet", Color.parseColor("#EE82EE"));
        colorMap.put("Beige", Color.parseColor("#F5F5DC"));
        colorMap.put("Lavender", Color.parseColor("#E6E6FA"));
        colorMap.put("Coral", Color.parseColor("#FF7F50"));
        colorMap.put("Gold", Color.parseColor("#FFD700"));
        colorMap.put("Silver", Color.parseColor("#C0C0C0"));
        colorMap.put("Bronze", Color.parseColor("#CD7F32"));
        colorMap.put("DarkRed", Color.parseColor("#8B0000"));
        colorMap.put("DarkGreen", Color.parseColor("#006400"));
        colorMap.put("DarkBlue", Color.parseColor("#00008B"));
        colorMap.put("DarkOrange", Color.parseColor("#FF8C00"));
        colorMap.put("SlateBlue", Color.parseColor("#6A5ACD"));
        colorMap.put("Aquamarine", Color.parseColor("#7FFFD4"));
        colorMap.put("Chartreuse", Color.parseColor("#7FFF00"));
        colorMap.put("Crimson", Color.parseColor("#DC143C"));
        colorMap.put("Tomato", Color.parseColor("#FF6347"));
        colorMap.put("RoyalBlue", Color.parseColor("#4169E1"));
        colorMap.put("MediumPurple", Color.parseColor("#9370DB"));
        colorMap.put("MediumAquamarine", Color.parseColor("#66CDAA"));
        colorMap.put("Orchid", Color.parseColor("#DA70D6"));
        colorMap.put("HotPink", Color.parseColor("#FF69B4"));
        colorMap.put("DeepPink", Color.parseColor("#FF1493"));
        colorMap.put("LightSalmon", Color.parseColor("#FFA07A"));
        colorMap.put("MediumOrchid", Color.parseColor("#BA55D3"));
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