package com.example.nabella.moviestation.seats;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nabella.moviestation.R;

import java.util.ArrayList;

import by.anatoldeveloper.hallscheme.hall.HallScheme;
import by.anatoldeveloper.hallscheme.hall.ScenePosition;
import by.anatoldeveloper.hallscheme.hall.Seat;
import by.anatoldeveloper.hallscheme.hall.SeatListener;
import by.anatoldeveloper.hallscheme.view.ZoomableImageView;


public class SeatsActivity extends AppCompatActivity {
    boolean doubleTap = true;
    ArrayList<String> listKursi = new ArrayList<>(0);
    int jmlh,total;
    int harga = 20;
    int saldo = 100;
    Button zoomButton;
    TextView txtSeat,txtSaldo,txtKet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats);
        final ZoomableImageView imageView = (ZoomableImageView) findViewById(R.id.zoomable_image);
        zoomButton = (Button) findViewById(R.id.scheme_button);

        txtSeat = (TextView)findViewById(R.id.txt_seat);
        txtSaldo = (TextView)findViewById(R.id.txt_saldo);
        txtKet = (TextView)findViewById(R.id.txt_ket);

        zoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doubleTap = !doubleTap;
                imageView.setZoomByDoubleTap(doubleTap);
                if (doubleTap) {
                    zoomButton.setText(getString(R.string.double_tap_enabled));
                } else {
                    zoomButton.setText(getString(R.string.double_tap_disabled));
                }
            }
        });
        final HallScheme scheme = new HallScheme(imageView, basicScheme(), this);
        scheme.setScenePosition(ScenePosition.NORTH);
        //scheme.clickSchemeProgrammatically(3,3);
        scheme.setSeatListener(new SeatListener() {

            @Override
            public void selectSeat(int id) {
                listKursi.add(String.valueOf(id));
                jmlhbayar();

            }

            @Override
            public void unSelectSeat(int id) {
                listKursi.remove(String.valueOf(id));
                jmlhbayar();
            }

        });
        /*scheme.setMaxSelectedSeats(kursi);
        scheme.setMaxSeatsClickListener(new MaxSeatsClickListener() {
            @Override
            public void maxSeatsReached(int id) {
                listKursi.add(String.valueOf(id));
                jmlhbayar();
            }
        });*/
    }
    public void jmlhbayar(){
        jmlh = listKursi.size();
        total = jmlh;
        txtSeat.setText(String.valueOf(total));
        txtSaldo.setText(String.valueOf(saldo));
        if (total <= saldo){
            txtKet.setText("cukup");
            zoomButton.setVisibility(View.VISIBLE);
        }else {
            txtKet.setText("Gak cukup");
            Toast.makeText(this,"Anda tidak dapat memproses karena saldo kurang",Toast.LENGTH_SHORT).show();
            zoomButton.setVisibility(View.INVISIBLE);
        }
    }

    public Seat[][] basicScheme() {
        ArrayList<String> jmlhnya = new ArrayList<>();
        Seat seats[][] = new Seat[7][14];
        String kursi = "1,2,4,3,66,56";
        String[] seatArr = kursi.split(",");

        int k = 0;
        for (int i = 0; i < 7; i++)
            for(int j = 0; j < 14; j++) {
                SeatExample seat = new SeatExample();
                seat.id = ++k;
                seat.selectedSeatMarker = String.valueOf("");
                seat.status = HallScheme.SeatStatus.FREE;
                seat.color = Color.GREEN;
                /*if (j == 0 || j == 13) {
                    seat.marker = String.valueOf("0");
                    seat.status = HallScheme.SeatStatus.INFO;
                }*/
                if (j > 5 && j < 8) {
                    seat.status = HallScheme.SeatStatus.EMPTY;
                }
                /*if (((j >= 0 && j < 6) || (j > 7 && j <= 13)) && i < 2) {
                    seat.status = HallScheme.SeatStatus.FREE;
                    seat.color = Color.GREEN;
                }
                if (((j >= 0 && j < 6) || (j > 7 && j <= 13)) && i > 4) {
                    seat.status = HallScheme.SeatStatus.FREE;
                    seat.color = Color.GREEN;
                }*/
                /*for (int p = 0; p < seatArr.length ; p++){
                    if (String.valueOf(seat.id).equals(seatArr[p])){
                        seat.status = HallScheme.SeatStatus.BUSY;
                    }
                }*/
                seats[i][j] = seat;
            }
        return seats;
    }
}
