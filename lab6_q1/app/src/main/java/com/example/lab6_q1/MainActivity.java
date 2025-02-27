package com.example.lab6_q1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

class Recipe implements Serializable {
    private float id;
    private String name;
    ArrayList<String> ingredients = new ArrayList<String>();
    ArrayList<String> instructions = new ArrayList<String>();
    private float prepTimeMinutes;
    private float cookTimeMinutes;
    private float servings;
    private String difficulty;
    private String cuisine;
    private float caloriesPerServing;
    ArrayList<String> tags = new ArrayList<String>();
    private float userId;
    private String image;
    private float rating;
    private float reviewCount;
    ArrayList<String> mealType = new ArrayList<String>();

    // Getter Methods
    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    public float getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    public float getServings() {
        return servings;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCuisine() {
        return cuisine;
    }

    public float getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public float getUserId() {
        return userId;
    }

    public String getImage() {
        return image;
    }

    public float getRating() {
        return rating;
    }

    public float getReviewCount() {
        return reviewCount;
    }

    public String getIngredients() {
        return String.join(", ", ingredients);
    }

    public String getInstructions() {
        StringBuilder formattedString = new StringBuilder("\n");

        for (int i = 0; i < instructions.size(); i++) {
            formattedString.append((i + 1)).append(". ").append(instructions.get(i)).append("\n");
        }

        return formattedString.toString();
    }

    public String getTags() {
        return String.join(", ", tags);
    }
}

class ApiResponse {
    ArrayList<Recipe> recipes = new ArrayList<>();
    private float total;
    private float skip;
    private float limit;

    public float getTotal() {
        return total;
    }

    public float getSkip() {
        return skip;
    }

    public float getLimit() {
        return limit;
    }
}

interface API {
    @GET("recipes")
    Call<ApiResponse> getRecipes();

    @GET("recipes/1")
    Call<Recipe> getOneRecipe();
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

        TextView resultTextview = findViewById(R.id.resultTextview);
        ListView listView = findViewById(R.id.listView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);

//        Call<Recipe> call = api.getOneRecipe();
//        call.enqueue(new Callback<Recipe>() {
//            @Override
//            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
//                Recipe recipe = response.body();
//                resultTextview.setText(recipe.getName());
//            }
//
//            @Override
//            public void onFailure(Call<Recipe> call, Throwable t) {
//                resultTextview.setText(t.toString());
//            }
//        });

        Call<ApiResponse> call = api.getRecipes();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.body() == null) {
                    resultTextview.setText("Failed to fetch recipes.");
                    return;
                }

                resultTextview.setVisibility(View.GONE);

                List<Recipe> recipes = response.body().recipes;
                List<String> names = Arrays.stream(recipes.stream().map(Recipe::getName).toArray(String[]::new)).toList();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, names);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener((parent, view, position, id) -> {
                    String recipeName = (String) parent.getItemAtPosition(position);
                    Recipe recipe = (Recipe) Arrays.stream(recipes.stream().filter(r ->
                            r.getName().equals(recipeName)
                    ).toArray()).toList().getFirst();

                    Toast.makeText(parent.getContext(), recipeName, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                    intent.putExtra("recipe", recipe);
                    startActivity(intent);
                });
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                resultTextview.setText(String.format("Error: %s", t));
            }
        });
    }
}