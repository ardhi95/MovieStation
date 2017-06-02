package com.example.nabella.moviestation.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.activity.SaldoActivity;
import com.example.nabella.moviestation.entities.Customer;
import com.example.nabella.moviestation.lib.FormData;
import com.example.nabella.moviestation.lib.InternetTask;
import com.example.nabella.moviestation.lib.OnInternetTaskFinishedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BalanceFragment extends Fragment {
    //tambah saldo
    Button b_isisaldo;
    TextView txt_saldo;
    public Customer customer;
    public BalanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_balance, container, false);
        // Inflate the layout for this fragment
        b_isisaldo = (Button) rootView.findViewById(R.id.btn_isiSaldo);
        b_isisaldo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(),
                        SaldoActivity.class);
                startActivity(mainIntent);
            }
        });
        txt_saldo = (TextView) rootView.findViewById(R.id.txt_SaldoFrag);
        doGetData();
        return rootView;
    }
    @SuppressLint("SetTextI18n")
    public void doGetData(){
        //String hsl = "";
        loadDataUsersLogin();
        String uang = customer.getSaldo();
        /*String[] rupiah = uang.split("(?<=\\\\G.{3})");
        String hsl = "."+rupiah.toString();*/
        txt_saldo.setText("Rp. "+uang);
    }

    public boolean loadDataUsersLogin(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("data_private", 0);
        Log.d("karepmu A", "sakkarepmu A");
        String data = sharedPref.getString("data", "");
        if (data != ""){
            Log.d("karepmu B", "sakkarepmu B");
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setPrettyPrinting()
                    .enableComplexMapKeySerialization()
                    .create();
            customer = gson.fromJson(data, Customer.class);
            return true;
        }else{
            return false;
        }
    }

}
