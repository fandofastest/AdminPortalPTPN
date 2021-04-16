package com.example.adminportalptpn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolders> {

    List<Berita> list = new ArrayList<>();

    public BeritaAdapter(List<Berita> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_berita, parent, false);

        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
        }
    }
}
