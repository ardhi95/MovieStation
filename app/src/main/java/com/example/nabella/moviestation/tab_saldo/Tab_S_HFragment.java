package com.example.nabella.moviestation.tab_saldo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.entities.Customer;
import com.example.nabella.moviestation.entities.Saldo;
import com.example.nabella.moviestation.lib.FormData;
import com.example.nabella.moviestation.lib.InternetTask;
import com.example.nabella.moviestation.lib.OnInternetTaskFinishedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_S_HFragment extends Fragment {
    private ArrayList<Saldo> listSaldo;
    public ArrayAdapter<String> adapter;
    public ListView lv;
    public Customer customer;
    EditText txtSearch;



    public Tab_S_HFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab__s__h, container, false);

        txtSearch = (EditText) rootView.findViewById(R.id.search);
        //buat search
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            } //akhir search
        });
        lv = (ListView)rootView.findViewById(R.id.list_History);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Saldo selecetedHS = listSaldo.get(position);
                Intent i = new Intent(getContext(), Detail_HSActivity.class);

                startActivity(i);
            }
        });
        this.listSaldo = new ArrayList<>();
        getSaldo();
        return rootView;
    }




    public void getSaldo(){
        loadDataUsersLogin();
        FormData data = new FormData();
        data.add("method", "getHistorySaldo");
        data.add("id_customer", customer.getId_customer());
        InternetTask uploadTask = new InternetTask("Saldo", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if (jsonArray.length() > 0){
                            ArrayList<String> listpost = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length();i++){
                                Saldo saldo = new Saldo(jsonArray.getJSONObject(i));
                                listSaldo.add(new Saldo(jsonArray.getJSONObject(i)));
                                listpost.add(saldo.getJumlah_saldo().toString()+"\n"+saldo.getTanggal());
                            }
                            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listpost);
                            lv.setAdapter(adapter);
                        }
                        Toast.makeText(getContext(),jsonArray.toString(),Toast.LENGTH_SHORT).show();
                    }else{
//                            Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //                       Snackbar.make(clContent, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
                //                   Snackbar.make(clContent, internetTask.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
        uploadTask.execute();
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
