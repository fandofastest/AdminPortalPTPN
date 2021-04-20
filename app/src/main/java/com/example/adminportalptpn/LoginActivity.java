package com.example.adminportalptpn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.buttonlogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login(username.getText().toString(),password.getText().toString());

            }
        });



        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("sp",MODE_PRIVATE);
        boolean login = sharedPreferences.getBoolean("login",false);


        if (login){
            Config.ID=sharedPreferences.getString("ID","");
            Config.name=sharedPreferences.getString("name","");
            Config.email=sharedPreferences.getString("email","");
            Config.nohp=sharedPreferences.getString("nohp","");
            Config.token=sharedPreferences.getString("token","");

            Intent intent= new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }




    }

    public void login(String email,String password){
        String postUrl = Config.APILOGIN;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("email", email);
            postData.put("password", password);

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
                            JSONObject jsonUser = response.getJSONObject("user");
                            String token= response.getString("_token");
                            Config.ID=jsonUser.getString("id");
                            Config.name=jsonUser.getString("name");
                            Config.email=jsonUser.getString("email");
                            Config.nohp=jsonUser.getString("hp");
                            Config.token=token;
                            Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();



                            SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("sp",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("login",true);
                            editor.putString("ID",Config.ID);
                            editor.putString("name",Config.name);
                            editor.putString("email",Config.email);
                            editor.putString("nohp",Config.nohp);
                            editor.putString("token",Config.token);
                            editor.apply();




                            Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }


}