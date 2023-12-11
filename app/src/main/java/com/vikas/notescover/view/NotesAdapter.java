package com.vikas.notescover.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.vikas.notescover.AddNote;

import com.vikas.notescover.R;
import com.vikas.notescover.model.Notes;
import com.vikas.notescover.model.NotesEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>  {


    ArrayList<NotesEntity> list;

    ArrayList<NotesEntity> filterList;
    Context context;
     public static final String KEY="NOTES_TRANSFTER";
     public  static  boolean onClickAdapter=false;



    public NotesAdapter(ArrayList<NotesEntity> list, Context context) {
        this.list = list;
        this.context = context;
        this.filterList = new ArrayList<>(list);

    }
    public void setData(ArrayList<NotesEntity> newNotesList) {
        list = newNotesList;
    }

    public  NotesEntity getItemByPosition(int position){
        return list.get(position);
    }
    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void filter(String text) {
        filterList.clear();

        if (text.isEmpty()) {
            filterList.addAll(list); // If the search query is empty, show all items.
        } else {
            text = text.toLowerCase();
            for (NotesEntity item : list) {
                if (item.getTitle().toLowerCase().contains(text)) {
                    filterList.add(item); // Add matching items to the filtered list.
                }
            }
        }
        notifyDataSetChanged(); // Notify the adapter that the data has changed.
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String dataAndMonth(){
        LocalDate currentDate=LocalDate.now();

        // Define a custom date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        String formattedDate = currentDate.format(formatter);

        return formattedDate;
    }



    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notet_view_holder, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.date.setText(dataAndMonth());
        }
        holder.story.setText(list.get(position).getStory());
        holder.itemView.setOnClickListener(e->{
            Intent intent = new Intent(context, AddNote.class);
            onClickAdapter=true;
            intent.putExtra(KEY,list.get(position));
            context.startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView title,story,date;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.settitle_txt);
            date=itemView.findViewById(R.id.setdate_txt);
            story=itemView.findViewById(R.id.setstory_txt);
        }
    }
}
