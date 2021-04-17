package com.example.adminportalptpn;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Dialog {
    public static void bottomDialogPlaylist(Context context,Berita berita,FragmentManager fm) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(R.layout.bottomsheet);

        Button lihat = dialog.findViewById(R.id.view);
        Button edit = dialog.findViewById(R.id.edit);
        Button hapus = dialog.findViewById(R.id.hapus);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft =fm.beginTransaction();
                ft.replace(R.id.frame, LihatBeritaFragment.newInstance(berita));
                ft.addToBackStack(null);
                ft.commit();

                dialog.dismiss();



            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft =fm.beginTransaction();
                ft.replace(R.id.frame, NewPostFragment.editInstance(true,berita));
                ft.addToBackStack(null);
                ft.commit();

                dialog.dismiss();


            }
        });



        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(berita.getId(),context);
                dialog.dismiss();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame, MainFragment.newInstance("data1","data2"));
                ft.addToBackStack(null);
                ft.commit();



            }
        });




        dialog.show();
    }

    public static void delete(String id,Context context){
        String url = Config.DELETE+id;
        RequestQueue requestQueue = Volley.newRequestQueue(context);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String kode =response.getString("kode");
                            if (kode.equals("200")){

                                Toast.makeText(context, "Berhasil dihapus", Toast.LENGTH_SHORT).show();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        requestQueue.add(jsonObjectRequest);

    }


}
