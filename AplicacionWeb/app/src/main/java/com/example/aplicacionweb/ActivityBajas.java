package com.example.aplicacionweb;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controlador.AnalizadorJSON;

public class ActivityBajas extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajas);
    }

    public void eliminaAlumno(View v){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();

        if(network != null && cm.getNetworkCapabilities(cm.getActiveNetwork()) != null ){
            //Obtener datos
           //String nc = cajaNumControl.getText().toString();
            //String n = cajaNombre.getText().toString();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = "http://10.0.2.2/ProyectoPagina/ApI_REST_MySQL/api_bajas.php";
                    String metodo = "POST";
                    Map<String, String> mapaDatos  =new HashMap<>();
                    mapaDatos.put("nc", "7");


                    AnalizadorJSON aJSON = new AnalizadorJSON();

                    JSONObject resultado = aJSON.peticionHTTPelim(url, metodo, mapaDatos);

                    String res = null;
                    try {
                        res = resultado.getString("exito");
                        String msj ="";
                        if (res.equals("true"))
                            msj = "AGREGADO CON EXITO";
                        else
                            msj = "ERROR EN LA INSERCION";
                        String finalMsj = msj;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), finalMsj, Toast.LENGTH_LONG).show();

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
