package com.example.nabella.moviestation.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.entities.Customer;
import com.example.nabella.moviestation.fragment.BalanceFragment;
import com.example.nabella.moviestation.tab_saldo.Tab_S_HFragment;
import com.example.nabella.moviestation.tab_saldo.Tab_S_TFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class SaldoActivity extends ActionBarActivity implements MaterialTabListener {

    public Customer customer;
    MaterialTabHost tabHost;
    ViewPager viewPager;
    ViewPagerAdapter androidAdapter;
    Toolbar toolBar;

    TextView txt_saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);
        //android toolbar
        toolBar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolBarS);
        this.setSupportActionBar(toolBar);

        //tab host
        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHostS);
        viewPager = (ViewPager) this.findViewById(R.id.viewPagerS);

        //adapter view
        androidAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(androidAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int tabposition) {
                tabHost.setSelectedNavigationItem(tabposition);
            }
        });

        tabHost.addTab(
                tabHost.newTab()
                        .setText("Tambah Saldo")
                        .setTabListener(this)
        );
        tabHost.addTab(
                tabHost.newTab()
                        .setText("History")
                        .setTabListener(this)
        );

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(SaldoActivity.this, BalanceFragment.class);
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
    // view pager adapter
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            if(i == 0) // if the position is 0 we are returning the First tab
            {
                Tab_S_TFragment tab_s_t_fragment = new Tab_S_TFragment();
                return tab_s_t_fragment;
            }
            else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
            {
                Tab_S_HFragment tab_s_hFragment = new Tab_S_HFragment();
                return tab_s_hFragment;
            }
        }

        @Override
        public int getCount() {

            return 2;
        }
    }

    public boolean loadDataUsersLogin(){
        SharedPreferences sharedPref = SaldoActivity.this.getSharedPreferences("data_private", 0);
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
