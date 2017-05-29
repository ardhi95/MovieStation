package com.example.nabella.moviestation.activity.ticket;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nabella.moviestation.BaseFunct;
import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.activity.adapter.FilmAdapter;
import com.example.nabella.moviestation.entities.Customer;
import com.example.nabella.moviestation.entities.Film;
import com.example.nabella.moviestation.lib.FormData;
import com.example.nabella.moviestation.lib.InternetTask;
import com.example.nabella.moviestation.lib.OnInternetTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieB_Activity extends BaseFunct {

    String txtidB, tanggal, nmB;
    TextView txtNamaB,txtalamat, txtIdcst;
    ImageView imgPict;
    private RecyclerView recyclerView;
    private FilmAdapter adapter;
    private List<Film> filmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_b);
        super.loadDataUsersLogin();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        txtNamaB = (TextView) this.findViewById(R.id.txt_namaB);
        txtalamat = (TextView) this.findViewById(R.id.txt_addrB);
        txtIdcst =(TextView) this.findViewById(R.id.txt_idcst);

        Intent i = getIntent();
        txtidB = i.getStringExtra("id_bioskop");
        nmB = i.getStringExtra("nama_bioskop");
        String addrB = i.getStringExtra("alamat");

        txtNamaB.setText(nmB);
        txtalamat.setText(addrB);
        txtIdcst.setText(super.customer.getId_customer());
        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        filmList = new ArrayList<>();
        adapter = new FilmAdapter(this, filmList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        getMovie();
        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
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

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void getDate(){
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        tanggal = format.format(curDate);
    }

    public void getMovie(){
        FormData data = new FormData();
        data.add("method", "getMovieTic");
        data.add("id_bioskop", txtidB.toString());
        InternetTask uploadTask = new InternetTask("Movie", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        //if (jsonArray.length() > 0){
                        ArrayList<String> listtgl = new ArrayList<String>();
                        for (int i = 0; i < jsonArray.length();i++){
                            getDate();
                            String dn = tanggal;
                            String dm = (String) jsonArray.getJSONObject(i).get("tgl_mulai");
                            String da = (String) jsonArray.getJSONObject(i).get("tgl_selesai");
                            String sdn = dn.replaceAll("[^\\d.]+", "");
                            String sda = da.replaceAll("[^\\d.]+", "");
                            String sdm = dm.replaceAll("[^\\d.]+", "");
                            int idn = Integer.parseInt(sdn);
                            int idm = Integer.parseInt(sdm);
                            int ida = Integer.parseInt(sda);
                            Log.d("tglMulai", "Mulai "+ String.valueOf(idm) +" Akhir "+ String.valueOf(ida));
                                if (idn >= idm && idn <= ida){
                                    filmList.add(new Film(jsonArray.getJSONObject(i)));
                                    adapter.notifyDataSetChanged();
                                }
                         }
                        //Toast.makeText(getContext(),jsonArray.toString(),Toast.LENGTH_SHORT).show();
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
}
