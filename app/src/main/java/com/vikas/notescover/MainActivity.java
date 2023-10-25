package com.vikas.notescover;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vikas.notescover.model.AppDatabase;
import com.vikas.notescover.model.CategorieModel;
import com.vikas.notescover.model.NotesEntity;
import com.vikas.notescover.view.NotesAdapter;
import com.vikas.notescover.view.categorieAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView categorieRview, notesRecyleview;
    private ImageView addFeatureTxt;
    private AppDatabase appDatabase;
    private NotesAdapter notesAdapter;


   private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categorieRview = findViewById(R.id.categories_recycleview);
        notesRecyleview = findViewById(R.id.noteRecycleview);
        addFeatureTxt = findViewById(R.id.addfeature_txt);
        searchView=findViewById(R.id.searchview);


        // Add button click
        addFeatureTxt.setOnClickListener(e -> addFeature());

        // Database instance
        appDatabase = AppDatabase.getInstance(this);

        // Categorie setLayoutManager
        categorieRview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<CategorieModel> list = new ArrayList<>();
        list.add(new CategorieModel(R.drawable.person_logo, "Personal"));
        list.add(new CategorieModel(R.drawable.work_logo, "Work"));
        list.add(new CategorieModel(R.drawable.important_logo, "Important"));
        list.add(new CategorieModel(R.drawable.health_logo, "Health"));
        list.add(new CategorieModel(R.drawable.add_logo, ""));
        categorieRview.setAdapter(new categorieAdapter(this, list));

        notesAdapter = new NotesAdapter(new ArrayList<>(), this);
        notesRecyleview.setLayoutManager(new LinearLayoutManager(this));
        notesRecyleview.setAdapter(notesAdapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
        touchHelper.attachToRecyclerView(notesRecyleview);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                notesAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // notesAdapter.filter(newText);
                return false;
            }
        });



    }
    //its use to left swipe .. i.e move right to left
    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            NotesEntity noteToDelete = notesAdapter.getItemByPosition(position);
            deleteNote(noteToDelete,position);
           // notesAdapter.notifyItemRemoved(position);

        }
    };






    @Override
    protected void onResume() {
        super.onResume();
        new RetrieveNotesTask().execute();

    }

    private void addFeature() {
        Intent intent = new Intent(this, AddNote.class);
        startActivity(intent);
    }

    public class RetrieveNotesTask extends AsyncTask<Void, Void, List<NotesEntity>> {

        @Override
        protected List<NotesEntity> doInBackground(Void... voids) {
            return appDatabase.notesRepro().getAllNotes();
        }

        @Override
        protected void onPostExecute(List<NotesEntity> notesEntities) {
            super.onPostExecute(notesEntities);

            // Update the adapter with the new data
            notesAdapter.setData((ArrayList<NotesEntity>) notesEntities);
            notesAdapter.notifyDataSetChanged();


        }
    }
    private void deleteNote(NotesEntity note,int position) {
        // Delete the note from the database
        new DeleteNoteTask().execute(note);
        //delete from recycleview
        notesAdapter.deleteItem(position);
        Toast.makeText(this,"SucessFull Delete",Toast.LENGTH_SHORT).show();
    }

    private class DeleteNoteTask extends AsyncTask<NotesEntity, Void, Void> {
        @Override
        protected Void doInBackground(NotesEntity... notesEntities) {
            appDatabase.notesRepro().delete(notesEntities[0]);
            return null;
        }


    }

}
