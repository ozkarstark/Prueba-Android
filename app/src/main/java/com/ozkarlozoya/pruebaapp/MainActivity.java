package com.ozkarlozoya.pruebaapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    EditText txtCorreo;
    EditText txtPass;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        txtCorreo=(EditText) findViewById(R.id.txtCorreo);
        txtPass=(EditText) findViewById(R.id.pass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient client=new OkHttpClient();
                //final String json="{\"user\":{ \"email\":\"gerardo@nextia.mx\",\"password\":\"securepassword\"} }";
                final String json="{\"user\":{ \"email\":\""+txtCorreo.getText()+"\",\"password\":\""+txtPass.getText()+"\"} }";
                String url="https://staging.api.socioinfonavit.io/api/v1/login";
                String url2="https://pokeapi.co/api/v2/pokemon/ditto";
                final RequestBody body=RequestBody.create(JSON,json);
                Request request=new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("sara: "+json+"\n"+"\n"+response);
                        if(response.isSuccessful()){
                            final String myResponse=response.body().string();

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("liz: "+myResponse);
                                    Intent intent=new Intent(MainActivity.this, Inicio2.class);
                                    startActivity(intent);
                                }
                            });
                        }else {
                            Thread thread = new Thread(){
                                public void run(){
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            new AlertDialog.Builder(MainActivity.this)
                                                    .setTitle("No se pudo Iniciar sesion")
                                                    .setMessage("Correo o contraseñas incorrectos")
                                                    .setPositiveButton("Nuke", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            //Log.d("MainActivity", "Sending atomic bombs to Jupiter");
                                                        }
                                                    })

                                                    .show();
                                        }
                                    });
                                }
                            };
                            thread.start();


                        }
                    }
                });
            }
        });



    }
}
