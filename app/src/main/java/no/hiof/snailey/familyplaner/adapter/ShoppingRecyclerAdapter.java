package no.hiof.snailey.familyplaner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import no.hiof.snailey.familyplaner.R;
import no.hiof.snailey.familyplaner.model.shopping;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class ShoppingRecyclerAdapter extends FirebaseRecyclerAdapter<shopping, ShoppingRecyclerAdapter.shoppingsViewholder> {

    public ShoppingRecyclerAdapter(
            @NonNull FirebaseRecyclerOptions<shopping> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "shopping_view_layout.xml") iwth data in
    // model class(here "shopping_view_layout.class")
    @Override
    protected void
    onBindViewHolder(@NonNull shoppingsViewholder holder,
                     int position, @NonNull shopping model)
    {

        // Add title from model class (here
        // "shopping_view_layout.class")to appropriate view in Card
        // view (here "shopping_view_layout.xml")
        holder.title.setText(model.getTitle());

        // Add number from model class (here
        // "shopping_view_layout.class")to appropriate view in Card
        // view (here "shopping_view_layout.xml")
        holder.number.setText(String.valueOf(model.getNumber()));

    }

    // Function to tell the class about the Card view (here
    // "shopping_view_layout.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public shoppingsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_view_layout, parent, false);
        return new ShoppingRecyclerAdapter.shoppingsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "shopping_view_layout.xml")
    class shoppingsViewholder
            extends RecyclerView.ViewHolder {
        TextView title, number;
        public shoppingsViewholder(@NonNull View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            number = itemView.findViewById(R.id.number);

        }
    }
}