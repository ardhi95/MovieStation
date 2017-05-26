package com.example.nabella.moviestation.activity.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.entities.Film;

import java.util.List;

/**
 * Created by Nabella on 5/26/2017.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder>{
    private Context mContext;
    private List<Film> filmList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail;
        public Button overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (Button) view.findViewById(R.id.btn_book);
        }
    }


    public FilmAdapter(Context mContext, List<Film> filmList) {
        this.mContext = mContext;
        this.filmList = filmList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_movie, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Film film = filmList.get(position);
        holder.title.setText(film.getNama_film());
        holder.count.setText("Rp. "+ film.getHarga());

        // loading album cover using Glide library
        Glide.with(mContext).load(film.getId_movie()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPopupMenu(holder.overflow);
                Toast.makeText(mContext, film.getNama_film().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPopupMenu(holder.overflow);
                Toast.makeText(mContext, "Pesan "+film.getNama_film().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }
}
