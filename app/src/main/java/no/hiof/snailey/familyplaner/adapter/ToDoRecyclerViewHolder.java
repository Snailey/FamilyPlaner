package no.hiof.snailey.familyplaner.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import no.hiof.snailey.familyplaner.R;

public class ToDoRecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView view;
    public ToDoRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.randomText);
    }

    public TextView getView(){
        return view;
    }
}