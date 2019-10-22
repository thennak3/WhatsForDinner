package com.murdoch.ict376.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.murdoch.ict376.whatsfordinner.database.Category;
import com.murdoch.ict376.whatsfordinner.database.Recipe;

public class NewCategoryActivity extends AppCompatActivity {

    private final static String CATEGORY_DATA = "WHATSFORDINNER.CATEGORYDATA";

    public static final String EXTRA_REPLY = "com.murdoch.ict376.whatsfordinner.categorylistsql.REPLY";

    Category mCategory;

    TextView mCategoryNameView;

    Boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        mCategoryNameView = findViewById(R.id.category_name);


        final Button button = findViewById(R.id.button_save_category);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                //Get fields


                if(!isEdit) {
                    if (TextUtils.isEmpty(mCategoryNameView.getText())) {
                        setResult(RESULT_CANCELED, replyIntent);
                    } else {
                        mCategory = new Category(mCategoryNameView.getText().toString());

                        replyIntent.putExtra(EXTRA_REPLY, mCategory);
                        setResult(RESULT_OK, replyIntent);
                    }
                }
                else {
                    if (TextUtils.isEmpty(mCategoryNameView.getText())) {
                        setResult(RESULT_CANCELED, replyIntent);
                    } else {
                        mCategory.name = mCategoryNameView.getText().toString();
                        replyIntent.putExtra(EXTRA_REPLY,mCategory);
                        setResult(RESULT_OK,replyIntent);
                    }
                }
                finish();
            }
        });

        Intent intent = getIntent();

        if(intent.hasExtra(CATEGORY_DATA)) {
            mCategory = (Category) intent.getSerializableExtra(CATEGORY_DATA);

            isEdit = true;
            mCategoryNameView.setText(mCategory.name);
        }
    }

    public static Intent GetEditCategoryIntent(Context packageContext, Category category)
    {
        Intent intent = new Intent(packageContext,NewCategoryActivity.class);
        intent.putExtra(CATEGORY_DATA,category);
        return intent;
    }
}
