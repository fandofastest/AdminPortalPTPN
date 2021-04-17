package com.example.adminportalptpn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LihatBeritaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LihatBeritaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Berita berita;

    private TextView judul,kategori,isi,tanggal;
    private ImageView foto;

    public LihatBeritaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment LihatBeritaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LihatBeritaFragment newInstance(Berita berita) {
        LihatBeritaFragment fragment = new LihatBeritaFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, berita);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            berita = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lihat_berita, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        judul=view.findViewById(R.id.judul);
        isi=view.findViewById(R.id.isi);
        kategori=view.findViewById(R.id.kategori);
        tanggal=view.findViewById(R.id.tgl);

        judul.setText(berita.getJudul());
        isi.setText(berita.getIsi());
        kategori.setText(berita.getKategori());
        tanggal.setText(berita.getTanggal());




    }
}