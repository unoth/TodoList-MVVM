package com.unoth.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class AddNoteActivity extends AppCompatActivity {

    private EditText edInput;
    private Button btnSave;
    private RadioButton radioLow;
    private RadioButton radioMedium;
    private AddNoteViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose) {
                    finish();
                }
            }
        });
        initViews();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewNote();
            }
        });
    }

    private void initViews() {
        edInput = findViewById(R.id.edInput);
        btnSave = findViewById(R.id.btnSave);
        radioLow = findViewById(R.id.radioLow);
        radioMedium = findViewById(R.id.radioMedium);
    }

    private void saveNewNote() {
        String text = edInput.getText().toString().trim();
        int priority = getPriority();
        Note note = new Note(text, priority);
        viewModel.add(note);
    }

    private int getPriority() {
        int priority;
        if (radioLow.isChecked()) {
            priority = 0;
        } else if (radioMedium.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }

}