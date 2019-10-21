package com.murdoch.ict376.whatsfordinner.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {

    @PrimaryKey(autoGenerate = true)
    public int categoryID;

    public String name;

    public Category(String name)
    {
        this.name = name;
    }

    public Category(int id,String name){
        this.categoryID = id;
        this.name = name;
    }
}
