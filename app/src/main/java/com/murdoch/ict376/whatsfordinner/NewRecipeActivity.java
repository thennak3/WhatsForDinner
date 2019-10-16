package com.murdoch.ict376.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.murdoch.ict376.whatsfordinner.database.Recipe;

public class NewRecipeActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.murdoch.ict376.whatsfordinner.recipelistsql.REPLY";

    static final String RECIPE_DATA = "com.murdoch.ict376.whatsfordinner.RECIPEDATA";

    private EditText mEditRecipeNameView;
    private EditText mEditPreparationTimeView;
    private EditText mEditCookingTimeView;
    private EditText mEditWebsiteURLView;
    private TextView mLabelTitle;


    private Boolean isEdit = false;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_recipe);

        mEditRecipeNameView = findViewById(R.id.recipe_name);
        mEditPreparationTimeView = findViewById(R.id.preparation_time);
        mEditCookingTimeView = findViewById(R.id.cooking_time);
        mEditWebsiteURLView = findViewById(R.id.website_url);
        mLabelTitle = findViewById(R.id.header);

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

    private void setFields()
    {
        mEditRecipeNameView.setText(mRecipe.getName());
        mEditCookingTimeView.setText(mRecipe.getCookTime());
        mEditPreparationTimeView.setText(mRecipe.getPrepTime());
        mEditWebsiteURLView.setText(mRecipe.getWebsiteURL());
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
}
