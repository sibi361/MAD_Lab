package com.example.lab2_q1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        String[] vegetables = {
                "Apple", "Mango", "Brinjal", "Lettuce", "Cauliflower", "Radish", "Beetroot",
                "Carrot", "Spinach", "Tomato", "Cucumber", "Pumpkin", "Potato", "Onion",
                "Garlic", "Peas", "Zucchini", "Green beans", "Cabbage", "Broccoli", "Kale",
                "Sweet potato", "Chili", "Bell pepper", "Mushroom", "Asparagus", "Leek",
                "Celery", "Artichoke", "Chard", "Fennel", "Turnip", "Parsnip", "Okra",
                "Squash", "Radicchio", "Watercress", "Rhubarb", "Taro", "Chayote", "Endive"
        };
        List<String> data = new ArrayList<String>(Arrays.asList(vegetables));

        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String item = parent.getItemAtPosition(position) + "";
            Toast.makeText(parent.getContext(), item, Toast.LENGTH_SHORT).show();
        });
    }
}