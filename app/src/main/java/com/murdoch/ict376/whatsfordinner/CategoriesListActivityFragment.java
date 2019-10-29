package com.murdoch.ict376.whatsfordinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.murdoch.ict376.whatsfordinner.database.Category;
import com.murdoch.ict376.whatsfordinner.view.CategoryListAdapter;
import com.murdoch.ict376.whatsfordinner.view.CategoryViewModel;
import com.murdoch.ict376.whatsfordinner.view.RecyclerViewClickListener;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoriesListActivityFragment extends androidx.fragment.app.Fragment  implements RecyclerViewClickListener {

    public CategoriesListActivityFragment() {
    }

    CategoryListAdapter categoryListAdapter;
    CategoryViewModel categoryViewModel;
    View RootView;
    RecyclerView recyclerView;

    private int NEW_CATEGORY_REQUEST_CODE = 1;
    private int EDIT_CATEGORY_REQUEST_CODE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_categories_list, container, false);
        recyclerView = RootView.findViewById(R.id.categoryRecyclerView);
        return RootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceBundle) {
        super.onActivityCreated(savedInstanceBundle);


        categoryListAdapter = new CategoryListAdapter(getContext(), this);
        recyclerView.setAdapter(categoryListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(getActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryListAdapter.setCategories(categories);
            }
        });

        FloatingActionButton fab = RootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),NewCategoryActivity.class);
                startActivityForResult(intent,NEW_CATEGORY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == NEW_CATEGORY_REQUEST_CODE && resultCode == getActivity().RESULT_OK){
            Category category = (Category)data.getSerializableExtra(NewCategoryActivity.EXTRA_REPLY);
            categoryViewModel.insert(category);

        } else if(requestCode == EDIT_CATEGORY_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            Category category = (Category) data.getSerializableExtra(NewCategoryActivity.EXTRA_REPLY);
            categoryViewModel.update(category);
        }
        else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.empty_recipe_not_saved, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Intent intent = NewCategoryActivity.GetEditCategoryIntent(getActivity(),categoryListAdapter.getCategory(position));
        startActivityForResult(intent,EDIT_CATEGORY_REQUEST_CODE);
    }
}
