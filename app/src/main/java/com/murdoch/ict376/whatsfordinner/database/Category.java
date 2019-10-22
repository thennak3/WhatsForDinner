package com.murdoch.ict376.whatsfordinner.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Category implements Serializable {

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
