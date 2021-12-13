package com.example.aplicacionweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controlador.AnalizadorJSON;

public class MainActivity extends AppCompatActivity {
    EditText usuario, contra;
    String val;
    Context cont = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = findViewById(R.id.caja_nombre);
        contra = findViewById(R.id.caja_contraseña);
    }

    public void abrirActivities(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.btn_reg:
                i = new Intent(this, ActivityRegistro.class);
                startActivity(i);
                break;
            case R.id.btn_log:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://paginafelipe.programacionisc2018.com/API_REST_MySQL/api_login.php";
                        String metodo = "POST";

                        AnalizadorJSON aJSON = new AnalizadorJSON();

                        JSONObject resultado = aJSON.peticionHTTPValidar(url, metodo, usuario.getText().toString(),contra.getText().toString());

                        String res = null;
                        try {
                            res = resultado.getString("valido");
                            String msj ="";
                            if (res.equals("true")){
                                Intent i = new Intent(cont, ActivityMenu.class);
                                startActivity(i);
                                msj = "Ingreso con EXITO";

                            }

                            else{
                                msj = "error en el ingreso";
                            }

                            String finalMsj = msj;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getBaseContext(), finalMsj, Toast.LENGTH_LONG).show();
                                    if (finalMsj.equals("Ingreso con EXITO")){
                                        usuario.setText("");
                                        contra.setText("");
                                    }
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                }).start();



                break;

        }
    }

    public boolean validar(String usuario, String contraseña){


                String url = "http://10.0.2.2/ProyectoPagina/ApI_REST_MySQL/api_login.php";
                String metodo = "POST";

                AnalizadorJSON aJSON = new AnalizadorJSON();

                JSONObject resultado = aJSON.peticionHTTPValidar(url, metodo, usuario,contraseña);

                String res = null;
                try {
                    res = resultado.getString("exito");
                    String msj ="";
                    if (res.equals("true"))
                        return true;
                    else
                        return false;



                } catch (JSONException e) {
                    e.printStackTrace();
                }

        return false;
    }
}