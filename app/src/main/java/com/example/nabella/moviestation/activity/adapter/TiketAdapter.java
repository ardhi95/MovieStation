package com.example.nabella.moviestation.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.activity.ticket.MovieB_Activity;
import com.example.nabella.moviestation.entities.Bioskop;
import com.example.nabella.moviestation.entities.Tiket;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.List;

/**
 * Created by Nabella on 5/31/2017.
 */

public class TiketAdapter extends RecyclerView.Adapter<TiketAdapter.MyViewHolder> {

    private Context mContext;
    private List<Tiket> tiketList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, addrs;
        public ImageView thumbnail;
        public CardView crd;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            addrs = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            crd = (CardView) view.findViewById(R.id.card_view);
        }
    }


    public TiketAdapter(Context mContext, List<Tiket> listTiket) {
        this.mContext = mContext;
        this.tiketList = listTiket;
    }


    public TiketAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_tiket, parent, false);


        return new TiketAdapter.MyViewHolder(itemView);
    }



    public void onBindViewHolder(final TiketAdapter.MyViewHolder holder, int position) {
        final Tiket tiket = tiketList.get(position);
        holder.title.setText(tiket.getTgl_beli());
        holder.addrs.setText(tiket.getId_jadwal());

        // loading album cover using Glide library
        //Glide.with(mContext).load(bioskop.getPicture_url()).into(holder.thumbnail);
        String content = tiket.getId_pembelian().toString();
        QRCodeWriter write = new QRCodeWriter();
        try {
            int width = 75;
            int height = 75;
            BitMatrix bitMatrix = write.encode(content, BarcodeFormat.QR_CODE, width, height);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK: Color.WHITE);
                }
            }
            holder.thumbnail.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        holder.crd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, tiket.getId_pembelian().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public int getItemCount() {
        return tiketList.size();
    }
}
