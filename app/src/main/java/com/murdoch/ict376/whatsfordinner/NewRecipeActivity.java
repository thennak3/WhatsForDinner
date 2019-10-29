package com.murdoch.ict376.whatsfordinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.IpCons;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.murdoch.ict376.whatsfordinner.database.Category;
import com.murdoch.ict376.whatsfordinner.database.Recipe;
import com.murdoch.ict376.whatsfordinner.database.RecipeCategory;
import com.murdoch.ict376.whatsfordinner.helper.ImageHelper;
import com.murdoch.ict376.whatsfordinner.view.RecipeViewModel;
import com.sayantan.advancedspinner.MultiSpinner;
import com.sayantan.advancedspinner.MultiSpinnerListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewRecipeActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.murdoch.ict376.whatsfordinner.recipelistsql.REPLY";
    public static final String EXTRA_CATEGORIES = "com.murdoch.ict376.whatsfordinner.recipelistsql.CATEGORIES";

    static final String RECIPE_DATA = "com.murdoch.ict376.whatsfordinner.RECIPEDATA";

    private EditText mEditRecipeNameView;
    private EditText mEditPreparationTimeView;
    private EditText mEditCookingTimeView;
    private EditText mEditWebsiteURLView;
    private TextView mLabelTitle;
    private ImageView mImageView;

    private MultiSpinner mCategorySpinner;

    private Boolean isEdit = false;

    private Recipe mRecipe;

    ImageHelper imageHelper;

    List<Category> mCategories;
    List<RecipeCategory> recipeCategories;

    private LifecycleRegistry lifecycleRegistry;

    private Bitmap selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);

        setContentView(R.layout.activity_new_recipe);

        mEditRecipeNameView = findViewById(R.id.recipe_name);
        mEditPreparationTimeView = findViewById(R.id.preparation_time);
        mEditCookingTimeView = findViewById(R.id.cooking_time);
        mEditWebsiteURLView = findViewById(R.id.website_url);
        mLabelTitle = findViewById(R.id.header);
        mImageView = findViewById(R.id.recipe_image);
        mCategorySpinner = findViewById(R.id.category_spinner);


        imageHelper = new ImageHelper(this);



        RecipeViewModel recipeViewModel = new RecipeViewModel(getApplication());

        recipeViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                mCategories = categories;

                List<String> categoryNames = new ArrayList<>();
                if (categories != null) {
                    for (int i = 0; i < categories.size(); i++) {
                        categoryNames.add(categories.get(i).getName());
                    }

                    mCategorySpinner.setSpinnerList(categoryNames);
                    mCategorySpinner.selectNone();

                    if (isEdit) {
                        recipeCategories = recipeViewModel.getAllRecipeCategoriesByRecipeID(mRecipe.getRecipeID());

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // Stuff that updates the UI
                                boolean[] selected = new boolean[categories.size()];
                                for (int i = 0; i < recipeCategories.size(); i++) {
                                    int recipeCategoryID = recipeCategories.get(i).CategoryID;
                                    for (int j = 0; j < categories.size(); j++) {
                                        if (categories.get(j).categoryID == recipeCategoryID) {
                                            selected[j] = true;
                                            break;
                                        }

                                    }
                                }
                                mCategorySpinner.setSelected(selected);
                            }

                        });
                    }


                }
            }
        });


        mCategorySpinner.addOnItemsSelectedListener(new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List choices, boolean[] selected) {

            }
        });


        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                //Get fields


                if(!isEdit) {
                    if (TextUtils.isEmpty(mEditRecipeNameView.getText())) {
                        setResult(RESULT_CANCELED, replyIntent);
                    } else {
                        mRecipe = new Recipe(mEditRecipeNameView.getText().toString());
                        if(selectedBitmap != null)
                            mRecipe.SetImage(selectedBitmap);
                        updateFields();
                        int[] retCatValues = getCategoryFields();
                        replyIntent.putExtra(EXTRA_REPLY, mRecipe);
                        replyIntent.putExtra(EXTRA_CATEGORIES, retCatValues);
                        setResult(RESULT_OK, replyIntent);
                    }
                }
                else {
                    if (TextUtils.isEmpty(mEditRecipeNameView.getText())) {
                        setResult(RESULT_CANCELED, replyIntent);
                    } else {
                        mRecipe.setName(mEditRecipeNameView.getText().toString());
                        updateFields();
                        int[] retCatValues = getCategoryFields();
                        replyIntent.putExtra(EXTRA_REPLY,mRecipe);
                        replyIntent.putExtra(EXTRA_CATEGORIES, retCatValues);
                        setResult(RESULT_OK,replyIntent);

                    }
                }
                finish();
            }
        });

        Intent intent = getIntent();

        if(intent.hasExtra(RECIPE_DATA)) {
            mRecipe = (Recipe)intent.getSerializableExtra(RECIPE_DATA);
            mLabelTitle.setText(R.string.edit_recipe);
            isEdit = true;
            setFields();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data)
    {
        if(ImagePicker.shouldHandle(requestCode,resultCode,data))
        {
            imageHelper.SetImages((ArrayList<Image>)ImagePicker.getImages(data));
            if(imageHelper.GetCount() > 0) {

                Bitmap bitmap = imageHelper.LoadImage(imageHelper.GetImages().get(0));
                if(bitmap != null) {
                    if(mRecipe == null)
                        mRecipe = new Recipe("");
                    mRecipe.SetImage(bitmap);
                    selectedBitmap = bitmap;
                    setImageField();
                }

            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }



    private void setFields()
    {
        mEditRecipeNameView.setText(mRecipe.getName());
        mEditCookingTimeView.setText(mRecipe.getCookTime());
        mEditPreparationTimeView.setText(mRecipe.getPrepTime());
        mEditWebsiteURLView.setText(mRecipe.getWebsiteURL());
        setImageField();
    }

    private int[] getCategoryFields() {
        boolean[] selected = mCategorySpinner.getSelected();
        int scount = 0;
        for(int i = 0;i<selected.length;i++)
        {
            if(selected[i])
                scount++;
        }

        int[] categoryValues = new int[scount];
        scount = 0;
        for(int i = 0;i<selected.length;i++)
        {
            if(selected[i]) {
                categoryValues[scount] = mCategories.get(i).categoryID;
                scount++;
            }
        }
        return categoryValues;
    }

    private void setImageField()
    {
        if(mRecipe.GetImage() == null)
        {
            mImageView.setImageResource(R.drawable.noimage);
        }
        else
        {
            mImageView.setImageBitmap(mRecipe.GetImage());
        }
    }

    private void updateFields()
    {
        mRecipe.setPrepTime(mEditPreparationTimeView.getText().toString());
        mRecipe.setCookTime(mEditCookingTimeView.getText().toString());
        mRecipe.setWebsiteURL(mEditWebsiteURLView.getText().toString());
    }

    public static Intent GetEditRecipeIntent(Context packageContext, Recipe recipe)
    {
        Intent intent = new Intent(packageContext,NewRecipeActivity.class);
        intent.putExtra(RECIPE_DATA,recipe);
        return intent;
    }

    public ImagePicker CreateImagePicker()
    {
        return ImagePicker.create(this)
                .returnMode(ReturnMode.ALL)
                .single()
                .showCamera(true)
                .imageDirectory("Lets Eat");
    }


    public void showImagePickerDialog(View view)
    {
        ImagePicker imagePicker = CreateImagePicker();

        startActivityForResult(imagePicker.getIntent(this),IpCons.RC_IMAGE_PICKER);
    }

}
