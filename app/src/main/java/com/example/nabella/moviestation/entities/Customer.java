package com.example.nabella.moviestation.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nabella on 5/15/2017.
 */

public class Customer {
    private String id_customer;
    private String email;
    private String nama;
    private String gender;
    private String no_hp;
    private String saldo;
    private String foto;

    private JSONObject json;
    private Boolean isCheck;

    public Customer(JSONObject json) throws JSONException {
        this.json = json;
        this.id_customer = this.json.getString("id_customer");
        this.email = this.json.getString("email");
        this.nama = this.json.getString("nama");
        this.gender = this.json.getString("gender");
        this.no_hp = this.json.getString("no_hp");
        this.saldo = this.json.getString("saldo");
    }

    public String getId_customer() {return id_customer;}
    public void setId_customer(String id_customer) {this.id_customer = id_customer;}

    public String getEmail(){return  email;}
    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNo_hp() {
        return no_hp;
    }
    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getSaldo() {
        return saldo;
    }
    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Boolean getCheck() {
        return isCheck;
    }
    public void setCheck(Boolean check) {
        isCheck = check;
    }
}
