package no.hiof.snailey.familyplaner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

import no.hiof.snailey.familyplaner.R;

public class RandomNumListAdapter extends RecyclerView.Adapter<ToDoRecyclerViewHolder> {
    private Random random;

    public RandomNumListAdapter(int seed) {
        this.random = new Random(seed);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.to_do_list_element;
    }

    @NonNull
    @Override
    public ToDoRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ToDoRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoRecyclerViewHolder holder, int position) {
        holder.getView().setText(String.valueOf(random.nextInt()));
    }

    @Override
    public int getItemCount() {
        return 100;
    }
}