package com.example.nabella.moviestation.activity.ticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.entities.Film;
import com.example.nabella.moviestation.lib.FormData;
import com.example.nabella.moviestation.lib.InternetTask;
import com.example.nabella.moviestation.lib.OnInternetTaskFinishedListener;
import com.example.nabella.moviestation.tab_saldo.Detail_HSActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieB_Activity extends AppCompatActivity {
    String txtidB;
    TextView txtNamaB;
    TextView txtalamat;
    ImageView imgPict;
    private ArrayList<Film> listFilm;
    public ArrayAdapter adapter;
    public ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_b);


        txtNamaB = (TextView) findViewById(R.id.txt_nama);
        txtalamat = (TextView) findViewById(R.id.txt_alamat);
        imgPict = (ImageView) findViewById(R.id.img_pict);
        showData();

        lv = (ListView)findViewById(R.id.lst_movie);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film selecetedFl = listFilm.get(position);
                Intent i = new Intent(MovieB_Activity.this, Detail_HSActivity.class);

                startActivity(i);
            }
        });
        this.listFilm = new ArrayList<>();
        getMovie();
    }

    private void showData() {
        Intent i = getIntent();
        txtidB = i.getStringExtra("id_bioskop");
        String  NamaB = i.getStringExtra("nama_bioskop");
        String AlamatB = i.getStringExtra("alamat");
        String PictB = i.getStringExtra("picture_url");
        txtNamaB.setText(NamaB);
        txtalamat.setText(AlamatB);
        String urlProfileImg = PictB;
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgPict);
    }

    public void getMovie(){
        FormData data = new FormData();
        data.add("method", "getMovieTic");
        data.add("id_bioskop", txtidB.toString());
        InternetTask uploadTask = new InternetTask("Movie", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if (jsonArray.length() > 0){
                            ArrayList<String> listpost = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length();i++){
                                Film film = new Film(jsonArray.getJSONObject(i));
                                listFilm.add(new Film(jsonArray.getJSONObject(i)));
                                listpost.add(film.getNama_film()+"\n"+film.getHarga());
                            }
                            adapter = new ArrayAdapter<String>(MovieB_Activity.this, android.R.layout.simple_list_item_1, listpost);
                            lv.setAdapter(adapter);
                        }
                        //Toast.makeText(getContext(),jsonArray.toString(),Toast.LENGTH_SHORT).show();
                    }else{
//                            Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //                       Snackbar.make(clContent, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
                //                   Snackbar.make(clContent, internetTask.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
        uploadTask.execute();
    }
}
