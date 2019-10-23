package com.murdoch.ict376.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.murdoch.ict376.whatsfordinner.database.Recipe;
import com.murdoch.ict376.whatsfordinner.view.RecipeListAdapter;
import com.murdoch.ict376.whatsfordinner.view.RecipeViewModel;
import com.murdoch.ict376.whatsfordinner.view.RecyclerViewClickListener;

import java.util.List;

public class MainActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void LaunchRecipes(View view) {
        Intent intent = new Intent(this,RecipeList.class);
        startActivity(intent);
    }

    public void LaunchCategories(View view) {
        Intent intent = new Intent(this,CategoriesListActivity.class);
        startActivity(intent);
    }

    public void LaunchCalendar(View view) {
        Intent intent = new Intent(this,Calendar.class);
        startActivity(intent);
    }


}
