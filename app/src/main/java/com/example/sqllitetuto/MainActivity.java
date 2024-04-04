package com.example.sqllitetuto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ListView noteListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        loadFromDBToMemory();
        setNoteAdapter();
        setOnclickListener();

    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.getInstance(this);
        sqLiteManager.populateNoteListArray();
    }

    private void initWidgets() {
        noteListView = findViewById(R.id.noteList);
    }
    private void setNoteAdapter() {
        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(),Note.nonDeletedNotes());
        noteListView.setAdapter(noteAdapter);
    }
    public void newNote(View view){
        Intent newNoteIntent = new Intent(this, NoteDetailActivity.class);
        startActivity(newNoteIntent);
    }
    private void setOnclickListener(){
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note selectedNote = (Note)noteListView.getItemAtPosition(position);
                Intent editIntent = new Intent(getApplicationContext(), NoteDetailActivity.class);
                editIntent.putExtra(Note.NOTE_EDIT_EXTRA, selectedNote.getId());
                startActivity(editIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNoteAdapter();
    }
}