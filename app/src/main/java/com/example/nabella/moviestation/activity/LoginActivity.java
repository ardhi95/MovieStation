package com.example.nabella.moviestation.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.nabella.moviestation.R;
import com.example.nabella.moviestation.entities.Customer;
import com.example.nabella.moviestation.lib.FormData;
import com.example.nabella.moviestation.lib.InternetTask;
import com.example.nabella.moviestation.lib.OnInternetTaskFinishedListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    public Customer cust;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    /*private ProgressDialog mProgressDialog;*/

    private SignInButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            final String idgoogle = acct.getId();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);

            /*Intent intent = new Intent(getBaseContext(), HomeActivity.class);
            intent.putExtra("id_customer", idgoogle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);*/

            /*txtName.setText(personName);
            txtEmail.setText(email);
            Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);*/

//            updateUI(true);
            FormData data = new FormData();
            data.add("method", "register");
            data.add("id_customer", idgoogle.toString());
            Log.d("id_gooo", idgoogle.toString());
            data.add("email", email.toString());
            data.add("nama", personName.toString());
            data.add("foto",personPhotoUrl.toString());
            Log.d("datakirim", data.toString());
            InternetTask uploadTask = new InternetTask("Customer", data);
            uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
                @Override
                public void OnInternetTaskFinished(InternetTask internetTask) {
                    try {
                        JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                        if (jsonObject.get("code").equals(200)){

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            JSONObject jobj = jsonArray.getJSONObject(0);
                            Log.d("h0g", jobj.toString());
                            saveDataUsersLogin(jobj.toString());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
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
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }
    public void saveDataUsersLogin(String data){
        Log.d("carijson", data.toString());
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("data_private", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("data", data);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
//            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}
