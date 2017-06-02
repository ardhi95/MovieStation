package com.example.nabella.moviestation.activity.ticket;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nabella.moviestation.BaseFunct;
import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.entities.Film;
import com.example.nabella.moviestation.entities.Jadwal;
import com.example.nabella.moviestation.lib.FormData;
import com.example.nabella.moviestation.lib.InternetTask;
import com.example.nabella.moviestation.lib.OnInternetTaskFinishedListener;
import com.example.nabella.moviestation.seats.SeatsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PaymentActivity extends BaseFunct {
    TextView txtNama, txtJam, txtTanggal, txtPjml, txtSubtotal, txtLayanan, txtJumlah;
    String  pJam, pidb,pidj, pJml, pSubtotal, pLayanan, pJumlah,txtIdmovie,tgl, idkursi;
    ImageView imgPoster;
    Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        txtNama = (TextView)findViewById(R.id.txt_nmFilm);
        txtJam = (TextView)findViewById(R.id.txt_jamp);
        txtPjml = (TextView)findViewById(R.id.txt_pjml);
        txtSubtotal = (TextView)findViewById(R.id.txt_subtotal);
        txtLayanan = (TextView)findViewById(R.id.txt_layanan);
        txtJumlah = (TextView)findViewById(R.id.txt_jumlah);
        txtTanggal =(TextView)findViewById(R.id.txt_tanggalp);
        imgPoster = (ImageView)findViewById(R.id.img_poster);
        pay = (Button) findViewById(R.id.btn_pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentActivity.this,"Test Click",Toast.LENGTH_SHORT).show();
                addTicket();
            }
        });
        getDate();
        getFromIntent();
        getMovie();
        setTV();
    }
    public void getDate(){
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        tgl = format.format(curDate);
        Log.d("tanggalnyaa", tgl);
        txtTanggal.setText(tgl);
    }
    private void setTV() {

        //txtJam.setText(pJam);
        //txtTanggal.setText(pTanggal);
        txtJam.setText(pJam);
        txtPjml.setText(pJml);
        txtSubtotal.setText("Rp. "+pSubtotal);
        txtLayanan.setText("Rp. "+pLayanan);
        txtJumlah.setText("Rp. "+pJumlah);

    }

    private void getFromIntent() {
        Intent i = getIntent();
        pJam = i.getStringExtra("jam");
        txtIdmovie = i.getStringExtra("id_movie");
        pJml = i.getStringExtra("jmlh");
        pSubtotal = i.getStringExtra("subtotal");
        pLayanan = i.getStringExtra("biayalayanan");
        pJumlah = i.getStringExtra("total");
        pidj = i.getStringExtra("id_jadwal");
        pidb = i.getStringExtra("id_bioskop");
        String hlng = i.getStringExtra("id_kursi").replace("[","");
        idkursi = hlng.replace("]","");
    }

    public void getMovie(){
        FormData data = new FormData();
        data.add("method", "get_movie");
        data.add("id_movie", txtIdmovie);
        InternetTask uploadTask = new InternetTask("Movie", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject jobj = jsonArray.getJSONObject(0);
                        String jobNama = (String) jsonArray.getJSONObject(0).get("Title");
                        String jobPoster = (String) jsonArray.getJSONObject(0).get("Poster");
                        txtNama.setText(jobNama);
                        Glide.with(getApplication()).load(jobPoster).into(imgPoster);
                        //pPoster = (String) jobj.get("Poster");
                        //Toast.makeText(getContext(),jsonArray.toString(),Toast.LENGTH_SHORT).show();
                    }else{
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
            }
        });
        uploadTask.execute();
    }
    public void addTicket(){
        super.loadDataUsersLogin();
        FormData data = new FormData();
        data.add("method", "addTicket");
        data.add("id_jadwal", pidj);
        data.add("id_customer", super.customer.getId_customer());
        data.add("id_bioskop", pidb);
        data.add("id_kursi", idkursi);
        data.add("kursi", pJml);
        data.add("tgl_beli", tgl);
        data.add("jml_uang", pJumlah);
        Log.d("datakirimp", String.valueOf(data));
        InternetTask uploadTask = new InternetTask("Ticket", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        Toast.makeText(PaymentActivity.this,"Berhasil beli",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(PaymentActivity.this,"Gagal",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(PaymentActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
            }
        });
        uploadTask.execute();
    }
}
