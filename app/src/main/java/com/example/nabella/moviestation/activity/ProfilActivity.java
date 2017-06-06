package com.example.nabella.moviestation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nabella.moviestation.BaseFunct;
import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.lib.FormData;
import com.example.nabella.moviestation.lib.InternetTask;
import com.example.nabella.moviestation.lib.OnInternetTaskFinishedListener;
import com.example.nabella.moviestation.other.CircleTransform;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.nabella.moviestation.R.id.fab;


public class ProfilActivity extends BaseFunct {
    TextView txtNama, txtEmail, txtPhone;
    EditText txt_Phone;
    private ImageView mButton;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        imgNavHeaderBg = (ImageView) findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) findViewById(R.id.img_profile);

        txtNama = (TextView)findViewById(R.id.txt_proNama);
        txtEmail = (TextView)findViewById(R.id.txt_proEmail);
        txtPhone = (TextView)findViewById(R.id.txt_proPhone);

        mButton = (ImageView) findViewById(R.id.btn_edit_phn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                txt_hasil.setText(null);
                edit_phone();
            }
        });

        getProfil();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getProfil(){
        super.loadDataUsersLogin();
        Glide.with(this).load(super.urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(super.imgNavHeaderBg);

        // Loading profile image
        String urlProfileImg = super.customer.getFoto().toString();
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(super.imgProfile);
        txtNama.setText(super.customer.getNama());
        txtEmail.setText(super.customer.getEmail());
        txtPhone.setText(super.customer.getNo_hp());
    }

    public void edit_phone(){
        dialog = new AlertDialog.Builder(ProfilActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_edit_phn, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Edit Nomor Telepon");

        txt_Phone    = (EditText) dialogView.findViewById(R.id.txt_editPhone);

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                doUpdateProfil();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void doUpdateProfil(){
        FormData data = new FormData();
        data.add("method", "update_akun");
        data.add("id_customer", super.customer.getId_customer().toString());
        data.add("no_hp", txt_Phone.getText().toString());
        Log.d("datakirim", data.toString());
        InternetTask uploadTask = new InternetTask("Customer", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){

                    }else{
//                            Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
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
}
