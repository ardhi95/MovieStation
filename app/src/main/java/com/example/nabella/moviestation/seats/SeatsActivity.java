package com.example.nabella.moviestation.seats;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nabella.moviestation.BaseFunct;
import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.activity.ticket.PaymentActivity;
import com.example.nabella.moviestation.entities.Film;
import com.example.nabella.moviestation.lib.FormData;
import com.example.nabella.moviestation.lib.InternetTask;
import com.example.nabella.moviestation.lib.OnInternetTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import by.anatoldeveloper.hallscheme.hall.HallScheme;
import by.anatoldeveloper.hallscheme.hall.ScenePosition;
import by.anatoldeveloper.hallscheme.hall.Seat;
import by.anatoldeveloper.hallscheme.hall.SeatListener;
import by.anatoldeveloper.hallscheme.view.ZoomableImageView;


public class SeatsActivity extends BaseFunct {

    ArrayList<String> listKursi = new ArrayList<>();
    ArrayList<String> kursipesan = new ArrayList<>();
    public ArrayAdapter<String> adapter;
    int jmlh,total,harga,saldo, biayaLayanan, subtotal;
    Button zoomButton;
    TextView txtSeat,txtSaldo,txtKet;
    String idj, typ, kuotaSeat, hrg, saldocust, idcust, tanggal;
    String kursi = "";
    String cs, idMovie, jam, idb;
    String[] idkursiArray;
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
        idb = i.getStringExtra("id_bioskop");

        //kursiChck = new int[Integer.parseInt(String.valueOf(listtmpatduduk))];


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
                Log.d("intennya", String.valueOf(biayaLayanan)+"/"+String.valueOf(total));
                i.putExtra("jmlh", String.valueOf(jmlh));
                i.putExtra("subtotal", String.valueOf(subtotal));
                i.putExtra("biayalayanan", String.valueOf(biayaLayanan));
                i.putExtra("total", String.valueOf(total));
                i.putExtra("id_movie", idMovie);
                i.putExtra("jam", jam);
                i.putExtra("id_jadwal", idj);
                i.putExtra("id_bioskop", idb);
                i.putExtra("id_kursi", String.valueOf(listKursi));
                startActivity(i);
                Toast.makeText(SeatsActivity.this, "Proses "+String.valueOf(jmlh)+" Kursi", Toast.LENGTH_LONG).show();
            }
        });
        pilihType();
        getKursi();
