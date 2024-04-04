package com.example.sqllitetuto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

public class NoteDetailActivity extends AppCompatActivity {
    private EditText titleEditText,descEditText;
    private Button deleteButton;
    private Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initWidgets();
        checkForEditNote();
    }

    private void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        descEditText = findViewById(R.id.descEditText);
        deleteButton = findViewById(R.id.deleteNoteButton);
    }

    private void checkForEditNote() {
        Intent previousIntent = getIntent();
        int passedNote = previousIntent.getIntExtra(Note.NOTE_EDIT_EXTRA,-1);
        selectedNote = Note.getNoteForID(passedNote);
        if(selectedNote != null){
            titleEditText.setText(selectedNote.getTitle());
            descEditText.setText(selectedNote.getDescription());
        }else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }


    public void saveNote(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.getInstance(this);
        String title = String.valueOf(titleEditText.getText());
        String desc = String.valueOf(descEditText.getText());
        //System.out.println(title);
        if (selectedNote == null){
            int id = Note.notes.size();
            Note newNote = new Note(id, title, desc);
            Note.notes.add(newNote);
            sqLiteManager.addNoteToDatabase(newNote);
        }else {
            selectedNote.setTitle(title);
            selectedNote.setDescription(desc);
            sqLiteManager.addNoteToDatabase(selectedNote);
        }
        finish();
    }
    public void deleteNote(View view){
        selectedNote.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.getInstance(this);
        sqLiteManager.updateNoteInDB(selectedNote);
        finish();
    }
}