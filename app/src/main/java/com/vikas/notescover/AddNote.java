package com.vikas.notescover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vikas.notescover.model.AppDatabase;
import com.vikas.notescover.model.NotesEntity;
import com.vikas.notescover.model.NotesRepro;
import com.vikas.notescover.view.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddNote extends AppCompatActivity {

    private TextInputEditText titleTxtEdt,storyTxtEdt;
    private ImageView saveImg,convertPdf;
    private AppDatabase appDatabase;
    ArrayList<NotesEntity> notesArrayList=new ArrayList<>();


    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        titleTxtEdt=findViewById(R.id.title_edt);
        storyTxtEdt=findViewById(R.id.story_edt);
        saveImg=findViewById(R.id.save_img);
        convertPdf=findViewById(R.id.savepdf_txt);

        appDatabase=AppDatabase.getInstance(this);


      //  new RetrieveNotesTask().execute();
        //pressing Adapter list
        if (NotesAdapter.onClickAdapter) {
            // Code for update mode
            Intent intent = getIntent();
            NotesEntity notes = (NotesEntity) intent.getSerializableExtra(NotesAdapter.KEY);

            // Populate fields with existing note data
            titleTxtEdt.setText(notes.getTitle());
            storyTxtEdt.setText(notes.getStory());

            saveImg.setOnClickListener(e -> {
                if ((titleTxtEdt.getText() + "").isEmpty()) {
                    titleTxtEdt.setError("Please Enter Title");
                }
                if (!(storyTxtEdt.getText() + "").isEmpty()) {

                    new UpdateNoteTask(appDatabase.notesRepro(), titleTxtEdt.getText() + "", storyTxtEdt.getText() + "").execute(notes);

                    Toast.makeText(this, "Successfully Updated", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            });

            // Reset the flag to avoid entering update mode again without selection
            NotesAdapter.onClickAdapter = false;
        } else {
            // Code for adding a new note (as it was in your original code)
            saveImg.setOnClickListener(e -> {
                if ((titleTxtEdt.getText() + "").isEmpty()) {
                    titleTxtEdt.setError("Please Enter Title");
                }
                if (!(storyTxtEdt.getText() + "").isEmpty()) {
                    new InsertNoteTask().execute(new NotesEntity(titleTxtEdt.getText() + "", storyTxtEdt.getText() + ""));
                    Toast.makeText(this, "Successfully Inserted", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            });
        }



    }

   



    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    public class InsertNoteTask extends AsyncTask<NotesEntity,Void,Void>{

        @Override
        protected Void doInBackground(NotesEntity... notesEntities) {
            appDatabase.notesRepro().insert(notesEntities[0]);
            return null;
        }
    }
    public class RetrieveNotesTask extends AsyncTask<Void,Void,List<NotesEntity>>{

        @Override
        protected List<NotesEntity> doInBackground(Void... voids) {
            return appDatabase.notesRepro().getAllNotes();
        }

        @Override
        protected void onPostExecute(List<NotesEntity> notesEntities) {
            super.onPostExecute(notesEntities);
            for(NotesEntity notes:notesEntities){
                notesArrayList.add(notes);

            }
        }
    }
    public class UpdateNoteTask extends AsyncTask<NotesEntity,Void,Void>{
        private NotesRepro notesRepro;
        private String updatedTitle;
        private String updatedStory;

        public UpdateNoteTask(NotesRepro notesRepro, String updatedTitle, String updatedStory) {
            this.notesRepro = notesRepro;
            this.updatedTitle = updatedTitle;
            this.updatedStory = updatedStory;
        }
        @Override
        protected Void doInBackground(NotesEntity... notesEntities) {
            NotesEntity note = notesEntities[0];
            note.setTitle(updatedTitle);
            note.setStory(updatedStory);
            appDatabase.notesRepro().update(note);
            return null;
        }
    }
}