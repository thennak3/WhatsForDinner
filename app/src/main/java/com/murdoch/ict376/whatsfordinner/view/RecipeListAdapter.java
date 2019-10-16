package com.murdoch.ict376.whatsfordinner.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.murdoch.ict376.whatsfordinner.R;
import com.murdoch.ict376.whatsfordinner.database.Recipe;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>
{
    public static class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView recipeItemView;
        private final TextView recipeLastEaten;
        private final ImageView recipeImage;



        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeItemView = itemView.findViewById(R.id.recipeName);
            recipeLastEaten = itemView.findViewById(R.id.tvLastEaten);
            recipeImage = itemView.findViewById(R.id.recipeImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v,this.getLayoutPosition());
        }
    }

    private final LayoutInflater mInflator;
    private List<Recipe> mRecipe;
    private static RecyclerViewClickListener itemListener;

    //Constructor
    public RecipeListAdapter(Context context,RecyclerViewClickListener itemListener) {
        mInflator = LayoutInflater.from(context);
        this.itemListener = itemListener;

    }

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
            if(current.GetImage() != null)
                holder.recipeImage.setImageBitmap(current.GetImage());
            else
                holder.recipeImage.setImageResource(R.drawable.noimage);

        } else {
            holder.recipeItemView.setText("No Name");
        }


    }
    public void setRecipes(List<Recipe> recipes) {
        mRecipe = recipes;
        notifyDataSetChanged();
    }

    public Recipe getRecipe(int index) {
        return mRecipe.get(index);
    }

    @Override
    public int getItemCount() {
        if(mRecipe != null)
            return mRecipe.size();
        else return 0;
    }

}
