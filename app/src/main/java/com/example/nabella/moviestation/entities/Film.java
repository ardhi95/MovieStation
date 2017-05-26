package com.example.nabella.moviestation.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nabella on 5/25/2017.
 */

public class Film {
    private String id_movie,
            id_jadwal,
            id_bioskop,
            Poster,
            Title,
            jam,
            kuota,
            harga,
            tgl_mulai,
            tgl_selesai;
    private JSONObject json;

    public Film(JSONObject json) throws JSONException {
        this.json = json;
        this.id_movie = this.json.getString("id_movie");
        this.id_jadwal = this.json.getString("id_jadwal");
        this.id_bioskop = this.json.getString("id_bioskop");
        this.Poster = this.json.getString("Poster");
        this.Title = this.json.getString("Title");
        this.jam = this.json.getString("jam");
        this.kuota = this.json.getString("kuota");
        this.harga = this.json.getString("harga");
        this.tgl_mulai = this.json.getString("tgl_mulai");
        this.tgl_selesai = this.json.getString("tgl_selesai");
    }

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getId_bioskop() {
        return id_bioskop;
    }

    public void setId_bioskop(String id_bioskop) {
        this.id_bioskop = id_bioskop;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getKuota() {
        return kuota;
    }

    public void setKuota(String kuota) {
        this.kuota = kuota;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTgl_mulai() {
        return tgl_mulai;
    }

    public void setTgl_mulai(String tgl_mulai) {
        this.tgl_mulai = tgl_mulai;
    }

    public String getTgl_selesai() {
        return tgl_selesai;
    }

    public void setTgl_selesai(String tgl_selesai) {
        this.tgl_selesai = tgl_selesai;
    }
}
