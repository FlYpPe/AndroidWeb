package com.example.aplicacionweb;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controlador.AnalizadorJSON;

public class ActivityRegistro extends Activity {
    EditText usuario, contra;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        usuario = findViewById(R.id.reg_usuario);
        contra = findViewById(R.id.reg_contra);

    }

    public void agregarAlumno(View v){
        if (usuario.getText().toString().equals("") || contra.getText().toString().equals("")){
            Toast.makeText(getBaseContext(), "Datos invalidos", Toast.LENGTH_LONG).show();
        }else {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            Network network = cm.getActiveNetwork();

            if(network != null && cm.getNetworkCapabilities(cm.getActiveNetwork()) != null ){
                //Obtener datos
                String us = usuario.getText().toString();
                String c = contra.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://paginafelipe.programacionisc2018.com/API_REST_MySQL/api_registro.php";
                        String metodo = "POST";



                        AnalizadorJSON aJSON = new AnalizadorJSON();

                        JSONObject resultado = aJSON.peticionHTTPRegistrar(url, metodo, us,c);

                        String res = null;
                        try {
                            res = resultado.getString("exito");
                            String msj ="";
                            if (res.equals("true"))
                                msj = "AGREGADO CON EXITO";
                            else
                                msj = resultado.getString("Mensaje");
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
}
