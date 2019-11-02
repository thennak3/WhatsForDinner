package com.murdoch.ict376.whatsfordinner;


import android.content.Context;

import com.murdoch.ict376.whatsfordinner.database.LetsEatDatabase;
import com.murdoch.ict376.whatsfordinner.database.Meal;
import com.murdoch.ict376.whatsfordinner.database.MealDAO;
import com.murdoch.ict376.whatsfordinner.helper.DateHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class MealDatabaseTest {

    private MealDAO mMealDAO;
    private LetsEatDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, LetsEatDatabase.class).build();
        mMealDAO = db.mealDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeMealAndCheckInList() throws Exception {
        Date date = new Date();
        Meal meal = MealTest.createMeal(2, date);
        Long returnID = mMealDAO.insert(meal);

        Meal byID = mMealDAO.findMealByID(returnID.intValue());
        assertThat(byID.getRecipeID()).isEqualTo(returnID);

    }


}
