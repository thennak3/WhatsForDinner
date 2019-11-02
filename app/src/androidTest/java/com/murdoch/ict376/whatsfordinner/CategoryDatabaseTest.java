package com.murdoch.ict376.whatsfordinner;


import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.murdoch.ict376.whatsfordinner.database.Category;
import com.murdoch.ict376.whatsfordinner.database.CategoryDAO;
import com.murdoch.ict376.whatsfordinner.database.LetsEatDatabase;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class CategoryDatabaseTest {

    private CategoryDAO mCategoryDAO;
    private LetsEatDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, LetsEatDatabase.class).build();
        mCategoryDAO = db.categoryDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeCategoryAndCheckInList() throws Exception {
        Category category = CategoryTest.createCategory("Testing");
        Long returnID = mCategoryDAO.insert(category);

        Category byID = mCategoryDAO.getByCategoryID(returnID.intValue());
        assertThat(byID.categoryID).isEqualTo(returnID);

    }

    @Test
    public void updateCategoryAndCheckInList() throws Exception {
        Category category = CategoryTest.createCategory("Testing");
        Long returnID = mCategoryDAO.insert(category);

        String updated_name = "Testing 2";
        CategoryTest.updateCategory(category, updated_name);
        mCategoryDAO.updateByName(returnID.intValue(), updated_name);

        Category byID = mCategoryDAO.getByCategoryID(returnID.intValue());
        assertThat(byID.categoryID).isEqualTo(returnID);
        assertThat(updated_name).isEqualTo(byID.getName());

    }

    @Test
    public void DeleteCategoryAndCheckInList() throws Exception {
        Category category = CategoryTest.createCategory("Testing");
        Long returnID = mCategoryDAO.insert(category);

        mCategoryDAO.deleteByID(returnID.intValue());

        Category byID = mCategoryDAO.getByCategoryID(returnID.intValue());
        assertThat(byID).isNull();

    }


}