// cobaen maneh, null mas datanya
        // sek
        // cobaen, sek null yo
        //yang ini nggk masuk mas
        // cobaen, masih kosong mas, apa mungkin script ku salah struktur?
        // coba deloken log e, sing di proses kursipesan-2 disik
        // nah metu ngunu,brarti buth refresh buat dpt id dr kursipesan?
        // koyoke iku value sebelumnya
        Log.d("kursipesan-2",String.valueOf(KursiPesanSingleton.getInstance().getData()));
        Intent mServiceIntent = new Intent(this, RSSPullService.class);
        //mServiceIntent.setData(Uri.parse(dataUrl));
        this.startService(mServiceIntent);

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
                        listKursi.add(String.valueOf(id));
                        jmlhbayar();

                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void unSelectSeat(int id) {
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
                        listKursi.add(String.valueOf(id));
                        jmlhbayar();

                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void unSelectSeat(int id) {
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
                        listKursi.add(String.valueOf(id));
                        jmlhbayar();
                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void unSelectSeat(int id) {
                        listKursi.remove(String.valueOf(id));
                        jmlhbayar();
                    }

                });
                break;
        }
    }

    public Seat[][] goldSeats() {
        String kur = String.valueOf(KursiPesanSingleton.getInstance().getData());
        String skur = kur.replace("[", "").replace("]", "").replaceAll(" ","");
        String[] arrkur = skur.split(",");
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
                for (int c = 0; c < arrkur.length; c++){
                    //Log.d("kursipesan-3", String.valueOf(arrkur[c]));
                    if (String.valueOf(seat.id).equals(arrkur[c])){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }
                seats[i][j] = seat;
            }
        return seats;
    }
    public Seat[][] premierSeats() {
        String kur = String.valueOf(KursiPesanSingleton.getInstance().getData());
        String skur = kur.replace("[", "").replace("]", "").replaceAll(" ","");
        String[] arrkur = skur.split(",");
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
                for (int c = 0; c < arrkur.length; c++){
                    //Log.d("kursipesan-3", String.valueOf(arrkur[c]));
                    if (String.valueOf(seat.id).equals(arrkur[c])){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }
                seats[i][j] = seat;
            }
        return seats;
    }
    public Seat[][] regulerSeats() {
        String kur = String.valueOf(KursiPesanSingleton.getInstance().getData());
        String skur = kur.replace("[", "").replace("]", "").replaceAll(" ","");
        String[] arrkur = skur.split(",");
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
                for (int c = 0; c < arrkur.length; c++){
                    //Log.d("kursipesan-3", String.valueOf(arrkur[c]));
                    if (String.valueOf(seat.id).equals(arrkur[c])){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }
                seats[i][j] = seat;
            }
        return seats;
    }

    public void getDate(){
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        tanggal = format.format(curDate);
    }

    // digoleki sing ono lenght e
    public static ArrayList<String> ParseIdKursi(ArrayList<String> input){
        ArrayList<String> result = new ArrayList<String>();
        for (int i=0;i<input.size();i++){
            String data = input.get(i);
            // iki ngecek, lak data ne null, continue, maksute proses data selanjutnya, cobaen disik
            if (data == null) {
                continue;
            }

            String[] parsed = data.split(",");
            // masalahe ndek kene.
            for (int j=0;j<parsed.length;j++){
                result.add(parsed[j]);
            }
        }
        Log.d("cekKursi", String.valueOf(result));
        return result;
    }

    /*public static ArrayList<String> ParseIdKursi(ArrayList<String> input){
        ArrayList<String> result = new ArrayList<String>();
        for (int i=0;i < input.size();i++){
            //String data = input.get(i);
            result.add(input.get(i));
            //String[] parsed = data.split(",");
            //result.add(String.valueOf(parsed));
            //System.out.println(data);
        }
        String haha = String.valueOf(result).replace("[", "").replace("]", "").replaceAll(" ","");
        String[] hihi = haha.split(",");
        for (int j=0;j<hihi.length;j++){
            Log.d("parsekursi" , String.valueOf(hihi[j]));
        }
        return result;
    }*/

    // ndi sing nyeluk getKursi?
    public void getKursi(){
        getDate();
        FormData data = new FormData();
        data.add("method", "checkTiket");
        data.add("id_jadwal", idj);
        data.add("tgl_beli", tanggal);
        Log.d("testkursi", tanggal);

        InternetTask uploadTask = new InternetTask("Ticket", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        ArrayList<String> kursiList = new ArrayList<String>();
                        for (int i = 0; i < jsonArray.length();i++){
                            //datanya dari sini kan mas?
                            // coba di log, metu gak datane
                            //keluar mas, itu di debug
                            kursiList.add((String) jsonArray.getJSONObject(i).get("id_kursi"));
                            //String idkusri = (String) jsonArray.getJSONObject(i).get("id_kursi");
                            //idkursiArray = idkusri.split(",");

                            // sek tak mikir, iya mas, itu resultnya apa nggk diluar try?
                        }
                        // terus iki lapo?
                        //ini ngirim datanya kan?ke parseidkursi

                        KursiPesanSingleton.getInstance().setData(ParseIdKursi(kursiList));
                        // sing iki ono isine?ada
                        Log.d("kursipesan", String.valueOf(KursiPesanSingleton.getInstance().getData()));
                        //terus, result e digawe opo?
                        //nggk tau mas
                        // maksutmu data tekan jsonobject, diparse, terus disimpen ndek kursipesan?nah iyaa mas, cobaen
                        //tetep mas
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
}


class KursiPesanSingleton {
    static KursiPesanSingleton ME;
    private ArrayList<String> data = new ArrayList<>();
    private KursiPesanSingleton(){}
    public static synchronized KursiPesanSingleton getInstance(){
        if (ME == null){
            ME = new KursiPesanSingleton();
        }
        return ME;
    }
    public void setData(ArrayList<String> input){
        this.data = input;
    }
    public ArrayList<String> getData() {
        return this.data;
    }
}