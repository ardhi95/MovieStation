package com.example.nabella.moviestation.tab_saldo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.activity.LoginActivity;
import com.example.nabella.moviestation.activity.MainActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_S_TFragment extends Fragment {
    public Customer customer;
    TextView txt_id;
    EditText edt_noTransi;
    Button bSpnner;
    Spinner spinner;
    String saldo;

    public Tab_S_TFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab__s__t, container, false);

        loadDataUsersLogin();
        edt_noTransi = (EditText) rootView.findViewById(R.id.edt_noTransaksi);
        spinner = (Spinner) rootView.findViewById(R.id.spinner_jmlh);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.nominal_uang, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        txt_id = (TextView) rootView.findViewById(R.id.textView7);
        bSpnner = (Button) rootView.findViewById(R.id.btn_krmSaldo);
        bSpnner.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                doKirimSaldo();
            }
        });



        return rootView;
    }

    public void doKirimSaldo(){

        getSpinner_jml();
        FormData data = new FormData();
        data.add("method", "tambah_saldo");
        data.add("id_customer", customer.getId_customer());
        data.add("jumlah_saldo", saldo);
        data.add("tanggal", "2017-06-02");
        data.add("id_transaksi_saldo", edt_noTransi.getText().toString());
        Log.d("datakirim", data.toString());
        InternetTask uploadTask = new InternetTask("Saldo", data);
        Log.d("datanya", data.toString());
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        Toast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //Snackbar.make(clContent, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
                //Snackbar.make(clContent, internetTask.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
        uploadTask.execute();
    }

    public  void getSpinner_jml(){
        String jml = spinner.getSelectedItem().toString();
        if (jml.equals("Rp. 1.000.000")){
            saldo = "1000000";
        }else if(jml.equals("Rp. 700.000")){
            saldo = "700000";
        }else if(jml.equals("Rp. 500.000")){
            saldo = "500000";
        }else if(jml.equals("Rp. 300.000")){
            saldo = "300000";
        }else {
            saldo = "100000";
        }
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
