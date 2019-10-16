package com.murdoch.ict376.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.murdoch.ict376.whatsfordinner.database.Recipe;
import com.murdoch.ict376.whatsfordinner.helper.ImageHelper;

import java.util.ArrayList;

public class NewRecipeActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.murdoch.ict376.whatsfordinner.recipelistsql.REPLY";

    static final String RECIPE_DATA = "com.murdoch.ict376.whatsfordinner.RECIPEDATA";

    private EditText mEditRecipeNameView;
    private EditText mEditPreparationTimeView;
    private EditText mEditCookingTimeView;
    private EditText mEditWebsiteURLView;
    private TextView mLabelTitle;
    private ImageView mImageView;

    private Boolean isEdit = false;

    private Recipe mRecipe;

    ImageHelper imageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_recipe);

        mEditRecipeNameView = findViewById(R.id.recipe_name);
        mEditPreparationTimeView = findViewById(R.id.preparation_time);
        mEditCookingTimeView = findViewById(R.id.cooking_time);
        mEditWebsiteURLView = findViewById(R.id.website_url);
        mLabelTitle = findViewById(R.id.header);
        mImageView = findViewById(R.id.recipe_image);



        imageHelper = new ImageHelper(this);


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
                        updateFields();
                        replyIntent.putExtra(EXTRA_REPLY, mRecipe);
                        setResult(RESULT_OK, replyIntent);
                    }
                }
                else {
                    if (TextUtils.isEmpty(mEditRecipeNameView.getText())) {
                        setResult(RESULT_CANCELED, replyIntent);
                    } else {
                        mRecipe.setName(mEditRecipeNameView.getText().toString());
                        updateFields();
                        replyIntent.putExtra(EXTRA_REPLY,mRecipe);
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
                    mRecipe.SetImage(bitmap);
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
