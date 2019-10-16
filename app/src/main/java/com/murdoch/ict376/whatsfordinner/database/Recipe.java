package com.murdoch.ict376.whatsfordinner.database;

import android.graphics.Bitmap;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
@TypeConverters(DateTypeConverter.class)
public class Recipe implements Serializable{

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "RecipeID")
    private int recipeID;

    @ColumnInfo( name = "Name" )
    private String name;

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public byte[] getPicture() {
        return picture;
    }

    @ColumnInfo( name = "Picture", typeAffinity = ColumnInfo.BLOB)
    private byte[] picture;

    @ColumnInfo( name = "PreparationTime" )
    private String prepTime;

    @ColumnInfo( name = "CookingTime" )
    private String cookTime;

    @ColumnInfo( name = "WebsiteURL" )
    private String websiteURL;

    @ColumnInfo( name = "LetsEatRecipeUUID" )
    private String letsEatRecipeUUID;

    @ColumnInfo( name = "UserAdded" )
    private Boolean userAdded;

    @ColumnInfo( name = "DateModified" )
    private Date dateModified;

    @Ignore
    Bitmap storedImage;


    public Recipe(@NonNull String name)
    {
        this.name = name;
        dateModified = new Date(System.currentTimeMillis());
    }


  /*  public Recipe(int recipeID, String name, byte[] picture, String prepTime, String cookTime, String websiteURL, String letsEatRecipeUUID, Boolean userAdded,Date dateModified)
    {
        this.recipeID = recipeID;
        this.name = name;
        this.picture = picture;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.websiteURL = websiteURL;
        this.letsEatRecipeUUID = letsEatRecipeUUID;
        this.userAdded = userAdded;
        this.dateModified = dateModified;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getLetsEatRecipeUUID() {
        return letsEatRecipeUUID;
    }

    public void setLetsEatRecipeUUID(String letsEatRecipeUUID) {
        this.letsEatRecipeUUID = letsEatRecipeUUID;
    }

    public Boolean getUserAdded() {
        return userAdded;
    }

    public void setUserAdded(Boolean userAdded) {
        this.userAdded = userAdded;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }
}
