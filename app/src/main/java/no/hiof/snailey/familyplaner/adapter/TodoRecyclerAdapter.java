package no.hiof.snailey.familyplaner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import no.hiof.snailey.familyplaner.R;
import no.hiof.snailey.familyplaner.model.todo;

public class TodoRecyclerAdapter extends FirebaseRecyclerAdapter<todo, TodoRecyclerAdapter.todosViewholder> {

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
                     int position, @NonNull todo model)
    {

        // Add title from model class (here
        // "shopping_view_layout.class")to appropriate view in Card
        // view (here "shopping_view_layout.xml")
        holder.title.setText(model.getTitle());

        // Add description from model class (here
        // "shopping_view_layout.class")to appropriate view in Card
        // view (here "shopping_view_layout.xml")
        holder.description.setText(model.getDescription());
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

    // Sub Class to create references of the views in Crad
    // view (here "shopping_view_layout.xml")
    class todosViewholder
            extends RecyclerView.ViewHolder {
        TextView title, description;
        public todosViewholder(@NonNull View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.number);

        }
    }
}