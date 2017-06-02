package com.example.nabella.moviestation.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.entities.Jadwal;
import com.example.nabella.moviestation.seats.SeatsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nabella on 5/28/2017.
 */

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.MyViewHolder> {
    public String tn;
    private Context mContext;
    private List<Jadwal> jadwalList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public Button overflow;

    public MyViewHolder(View view) {
        super(view);

        overflow = (Button) view.findViewById(R.id.btn_jam);
    }
}


    public JadwalAdapter(Context mContext, List<Jadwal> jamlist) {
        this.mContext = mContext;
        this.jadwalList = jamlist;
    }


    public JadwalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_jadwal, parent, false);


        return new JadwalAdapter.MyViewHolder(itemView);
    }

    public void getTime(){
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        tn = format.format(curDate);
    }

    public void onBindViewHolder(final JadwalAdapter.MyViewHolder holder, int position) {
        final Jadwal jadwal = jadwalList.get(position);
        String jam = jadwal.getJam();
        getTime();

        String jamTayang = jam.replaceAll("[^\\d.]+", "");
        String timeNow = tn.replaceAll("[^\\d.]+", "");

        int jamnya = Integer.parseInt(jamTayang);
        int jsekarang = Integer.parseInt(timeNow);
        int jtayang = jamnya - 3000;
        Log.d("jamint",jtayang+" / "+ jsekarang);
        if (jtayang < jsekarang){
            holder.overflow.setEnabled(false);
        }else {
            holder.overflow.setEnabled(true);
        }
        holder.overflow.setText(jam);
        // loading album cover using Glide library

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, SeatsActivity.class);
                i.putExtra("id_jadwal", jadwal.getId_jadwal());
                i.putExtra("type_theater", jadwal.getType_theater());
                i.putExtra("kuota", jadwal.getKuota());
                i.putExtra("harga", jadwal.getHarga());
                i.putExtra("jam", jadwal.getJam());
                i.putExtra("id_movie", jadwal.getId_movie());
                i.putExtra("id_bioskop", jadwal.getId_bioskop());
                mContext.startActivity(i);
                Log.d("idmv", jadwal.getId_movie());
                /*Toast.makeText(mContext, jadwal.getJam().toString(), Toast.LENGTH_LONG).show();*/
            }
        });
    }


    public int getItemCount() {
        return jadwalList.size();
    }
}
