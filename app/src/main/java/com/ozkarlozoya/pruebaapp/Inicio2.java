package com.ozkarlozoya.pruebaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.ozkarlozoya.pruebaapp.MainActivity.JSON;

public class Inicio2 extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio2);
        OkHttpClient client = new OkHttpClient();
        String url = "https://staging.api.socioinfonavit.io/api/v1/member/wallets";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("sara: " + response);
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    Inicio2.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("liz: " + myResponse);
                            try {
                                JSONObject obj = new JSONObject(myResponse.toString());
                                JSONArray arr = obj.getJSONArray(myResponse);
                                for (int i = 0; i < arr.length(); i++) {
                                    String post_id = arr.getJSONObject(i).getString("name");
                                    System.out.println("sarita: "+post_id);
                                }
                            }catch(Exception e){
                                System.out.println("sarita: "+e.getMessage());
                            }



                        }
                    });
                } else {

                }
            }
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher_background);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.close) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Inicio2.this).create();
                    alertDialog.setTitle("Cerrar sesion");
                    alertDialog.setMessage("Alert message to be shown");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dialog.dismiss();
                                    OkHttpClient client=new OkHttpClient();

                                    String url="https://staging.api.socioinfonavit.io/api/v1/logout\n";


                                    Request request=new Request.Builder()
                                            .url(url)
                                            .delete()
                                            .build();

                                    client.newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            e.printStackTrace();
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            System.out.println("sara: "+"\n"+response);
                                            if(response.isSuccessful()){
                                                final String myResponse=response.body().string();

                                                Inicio2.this.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        System.out.println("liz: "+myResponse);
                                                        finish();
                                                    }
                                                });
                                            }else {

                                            }
                                        }
                                    });
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }




    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
