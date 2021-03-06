package com.example.adminportalptpn;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;
    Context contex;
    RealmResults<Berita> results;
    public RealmHelper(Context contex) {
        Realm.init(contex);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);
        this.contex = contex;
    }


    // untuk menyimpan data
    public void save(final Berita berita){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(Berita.class).max("no");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    berita.setNo(nextId);
                    Berita model = realm.copyToRealm(berita);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });



    }

    public void update(int no,String judul,String kategori,String isi) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Berita model = realm.where(Berita.class)
                        .equalTo("no", no)
                        .findFirst();

                model.setJudul(judul);
                model.setKategori(kategori);
                model.setIsi(isi);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e("fff", "sukses" );

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
                Log.e("fff", error.getMessage() );

            }
        });
    }

    // untuk memanggil semua data
    public List<Berita> getLocalberita(){
        RealmResults<Berita> results = realm.where(Berita.class).findAll();
        return results;
    }


    public void deleteberita(Integer no) {
        final RealmResults<Berita> model = realm.where(Berita.class).equalTo("no", no).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm(0);


            }
        });
    }









}
