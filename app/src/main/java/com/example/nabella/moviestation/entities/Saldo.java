package com.example.nabella.moviestation.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nabella on 5/22/2017.
 */

public class Saldo {
    private String id_transaksi_saldo;
    private String jumlah_saldo;
    private String tanggal;
    private String id_customer;
    private String status;

    private JSONObject json;
    private Boolean isCheck;

    public Saldo(JSONObject json) throws JSONException{
        this.json = json;
        this.id_transaksi_saldo = this.json.getString("id_transaksi_saldo");
        this.jumlah_saldo = this.json.getString("jumlah_saldo");
        this.tanggal = this.json.getString("tanggal");
        this.id_customer = this.json.getString("id_customer");
        this.status = this.json.getString("status");
    }

    public String getId_transaksi_saldo() {
        return id_transaksi_saldo;
    }

    public void setId_transaksi_saldo(String id_transaksi_saldo) {
        this.id_transaksi_saldo = id_transaksi_saldo;
    }

    public String getJumlah_saldo() {
        return jumlah_saldo;
    }

    public void setJumlah_saldo(String jumlah_saldo) {
        this.jumlah_saldo = jumlah_saldo;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isCheck() {
        return isCheck();
    }

    public void setCheck(Boolean isCheck) {
        this.isCheck = isCheck;
    }
}
