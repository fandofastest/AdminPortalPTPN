package com.example.adminportalptpn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolders> {

    List<Berita> list = new ArrayList<>();

    private Context context;
    private FragmentManager fm;
      boolean online;

    public BeritaAdapter(List<Berita> list,Context context,FragmentManager fm,boolean online) {
        this.list = list;
        this.context=context;
        this.fm=fm;
        this.online=online;
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
        Berita berita=list.get(position);

         holder.judul.setText(berita.getJudul());
         holder.des.setText(berita.getIsi());
         holder.tgl.setText(berita.getTanggal());

         holder.action.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

            Dialog.bottomDialogPlaylist(context,berita,fm,online);

             }
         });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        ImageView foto ;
        TextView judul,des,tgl;
        ImageButton action;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            foto=itemView.findViewById(R.id.foto);
            judul=itemView.findViewById(R.id.judul);
            des=itemView.findViewById(R.id.des);
            tgl=itemView.findViewById(R.id.tgl);
            action=itemView.findViewById(R.id.action);




        }
    }
}
