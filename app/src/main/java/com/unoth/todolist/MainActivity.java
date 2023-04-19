package com.unoth.todolist;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unoth.todolost.R;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayoutNotes;
    private FloatingActionButton btnAddNote;
    private Database database = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void initViews() {
        linearLayoutNotes = findViewById(R.id.linearLayoutNotes);
        btnAddNote = findViewById(R.id.btnAddNote);
    }

    private void showNotes() {
        linearLayoutNotes.removeAllViews();
        for (Note note : database.getNotes()) {
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