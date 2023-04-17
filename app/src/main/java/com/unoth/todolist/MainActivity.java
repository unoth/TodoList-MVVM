package com.unoth.todolost;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayoutNotes;
    private FloatingActionButton btnAddNote;
    private ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Note note = new Note(i, "Note " + i, random.nextInt(3));
            notes.add(note);
        }
        showNotes();
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        linearLayoutNotes = findViewById(R.id.linearLayoutNotes);
        btnAddNote = findViewById(R.id.btnAddNote);
    }

    private void showNotes() {
        for (Note note : notes) {
            View view = getLayoutInflater().inflate(R.layout.note_item, linearLayoutNotes, false);
            TextView textViewNote = view.findViewById(R.id.noteItem);
            textViewNote.setText(note.getText());
            int colorId;
            switch (note.getPriority()) {
                case 0:
                    colorId = android.R.color.holo_green_light;
                    break;
                case 1:
                    colorId = android.R.color.holo_orange_light;
                    break;
                default:
                    colorId = android.R.color.holo_red_light;
            }
            int color = ContextCompat.getColor(this,colorId);
            textViewNote.setBackgroundColor(color);
            linearLayoutNotes.addView(view);
        }
    }
}