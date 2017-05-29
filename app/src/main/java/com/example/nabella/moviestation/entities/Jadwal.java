package com.example.nabella.moviestation.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nabella on 5/27/2017.
 */

public class Jadwal {
    private String id_jadwal;
    private String id_movie;
    private String jam;
    private String type_theater;
    private String kuota;
    private String tgl_mulai;
    private String tgl_selesai;
    private String harga;

    private JSONObject json;
    public Jadwal(JSONObject json) throws JSONException {
        this.json = json;
        this.id_jadwal = this.json.getString("id_jadwal");
        this.id_movie = this.json.getString("id_movie");
        this.jam = this.json.getString("jam");
        this.type_theater = this.json.getString("type_theater");
        this.kuota = this.json.getString("kuota");
        this.tgl_mulai = this.json.getString("tgl_mulai");
        this.tgl_selesai = this.json.getString("tgl_selesai");
        this.harga = this.json.getString("harga");
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

    public String getType_theater() {
        return type_theater;
    }

    public void setType_theater(String type_theater) {
        this.type_theater = type_theater;
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
