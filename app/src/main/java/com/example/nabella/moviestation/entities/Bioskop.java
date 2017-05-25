package com.example.nabella.moviestation.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nabella on 5/24/2017.
 */

public class Bioskop {
    private String id_bioskop;
    private String nama_bioskop;
    private String alamat;
    private String picture_url;

    private JSONObject json;

    public Bioskop(JSONObject json) throws JSONException{
        this.json = json;
        this.id_bioskop = this.json.getString("id_bioskop");
        this.nama_bioskop = this.json.getString("nama_bioskop");
        this.alamat = this.json.getString("alamat");
        this.picture_url = this.json.getString("picture_url");
    }

    public String getId_bioskop() {
        return id_bioskop;
    }

    public void setId_bioskop(String id_bioskop) {
        this.id_bioskop = id_bioskop;
    }

    public String getNama_bioskop() {
        return nama_bioskop;
    }

    public void setNama_bioskop(String nama_bioskop) {
        this.nama_bioskop = nama_bioskop;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }
}
