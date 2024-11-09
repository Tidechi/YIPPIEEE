package com.example.proyectodeejemplo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private List<Nota> ListaNotas;

    public NotesAdapter(List<Nota> ListaNotas, RecyclerViewInterface recyclerViewInterface) {
        this.ListaNotas = ListaNotas;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    // Method to update the dataset and refresh the RecyclerView
    public void updateData(List<Nota> newNotas) {
        this.ListaNotas = newNotas;
        notifyDataSetChanged(); // Notify RecyclerView to rebind all views
    }

    public Nota getNotaAt(int position) {
        return ListaNotas.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notas_layout, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nota nota = ListaNotas.get(position);
        holder.tituloTextView.setText(nota.getTitulo());
        holder.fechaTextView.setText(nota.getFecha());
        holder.descripcionTextView.setText(nota.getTexto());

        Log.d("Adapter", "Binding note at position " + position + ": " + nota.getTitulo());
    }

    @Override
    public int getItemCount() {
        Log.d("Adapter", "Total notes in adapter: " + ListaNotas.size());
        return ListaNotas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tituloTextView, fechaTextView, descripcionTextView;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.eltitulo);
            fechaTextView = itemView.findViewById(R.id.lafecha);
            descripcionTextView = itemView.findViewById(R.id.ladescripcion);

            itemView.setOnClickListener(view -> {
                if (recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onNotaClick(position);
                    }
                }
            });
        }
    }
}
