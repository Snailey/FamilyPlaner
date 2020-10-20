package no.hiof.snailey.familyplaner.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.core.Context;

import no.hiof.snailey.familyplaner.R;
import no.hiof.snailey.familyplaner.ToDoActivity;
import no.hiof.snailey.familyplaner.model.todo;

import static java.security.AccessController.getContext;

public class TodoRecyclerAdapter extends FirebaseRecyclerAdapter<todo, TodoRecyclerAdapter.todosViewholder>{

    public TodoRecyclerAdapter(
            @NonNull FirebaseRecyclerOptions<todo> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "shopping_view_layout.xml") iwth data in
    // model class(here "shopping_view_layout.class")
    @Override
    protected void
    onBindViewHolder(@NonNull TodoRecyclerAdapter.todosViewholder holder,
                     final int position, @NonNull todo model)
    {

        // Add title from model class (here
        // "shopping_view_layout.class")to appropriate view in Card
        // view (here "shopping_view_layout.xml")
        holder.title.setText(model.getTitle());

        // Add description from model class (here
        // "shopping_view_layout.class")to appropriate view in Card
        // view (here "shopping_view_layout.xml")
        holder.description.setText(model.getDescription());

        //holder.itemView.setOnClickListener(new View.OnClickListener() {

        //    @Override
        //    public void onClick(View view) {
        //        Log.i("TAG", "OnClick" + position);
        //    }
        //});
    }

    // Function to tell the class about the Card view (here
    // "shopping_view_layout.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public TodoRecyclerAdapter.todosViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_view_layout, parent, false);
        return new TodoRecyclerAdapter.todosViewholder(view);
    }


    // Sub Class to create references of the views in Card
    // view (here "shopping_view_layout.xml")
    class todosViewholder
            extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, description;
        ImageView delete;
        public todosViewholder(@NonNull View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.number);
            delete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(this);
            delete.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == delete.getId()){
                Toast.makeText(view.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }


        }
    }
}