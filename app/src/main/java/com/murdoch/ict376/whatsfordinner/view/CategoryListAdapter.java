package com.murdoch.ict376.whatsfordinner.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.murdoch.ict376.whatsfordinner.R;
import com.murdoch.ict376.whatsfordinner.database.Category;
import com.murdoch.ict376.whatsfordinner.database.Recipe;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView categoryNameView;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryNameView = itemView.findViewById(R.id.categoryName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            categoryListener.recyclerViewListClicked(v,this.getLayoutPosition());
        }



    }

    private final LayoutInflater mInflator;
    private List<Category> mCategory;
    private static RecyclerViewClickListener categoryListener;

    public CategoryListAdapter(Context context, RecyclerViewClickListener itemListener) {
        mInflator = LayoutInflater.from(context);
        this.categoryListener = itemListener;
    }



    @Override
    public CategoryListAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.category_recyclerview_item,parent, false);
        return new CategoryListAdapter.CategoryViewHolder(itemView);
    }

    public void setCategories(List<Category> categories) {
        mCategory = categories;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.CategoryViewHolder holder, int position) {
        if (mCategory != null) {
            Category current = mCategory.get(position);
            holder.categoryNameView.setText(current.name);

        } else {
            holder.categoryNameView.setText("No Name");
        }


    }

    public Category getCategory(int index) {
        return mCategory.get(index);
    }


    @Override
    public int getItemCount() {
        if(mCategory != null)
            return mCategory.size();
        else return 0;
    }
}
