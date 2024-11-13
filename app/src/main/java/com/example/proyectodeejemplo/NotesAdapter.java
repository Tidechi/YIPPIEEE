package com.example.proyectodeejemplo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private List<Nota> ListaNotas;
    private List<Nota> ListaNotaOrigi;


    public NotesAdapter(List<Nota> ListaNotas, RecyclerViewInterface recyclerViewInterface) {
        this.ListaNotas = ListaNotas;
        this.recyclerViewInterface = recyclerViewInterface;
        ListaNotaOrigi = new ArrayList<>();
        ListaNotaOrigi.addAll(ListaNotas);


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


    public void filtrar(String textoBuscado){
        int longi= textoBuscado.length();
        if(longi==0){
            ListaNotas.clear();
            ListaNotas.addAll(ListaNotaOrigi);
        }else{


            //filtro de busqueda
            List<Nota> ListaFiltrada = ListaNotas.stream()
                    .filter(i -> {
                        boolean tituloTexto = i.getTitulo().toLowerCase().contains(textoBuscado.toLowerCase());
                        boolean fechaString= i.getFecha().toLowerCase().contains(textoBuscado.toLowerCase());

                        SimpleDateFormat formatoADate = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat formatoPalabras = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");

                        boolean fechaTexto = false;
                        try {
                            // Convierte el String a Date
                            Date fecha = formatoADate.parse(i.getFecha());
                            // Convierte la fecha a palabras
                            String fechaEnPalabras = formatoPalabras.format(fecha);
                            fechaTexto = fechaEnPalabras.toLowerCase().contains(textoBuscado.toLowerCase());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // Retorna verdadero si el texto buscado está en el título o en la fecha en palabras o en fecha normal (ejem.11/11/2020)
                        return tituloTexto || fechaTexto || fechaString;
                    })
                    .collect(Collectors.toList());
            ListaNotas.clear();
            ListaNotas.addAll(ListaFiltrada);

        }
        notifyDataSetChanged();
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
