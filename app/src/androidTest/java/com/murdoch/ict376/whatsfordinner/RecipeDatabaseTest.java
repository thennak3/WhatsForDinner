package com.murdoch.ict376.whatsfordinner;


import android.content.Context;

import com.murdoch.ict376.whatsfordinner.database.Category;
import com.murdoch.ict376.whatsfordinner.database.RecipeDAO;
import com.murdoch.ict376.whatsfordinner.database.LetsEatDatabase;
import com.murdoch.ict376.whatsfordinner.database.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class RecipeDatabaseTest {

    private RecipeDAO mRecipeDAO;
    private LetsEatDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, LetsEatDatabase.class).build();
        mRecipeDAO = db.recipeDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeRecipeAndCheckInList() throws Exception {
        Recipe recipe = RecipeTest.createRecipe("Test Recipe");
        Long returnID = mRecipeDAO.insert(recipe);

        Recipe byID = mRecipeDAO.findRecipeByID(returnID.intValue());
        assertThat(byID.getRecipeID()).isEqualTo(returnID);

    }

    @Test
    public void updateRecipeAndCheckInList() throws Exception {
        Recipe recipe = RecipeTest.createRecipe("Test Recipe");
        Long returnID = mRecipeDAO.insert(recipe);

        String updated_name = "Testing 2";
        RecipeTest.updateRecipe(recipe, updated_name);
        mRecipeDAO.updateRecipe(recipe);

        Recipe byID = mRecipeDAO.findRecipeByID(returnID.intValue());
        assertThat(byID.getRecipeID()).isEqualTo(returnID);

    }


}
