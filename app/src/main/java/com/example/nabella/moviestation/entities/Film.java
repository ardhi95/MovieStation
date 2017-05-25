package com.example.nabella.moviestation.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nabella on 5/25/2017.
 */

public class Film {

    private String nama_film;
    private String harga;
    private String id_bioskop;
    private String id_movie;
    private String id_jadwal;
    private JSONObject json;

    public Film(JSONObject json) throws JSONException {
        this.json = json;
        this.id_bioskop = this.json.getString("id_bioskop");
        this.nama_film = this.json.getString("nama_film");
        this.harga = this.json.getString("harga");
        this.id_jadwal = this.json.getString("id_jadwal");
        this.id_movie = this.json.getString("id_movie");
    }

    public String getId_bioskop() {
        return id_bioskop;
    }

    public void setId_bioskop(String id_bioskop) {
        this.id_bioskop = id_bioskop;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getNama_film() {
        return nama_film;
    }

    public void setNama_film(String nama_film) {
        this.nama_film = nama_film;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }
}
