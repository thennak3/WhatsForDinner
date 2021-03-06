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
    long[] insert(Category... categories);

    @Insert
    long insert(Category category);

    @Update
    void update(Category... categories);

    @Query("Update Category SET name=:new_name WHERE categoryID=:id")
    void updateByName(int id, String new_name);

    @Query("DELETE FROM Category WHERE categoryID = :id")
    void deleteByID(int id);

    @Delete
    void delete(Category... categories);

    @Query("select * from category")
    LiveData<List<Category>> getAllCategories();

    @Query("select * from category")
    List<Category> getAllCategoriesList();

    @Query("delete from category")
    void deleteAll();

    @Query("select * from category where categoryID=:id")
    Category getByCategoryID(int id);

}
