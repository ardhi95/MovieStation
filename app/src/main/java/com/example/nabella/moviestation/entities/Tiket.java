package com.example.nabella.moviestation.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nabella on 5/31/2017.
 */

public class Tiket {
    private String id_pembelian
                    ,id_jadwal
                    ,id_customer
                    ,id_bioskop
                    ,id_kursi
                    ,kursi
                    ,tgl_beli
                    ,jml_uang
                    ,status;
    private JSONObject json;
    public Tiket(JSONObject json) throws JSONException {
        this.json = json;
        this.id_pembelian = this.json.getString("id_pembelian");
        this.id_jadwal = this.json.getString("id_jadwal");
        this.id_customer = this.json.getString("id_customer");
        this.id_bioskop = this.json.getString("id_bioskop");
        this.id_kursi = this.json.getString("id_kursi");
        this.kursi = this.json.getString("kursi");
        this.tgl_beli = this.json.getString("tgl_beli");
        this.jml_uang = this.json.getString("jml_uang");
        this.status = this.json.getString("status");
    }

    public String getId_pembelian() {
        return id_pembelian;
    }

    public void setId_pembelian(String id_pembelian) {
        this.id_pembelian = id_pembelian;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getId_bioskop() {
        return id_bioskop;
    }

    public void setId_bioskop(String id_bioskop) {
        this.id_bioskop = id_bioskop;
    }

    public String getId_kursi() {
        return id_kursi;
    }

    public void setId_kursi(String id_kursi) {
        this.id_kursi = id_kursi;
    }

    public String getKursi() {
        return kursi;
    }

    public void setKursi(String kursi) {
        this.kursi = kursi;
    }

    public String getJml_uang() {
        return jml_uang;
    }

    public void setJml_uang(String jml_uang) {
        this.jml_uang = jml_uang;
    }

    public String getTgl_beli() {
        return tgl_beli;
    }

    public void setTgl_beli(String tgl_beli) {
        this.tgl_beli = tgl_beli;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
