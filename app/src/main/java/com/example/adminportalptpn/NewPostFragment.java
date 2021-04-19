package com.example.adminportalptpn;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean edit =false;
    private Berita berita;
    private Context context;
    EditText title,isi;
    Spinner kategori;
    String katselected;
    Button simpan;
    boolean online =true;
    public NewPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment NewPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPostFragment newInstance() {
        NewPostFragment fragment = new NewPostFragment();

        return fragment;
    }

    public static NewPostFragment editInstance(boolean edit,Berita berita,boolean online) {
        NewPostFragment fragment = new NewPostFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, edit);
        args.putParcelable(ARG_PARAM2, berita);
        args.putBoolean("on",online);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            edit = getArguments().getBoolean(ARG_PARAM1);
            berita = getArguments().getParcelable(ARG_PARAM2);
            online=getArguments().getBoolean("on");
        }
        context=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title=view.findViewById(R.id.judul);
        isi=view.findViewById(R.id.isi);
        kategori=view.findViewById(R.id.kategori);
        simpan=view.findViewById(R.id.simpan);


        if (edit){


            isi.setText(berita.getIsi());
            title.setText(berita.getJudul());




            if (berita.getKategori().equals("Music")){
                kategori.setSelection(0);
            }

            else if (berita.getKategori().equals("Sports")){
                kategori.setSelection(1);
            }
            else if (berita.getKategori().equals("Movie")){
                kategori.setSelection(2);
            }





        }



        kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Log.e("onItemSelected", "onItemSelected: "+position );

                if (position==0){
                    katselected="Music";
                }
                else if (position==1){
                    katselected="Sports";
                }
                else if (position==2){
                    katselected="Movie";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Berita berita = new Berita();
                berita.setJudul(title.getText().toString());
                berita.setIsi(isi.getText().toString());
                berita.setKategori(katselected);
                berita.setTanggal(tgl);*/
                TimeZone tz = TimeZone.getTimeZone("UTC");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"); // Quoted "Z" to indicate UTC, no timezone offset
                df.setTimeZone(tz);
                String nowAsISO = df.format(new Date());

                Log.e("tgl", nowAsISO );
                //2021-04-16 03:35:33

                if (edit){
                    if ( online){
                    edit(title.getText().toString(),nowAsISO,katselected,isi.getText().toString(),"foto",berita.getId());

                    }

                    else {
                      editoffline(berita);
                    }


                }

                else {
                    create(title.getText().toString(),nowAsISO,katselected,isi.getText().toString(),"foto");
                }
            }
        });









    }

        public void editoffline(Berita berita){

        RealmHelper realmHelper = new RealmHelper(context);
        realmHelper.update(berita);

}

    public void edit(String judul,String tanggal,String kat,String isi,String gambar,String id){
        String postUrl = Config.EDIT+id;
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JSONObject postData = new JSONObject();
        try {
            postData.put("judul", judul);
            postData.put("tanggal", tanggal);
            postData.put("kategori", kat);
            postData.put("isi", isi);
            postData.put("gambar", gambar);
            postData.put("_token", Config.token);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("edit", response.toString());

                try {
                    String kode =response.getString("kode");

                    if (kode.equals("200")){

                        Toast.makeText(context, "Edit Berhasil", Toast.LENGTH_SHORT).show();

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frame, ListOnLineFragment.newInstance("data1","data2"));
                        ft.addToBackStack(null);
                        ft.commit();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Gagal Create", Toast.LENGTH_SHORT).show();
                error.printStackTrace();




            }
        });

        requestQueue.add(jsonObjectRequest);

    }


    public void create(String judul,String tanggal,String kat,String isi,String gambar){
        String postUrl = Config.CREATE;
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JSONObject postData = new JSONObject();
        try {
            postData.put("judul", judul);
            postData.put("tanggal", tanggal);
            postData.put("kategori", kat);
            postData.put("isi", isi);
            postData.put("gambar", gambar);
            postData.put("_token", Config.token);




        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("testlogin", response.toString());

                try {
                    String kode =response.getString("kode");

                    if (kode.equals("200")){

                        Toast.makeText(context, "Create Berhasil", Toast.LENGTH_SHORT).show();

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frame, ListOnLineFragment.newInstance("data1","data2"));
                        ft.addToBackStack(null);
                        ft.commit();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Gagal Create", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

                Berita berita = new Berita();
                berita.setJudul(judul);
                berita.setIsi(isi);
                berita.setKategori(kat);
                berita.setTanggal(tanggal);

                RealmHelper realmHelper = new RealmHelper(context);
                realmHelper.save(berita);


            }
        });

        requestQueue.add(jsonObjectRequest);

    }


}