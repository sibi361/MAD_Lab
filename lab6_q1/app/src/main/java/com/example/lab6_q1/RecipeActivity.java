package com.example.lab6_q1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView resultTextview = findViewById(R.id.resultTextview);
        Button cookBtn = findViewById(R.id.cookBtn);
        Button returnBtn = findViewById(R.id.returnBtn);
        ImageView recipeImageView = findViewById(R.id.recipeImageView);

        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");
        assert recipe != null;

        resultTextview.setText(String.format("Name: %s\nPreparation time: %s\nCooking time: %s\n" +
                        "Servings: %s\nDifficulty: %s\nCuisine: %s\nCalories Per Serving: %s\n" +
                        "Rating: %s\nTags: %s",
                recipe.getName(), recipe.getPrepTimeMinutes(), recipe.getCookTimeMinutes(),
                recipe.getServings(), recipe.getDifficulty(), recipe.getCuisine(),
                recipe.getCaloriesPerServing(), recipe.getRating(), recipe.getTags()));

        cookBtn.setOnClickListener(v -> {
            Intent intent2 = new Intent(RecipeActivity.this, CookActivity.class);
            intent2.putExtra("ingredients", recipe.getIngredients());
            intent2.putExtra("instructions", recipe.getInstructions());
            startActivity(intent2);
        });

        returnBtn.setOnClickListener(v -> {
            this.finish();
        });

        Glide.with(this).load(recipe.getImage()).into(recipeImageView);
    }
}