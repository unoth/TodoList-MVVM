package com.unoth.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.unoth.todolost.R;
import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private ArrayList<Note> notes = new ArrayList<>();
    private onClickListener onClickListener;

    public void setOnClickListener(NotesAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent).getContext()).inflate(
                R.layout.note_item,
                parent,
                false
        );
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        viewHolder.textViewNote.setText(note.getText());
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
        int color = ContextCompat.getColor(viewHolder.itemView.getContext(), colorId);
        viewHolder.textViewNote.setBackgroundColor(color);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(note);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNote = itemView.findViewById(R.id.noteItem);
        }
    }

    interface onClickListener {
        void onClick(Note note);
    }
}
