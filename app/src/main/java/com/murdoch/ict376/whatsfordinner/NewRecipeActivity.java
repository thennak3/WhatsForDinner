package com.murdoch.ict376.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewRecipeActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.murdoch.ict376.whatsfordinner.recipelistsql.REPLY";

    private EditText mEditRecipeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        mEditRecipeView = findViewById(R.id.edit_recipe);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(mEditRecipeView.getText())) {
                    setResult(RESULT_CANCELED,replyIntent);
                } else {
                    String recipe = mEditRecipeView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY,recipe);
                    setResult(RESULT_OK,replyIntent);
                }
                finish();
            }
        });

    }
}
