package com.example.proyectodeejemplo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.ViewHolder> {
    private final Context context;
    private final List<Integer> imageResources;
    private final OnImageClickListener listener;

    public interface OnImageClickListener {
        void onImageClick(int imageResource);
    }

    public GalleryImageAdapter(Context context, List<Integer> imageResources, OnImageClickListener listener) {
        this.context = context;
        this.imageResources = imageResources;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int imageResource = imageResources.get(position);
        holder.imageView.setImageResource(imageResource);
        holder.imageView.setOnClickListener(v -> listener.onImageClick(imageResource));
    }

    @Override
    public int getItemCount() {
        return imageResources.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gallery_image);
        }
    }
}
