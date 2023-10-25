package com.vikas.notescover.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vikas.notescover.AddNote;
import com.vikas.notescover.R;
import com.vikas.notescover.model.CategorieModel;

import java.util.ArrayList;

public class categorieAdapter extends RecyclerView.Adapter<categorieAdapter.categorieViewHolder> {

    Context context;
    ArrayList<CategorieModel> categorieModelsArrayList;

    public categorieAdapter(Context context, ArrayList<CategorieModel> arrayItem) {
        this.context = context;
        categorieModelsArrayList = arrayItem;
    }

    @NonNull
    @Override
    public categorieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.categorie_view_holder, parent, false);
        return new categorieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categorieViewHolder holder, int position) {
      holder.categoriesLogoImg.setImageResource(categorieModelsArrayList.get(position).getCategoriesLogo());
      holder.categorieNameTxt.setText(categorieModelsArrayList.get(position).getCategoriesName());

    }

    @Override
    public int getItemCount() {
        return categorieModelsArrayList.size();
    }

    //ViewHolder
    public  class categorieViewHolder extends RecyclerView.ViewHolder{
        private ImageView categoriesLogoImg;
        private TextView categorieNameTxt;

        public categorieViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriesLogoImg=itemView.findViewById(R.id.setcategerie_imgview);
            categorieNameTxt=itemView.findViewById(R.id.setcategeriestitle_txt);
        }
    }
}
