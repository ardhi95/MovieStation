package com.example.nabella.moviestation.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.activity.ticket.MovieB_Activity;
import com.example.nabella.moviestation.entities.Bioskop;
import com.example.nabella.moviestation.fragment.HomeFragment;

import java.util.List;

/**
 * Created by Nabella on 5/25/2017.
 */

public class BioskopAdapter extends RecyclerView.Adapter<BioskopAdapter.MyViewHolder> {

    private Context mContext;
    private List<Bioskop> bioskopList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, addrs;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            addrs = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    public BioskopAdapter(Context mContext, List<Bioskop> albumList) {
        this.mContext = mContext;
        this.bioskopList = albumList;
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_bioskop, parent, false);


        return new MyViewHolder(itemView);
    }



    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Bioskop bioskop = bioskopList.get(position);
        holder.title.setText(bioskop.getNama_bioskop());
        holder.addrs.setText(bioskop.getAlamat());

        // loading album cover using Glide library
        Glide.with(mContext).load(bioskop.getPicture_url()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, MovieB_Activity.class);
                i.putExtra("nama_bioskop", bioskop.getNama_bioskop().toString());
                i.putExtra("id_bioskop", bioskop.getId_bioskop().toString());
                i.putExtra("alamat", bioskop.getAlamat().toString());
                i.putExtra("picture_url", bioskop.getPicture_url().toString());
                mContext.startActivity(i);
                Toast.makeText(mContext, bioskop.getNama_bioskop().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public int getItemCount() {
        return bioskopList.size();
    }
}