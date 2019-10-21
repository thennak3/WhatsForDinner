package com.murdoch.ict376.whatsfordinner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert
    void Insert(Category... categories);

    @Update
    void Update(Category... categories);

    @Delete
    void Delete(Category... categories);

    @Query("select * from category")
    LiveData<List<Category>> getAllCategories();

}
