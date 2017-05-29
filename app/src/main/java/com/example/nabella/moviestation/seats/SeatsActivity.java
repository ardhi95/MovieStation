package com.example.nabella.moviestation.seats;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nabella.moviestation.BaseFunct;
import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.activity.ticket.PaymentActivity;

import java.util.ArrayList;

import by.anatoldeveloper.hallscheme.hall.HallScheme;
import by.anatoldeveloper.hallscheme.hall.ScenePosition;
import by.anatoldeveloper.hallscheme.hall.Seat;
import by.anatoldeveloper.hallscheme.hall.SeatListener;
import by.anatoldeveloper.hallscheme.view.ZoomableImageView;


public class SeatsActivity extends BaseFunct {
    boolean doubleTap = true;
    ArrayList<String> listKursi = new ArrayList<>(0);
    ArrayList<String> listtmpatduduk = new ArrayList<>(0);
    int jmlh,total,harga,saldo, biayaLayanan, subtotal;
    Button zoomButton;
    TextView txtSeat,txtSaldo,txtKet;
    String idj, typ, kuotaSeat, hrg, saldocust, idcust, nmFlm, jmFilm, tglFilm;
    String kursi = "";
    String cs, idMovie, jam;
    String[] ary = kursi.split(",");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats);
        final ZoomableImageView imageView = (ZoomableImageView) findViewById(R.id.zoomable_image);
        zoomButton = (Button) findViewById(R.id.btn_proses);
        super.loadDataUsersLogin();

        Intent i = getIntent();
        hrg = i.getStringExtra("harga");
        idMovie = i.getStringExtra("id_movie");
        idj = i.getStringExtra("id_jadwal");
        typ = i.getStringExtra("type_theater");
        kuotaSeat = i.getStringExtra("kuota");
        jam = i.getStringExtra("jam");

        harga = Integer.parseInt(hrg);
        saldocust = super.customer.getSaldo();
        saldo = Integer.parseInt(saldocust);
        idcust = super.customer.getId_customer();
        Log.d("mboh", typ+"/"+kuotaSeat);

        txtSeat = (TextView)findViewById(R.id.txt_seat);
        txtSaldo = (TextView)findViewById(R.id.txt_saldo);
        txtKet = (TextView)findViewById(R.id.txt_ket);
        txtSaldo.setText("Rp. "+String.valueOf(saldo));
        zoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SeatsActivity.this, PaymentActivity.class);
                /*i.putExtra("nama", idj.toString());
                i.putExtra("jam", idcust.toString());
                i.putExtra("tanggal", String.valueOf(listKursi));*/
                //i.putExtra("poster", String.valueOf(listtmpatduduk));
                Log.d("intennya", String.valueOf(biayaLayanan)+"/"+String.valueOf(total));
                i.putExtra("jmlh", String.valueOf(jmlh));
                i.putExtra("subtotal", String.valueOf(subtotal));
                i.putExtra("biayalayanan", String.valueOf(biayaLayanan));
                i.putExtra("total", String.valueOf(total));
                i.putExtra("id_movie", idMovie);
                i.putExtra("jam", jam);
                startActivity(i);
                Toast.makeText(SeatsActivity.this, "Proses "+String.valueOf(jmlh)+" Kursi", Toast.LENGTH_LONG).show();
            }
        });
        pilihType();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void jmlhbayar(){
        jmlh = listKursi.size();
        subtotal = jmlh * harga;
        double sepuluh = 0.1;
        biayaLayanan = (int) (subtotal * sepuluh);
        Log.d("layanannyaa", String.valueOf(biayaLayanan));
        total = (subtotal + biayaLayanan);
        txtSeat.setText("Rp. "+String.valueOf(total));

        if (total <= saldo){
            zoomButton.setEnabled(true);
            txtSeat.setTextColor(getResources().getColor(R.color.uang));
            txtKet.setText(String.valueOf(jmlh));
        }else {
            txtSeat.setTextColor(getResources().getColor(R.color.colorPrimary));
            Toast.makeText(this,"Anda tidak dapat memproses karena saldo kurang",Toast.LENGTH_SHORT).show();
            zoomButton.setEnabled(false);
        }
    }
    public void pilihType(){
        final ZoomableImageView imageView = (ZoomableImageView) findViewById(R.id.zoomable_image);
        String typeKursi = typ;

        switch (typeKursi){
            case "gold" :
                final HallScheme gold = new HallScheme(imageView, goldSeats(), this);
                gold.setScenePosition(ScenePosition.NORTH);
                gold.setSeatListener(new SeatListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void selectSeat(int id) {
                        listtmpatduduk.add(String.valueOf(cs));
                        listKursi.add(String.valueOf(id));
                        jmlhbayar();

                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void unSelectSeat(int id) {
                        listtmpatduduk.remove(String.valueOf(cs));
                        listKursi.remove(String.valueOf(id));
                        jmlhbayar();
                    }

                });
                break;
            case "premier" :
                final HallScheme premier = new HallScheme(imageView, premierSeats(), this);
                premier.setScenePosition(ScenePosition.NORTH);
                premier.setSeatListener(new SeatListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void selectSeat(int id) {
                        listtmpatduduk.add(String.valueOf(cs));
                        listKursi.add(String.valueOf(id));
                        jmlhbayar();

                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void unSelectSeat(int id) {
                        listtmpatduduk.remove(String.valueOf(cs));
                        listKursi.remove(String.valueOf(id));
                        jmlhbayar();
                    }

                });
                break;
            case "reguler":
                final HallScheme reguler = new HallScheme(imageView, regulerSeats(), this);
                reguler.setScenePosition(ScenePosition.NORTH);
                reguler.setSeatListener(new SeatListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void selectSeat(int id) {
                        listtmpatduduk.add(String.valueOf(cs));
                        listKursi.add(String.valueOf(id));
                        jmlhbayar();
                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void unSelectSeat(int id) {
                        listtmpatduduk.remove(String.valueOf(cs));
                        listKursi.remove(String.valueOf(id));
                        jmlhbayar();
                    }

                });
                break;
        }
    }

    public Seat[][] goldSeats() {
        Seat seats[][] = new Seat[9][11];
        int k = 0;
        for (int i = 0; i < 9; i++)
            for(int j = 0; j < 11; j++) {
                SeatExample seat = new SeatExample();
                seat.id = ++k;
                int an = i +65;
                cs = String.valueOf((char)an + (j + " "));
                Log.d("csnya",cs);

                seat.selectedSeatMarker = String.valueOf(cs);
                seat.status = HallScheme.SeatStatus.FREE;
                seat.color = Color.GREEN;

                if (kuotaSeat.equals("depan")){
                    if (i > 4 && i <= 9){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }else if (kuotaSeat.equals("tengah")){
                    if ((i >= 0 && i < 3)||(i > 5 && i <= 9)){
                        seat.status = HallScheme.SeatStatus.BUSY;

                    }
                }else if (kuotaSeat.equals("belakang")){
                    if (i >= 0 && i <= 5){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }
                for (int p = 0; p < ary.length ; p++){
                    if (String.valueOf(seat.id).equals(ary[p])){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }
                if ((j >= 2 && j <= 2)||(j >= 5 && j <= 5) || (j >= 8 && j <= 8)) {
                    seat.status = HallScheme.SeatStatus.EMPTY;
                }
                seats[i][j] = seat;
            }
        return seats;
    }
    public Seat[][] premierSeats() {
        Seat seats[][] = new Seat[8][22];
        int k = 0;
        for (int i = 0; i < 8; i++)
            for(int j = 0; j < 22; j++) {
                SeatExample seat = new SeatExample();
                seat.id = ++k;
                int an = i +65;
                cs = String.valueOf((char)an + (j + " "));
                Log.d("csnya",cs);

                seat.selectedSeatMarker = String.valueOf(cs);
                seat.status = HallScheme.SeatStatus.FREE;
                seat.color = Color.GREEN;

                if (kuotaSeat.equals("depan")){
                    if (i > 2 && i <= 8){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }else if (kuotaSeat.equals("tengah")){
                    if ((i >= 0 && i < 3)||(i > 4 && i <= 9)){
                        seat.status = HallScheme.SeatStatus.BUSY;

                    }
                }else if (kuotaSeat.equals("belakang")){
                    if (i >= 0 && i <= 4){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }
                for (int p = 0; p < ary.length ; p++){
                    if (String.valueOf(seat.id).equals(ary[p])){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }
                if ((j >= 6 && j <= 7 && i < 7)||(j >= 14 && j <= 15 && i < 7)) {
                    seat.status = HallScheme.SeatStatus.EMPTY;
                }
                seats[i][j] = seat;
            }
        return seats;
    }
    public Seat[][] regulerSeats() {
        Seat seats[][] = new Seat[12][20];
        int k = 0;
        for (int i = 0; i < 12; i++)
            for(int j = 0; j < 20; j++) {
                SeatExample seat = new SeatExample();
                seat.id = ++k;
                int an = i +65;
                cs = String.valueOf((char)an + (j + " "));
                Log.d("csnya",cs);

                seat.selectedSeatMarker = String.valueOf(cs);
                seat.status = HallScheme.SeatStatus.FREE;
                seat.color = Color.GREEN;

                if (kuotaSeat.equals("depan")){
                    if (i > 3 && i <= 12){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }else if (kuotaSeat.equals("tengah")){
                    if ((i >= 0 && i < 4)||(i > 7 && i <= 12)){
                        seat.status = HallScheme.SeatStatus.BUSY;

                    }
                }else if (kuotaSeat.equals("belakang")){
                    if (i >= 0 && i <= 7){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }
                for (int p = 0; p < ary.length ; p++){
                    if (String.valueOf(seat.id).equals(ary[p])){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }
                if ((j >= 6 && j <= 7 && i < 11)||(j >= 12 && j <= 13 && i < 11)) {
                    seat.status = HallScheme.SeatStatus.EMPTY;
                }
                seats[i][j] = seat;
            }
        return seats;
    }
}
