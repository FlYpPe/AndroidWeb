package com.example.aplicacionweb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controlador.AnalizadorJSON;

public class ActivityCambios extends Activity {
    TextView tx1;
    EditText nom, cant, pre, uni, desc;
    Spinner sp1, sp2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios);

        tx1 = findViewById(R.id.tx_1);
        nom = findViewById(R.id.c_nom);
        cant = findViewById(R.id.c_cant);
        pre = findViewById(R.id.c_pre);
        uni = findViewById(R.id.c_uni);
        desc = findViewById(R.id.c_desc);
        sp1 = findViewById(R.id.spn_1);
        sp2 = findViewById(R.id.spn_2);
        tx1.setText(getIntent().getStringExtra("registro"));
        String objeto = getIntent().getStringExtra("registro");
        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = "http://paginafelipe.programacionisc2018.com/API_REST_MySQL/api_consultas.php";
                String metodo = "POST";
                AnalizadorJSON aJSON = new AnalizadorJSON();
                JSONObject resultado = aJSON.peticionHTTPConsultas(url, metodo, objeto);



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONArray datos = resultado.getJSONArray("objetos");


                            for (int i = 0; i < 1; i++) {
                                //"id":"1","nom":"C. Puerco
                                //1","prov":"1","cat":"1","cant":"10","pre":"10","stoc":"100","desc":"Carne"
                                nom.setText(datos.getJSONObject(i).getString("nom"));
                                cant.setText(datos.getJSONObject(i).getString("cant"));
                                sp1.setSelection(Integer.parseInt(datos.getJSONObject(i).getString("prov"))-1);
                                sp2.setSelection(Integer.parseInt(datos.getJSONObject(i).getString("cat"))-1);
                                pre.setText(datos.getJSONObject(i).getString("pre"));
                                uni.setText(datos.getJSONObject(i).getString("stoc"));
                                desc.setText(datos.getJSONObject(i).getString("desc"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });







            }//run
        }).start();
        desc.setText("sabe");

    }

    public void eliminar(View v){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();

        if(network != null && cm.getNetworkCapabilities(cm.getActiveNetwork()) != null ){
            //Obtener datos
            //String nc = cajaNumControl.getText().toString();
            //String n = cajaNombre.getText().toString();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = "http://paginafelipe.programacionisc2018.com/API_REST_MySQL/api_bajas.php";
                    String metodo = "POST";
                    Map<String, String> mapaDatos  =new HashMap<>();
                    mapaDatos.put("id", tx1.getText().toString());


                    AnalizadorJSON aJSON = new AnalizadorJSON();

                    JSONObject resultado = aJSON.peticionHTTPelim(url, metodo, mapaDatos);

                    String res = null;
                    try {
                        res = resultado.getString("exito");
                        String msj ="";
                        if (res.equals("true"))
                            msj = "Eliminado CON EXITO";
                        else
                            msj = "ERROR EN LA INSERCION";
                        String finalMsj = msj;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), finalMsj, Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public void cambiar(View v){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();

        if(network != null && cm.getNetworkCapabilities(cm.getActiveNetwork()) != null ){
            //Obtener datos
            // String nc = cajaNumControl.getText().toString();
            //String n = cajaNombre.getText().toString();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = "http://paginafelipe.programacionisc2018.com/API_REST_MySQL/api_modificacion.php";
                    String metodo = "POST";
                    Map<String, String> mapaDatos  =new HashMap<>();
                    mapaDatos.put("id", tx1.getText().toString());
                    mapaDatos.put("nom", nom.getText().toString());
                    mapaDatos.put("prov", sp1.getSelectedItem().toString());
                    mapaDatos.put("cat", sp2.getSelectedItem().toString());
                    mapaDatos.put("cant", cant.getText().toString());
                    mapaDatos.put("pre", pre.getText().toString());
                    mapaDatos.put("stoc", uni.getText().toString());
                    mapaDatos.put("desc", desc.getText().toString());
                    AnalizadorJSON aJSON = new AnalizadorJSON();

                    JSONObject resultado = aJSON.peticionHTTPmod(url, metodo, mapaDatos);



                            try {
                                String res = null;
                                res = resultado.getString("exito");
                                String msj ="";
                                if (res.equals("true"))
                                    msj = "modificado CON EXITO";
                                else
                                    msj = "ERROR EN LA mod";
                                String finalMsj = msj;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getBaseContext(), finalMsj, Toast.LENGTH_LONG).show();
                                        finish();
                                        overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);


                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                }
            }).start();
        }
    }




}

