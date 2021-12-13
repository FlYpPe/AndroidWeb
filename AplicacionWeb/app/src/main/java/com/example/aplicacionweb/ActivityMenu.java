package com.example.aplicacionweb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class ActivityMenu extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void abrirActivities(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btn_altas: i = new Intent(this, ActivityAltas.class);
                startActivity(i);
                break;

            case R.id.btn_consultas: i = new Intent(this, ActivityConsultas.class);
                startActivity(i);
                break;


        }
    }


}
