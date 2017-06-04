package com.example.nabella.moviestation.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.activity.adapter.BioskopAdapter;
import com.example.nabella.moviestation.activity.adapter.TiketAdapter;
import com.example.nabella.moviestation.entities.Bioskop;
import com.example.nabella.moviestation.entities.Customer;
import com.example.nabella.moviestation.entities.Tiket;
import com.example.nabella.moviestation.lib.FormData;
import com.example.nabella.moviestation.lib.InternetTask;
import com.example.nabella.moviestation.lib.OnInternetTaskFinishedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    private RecyclerView recyclerView;
    private TiketAdapter adapter;
    private List<Tiket> tiketList;
    public Customer customer;
    public SettingsFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        tiketList = new ArrayList<>();
        adapter = new TiketAdapter(getActivity(), tiketList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(3), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        getTiket();
        return rootView;
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private void getTiket() {
        loadDataUsersLogin();
        FormData data = new FormData();
        data.add("method", "getTicketCust");
        data.add("id_customer", customer.getId_customer());
        InternetTask uploadTask = new InternetTask("Ticket", data);
        Log.d("idcust", customer.getId_customer());
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if (jsonArray.length() > 0){
                            for (int i = 0; i < jsonArray.length();i++){
                                tiketList.add(new Tiket(jsonArray.getJSONObject(i)));
                                Log.d("testnya", jsonArray.getJSONObject(i).toString());
                                adapter.notifyDataSetChanged();
                                //Toast.makeText(getContext(),bioskopList.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
            }
        });
        uploadTask.execute();
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public boolean loadDataUsersLogin(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("data_private", 0);
        String data = sharedPref.getString("data", "");
        if (data != ""){
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
