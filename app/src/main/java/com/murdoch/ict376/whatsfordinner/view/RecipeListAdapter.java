package com.murdoch.ict376.whatsfordinner.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.murdoch.ict376.whatsfordinner.R;
import com.murdoch.ict376.whatsfordinner.database.Recipe;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>
{
    class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final TextView recipeItemView;

        private RecipeViewHolder(View itemView) {
            super(itemView);
            recipeItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflator;
    private List<Recipe> mRecipe;

    //Constructor
    public RecipeListAdapter(Context context) { mInflator = LayoutInflater.from(context); }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.recyclerview_item,parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        if (mRecipe != null) {
            Recipe current = mRecipe.get(position);
            holder.recipeItemView.setText(current.getName());
        } else {
            holder.recipeItemView.setText("No Name");
        }
    }
    public void setRecipes(List<Recipe> recipes) {
        mRecipe = recipes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mRecipe != null)
            return mRecipe.size();
        else return 0;
    }

}
