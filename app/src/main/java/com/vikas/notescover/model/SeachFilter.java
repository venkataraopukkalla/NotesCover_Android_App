package com.vikas.notescover.model;

import android.widget.Filter;

import com.vikas.notescover.view.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class SeachFilter extends Filter {

  //implements
    private NotesAdapter adapter;
    private List<NotesEntity> orginalList;
    private List<NotesEntity> filterList;

    public SeachFilter(NotesAdapter notesAdapter, List<NotesEntity> orginalList) {
        this.adapter = notesAdapter;
        this.orginalList = orginalList;
        this.filterList=new ArrayList<>(orginalList);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterPattern = constraint.toString().toLowerCase().trim();
        filterList.clear();

        for(NotesEntity notes:orginalList){
            if(notes.getTitle().toLowerCase().contains(filterPattern)){
                filterList.add(notes);
            }
        }
        FilterResults filterResults = new FilterResults();
        filterResults.values=filterList;
        filterResults.count=filterList.size();

        return null;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        // Notify the adapter of changes and provide the filtered data


    }
}
