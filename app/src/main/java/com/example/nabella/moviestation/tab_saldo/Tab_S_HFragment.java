package com.example.nabella.moviestation.tab_saldo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.entities.Saldo;
import com.example.nabella.moviestation.lib.FormData;
import com.example.nabella.moviestation.lib.InternetTask;
import com.example.nabella.moviestation.lib.OnInternetTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_S_HFragment extends Fragment {

    public ArrayAdapter<String> adapter;
    EditText txtSearch;
    public ListView lv;
    private ArrayList<Saldo> listSaldo;

    public Tab_S_HFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        lv = (ListView)rootView.findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Saldo selecetedMakanan = listSaldo.get(position);
                Intent i = new Intent(getContext(), Detail_HSActivity.class);
                i.putExtra("id_transaksi_saldo", String.valueOf(selecetedMakanan.getId_transksi_saldo()));

                startActivity(i);
            }
        });
        this.listSaldo = new ArrayList<>();
        getMakanan();
        return rootView;
    }
    public void getMakanan(){
        FormData data = new FormData();
        data.add("method", "getHistorySaldo");
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
                                Saldo makanan = new Saldo(jsonArray.getJSONObject(i));
                                listSaldo.add(new Saldo(jsonArray.getJSONObject(i)));
                                listpost.add(makanan.getJumlah_saldo().toString()+"\n"+ makanan.getTanggal().toString());
                            }
                            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listpost);
                            lv.setAdapter(adapter);
                        }
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
}
