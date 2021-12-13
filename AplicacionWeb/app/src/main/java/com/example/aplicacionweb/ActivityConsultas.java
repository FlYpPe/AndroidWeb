package com.example.aplicacionweb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import controlador.AnalizadorJSON;

public class ActivityConsultas extends Activity {

    RecyclerView recyclerView;
    AdaptadorRegistros adapter;
    AdaptadorObjetos adaptador;
    ArrayList<ObjetoVo> listaObjetos = new ArrayList<>();
    ArrayList ids = new ArrayList();
    RecyclerView.LayoutManager layoutManager;
    EditText edit_consulta;

    Context context = this;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = "http://paginafelipe.programacionisc2018.com/API_REST_MySQL/api_consultas.php";
                String metodo = "POST";
                AnalizadorJSON aJSON = new AnalizadorJSON();
                JSONObject resultado = aJSON.peticionHTTPConsultas(url, metodo, edit_consulta.getText().toString());

                // Log.d("---->", resultado.toString());

                try {
                    JSONArray datos = resultado.getJSONArray("objetos");
                    String cad = "";
                    for (int i = 0; i < datos.length(); i++) {
                        String nombre = datos.getJSONObject(i).getString("nom");

                        ids.add(datos.getJSONObject(i).getString("id"));
                        listaObjetos.add(new ObjetoVo(nombre, "", R.drawable.objeto));



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adaptador = new AdaptadorObjetos(listaObjetos);

                        adaptador.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent in = new Intent(context, ActivityCambios.class);
                                in.putExtra("registro", ids.get(recyclerView.getChildAdapterPosition(v)).toString());
                                startActivity(in);

                                Log.d("---->", ids.toString());
                            }
                        });
                                /*adapter = new AdaptadorRegistros(registros);
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getApplicationContext(), "Seleccion" +
                                                registros.get(recyclerView.getChildAdapterPosition(v)).toString(), Toast.LENGTH_LONG).show();
                                        Intent in = new Intent(context, ActivityCambios.class);
                                        in.putExtra("registro", registros.get(recyclerView.getChildAdapterPosition(v)).toString());
                                        startActivity(in);
                                    }
                                });*/
                        recyclerView.setAdapter(adaptador);

                    }
                });
            }//run
        }).start();

        //---------------------------------------------------

        adaptador = new AdaptadorObjetos(listaObjetos);
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ActivityCambios.class);
                in.putExtra("registro", ids.get(recyclerView.getChildAdapterPosition(v)).toString());
                startActivity(in);

            }
        });
        //adapter = new AdaptadorRegistros(registros);
                /*adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Seleccion" +
                                registros.get(recyclerView.getChildAdapterPosition(v)).toString(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context, ActivityCambios.class);
                    i.putExtra("registro", registros.get(recyclerView.getChildAdapterPosition(v)).toString());
                        startActivity(i);

                    }
                });*/
        recyclerView.setAdapter(adaptador);
        ids.clear();
        listaObjetos.clear();

    }

    static ArrayList registros = new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        recyclerView = findViewById(R.id.recycler_consultas);
        edit_consulta = (EditText) findViewById(R.id.edit_cons);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ids.clear();
        registros.clear();

        edit_consulta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String url = "http://paginafelipe.programacionisc2018.com/API_REST_MySQL/api_consultas.php";
                        String metodo = "POST";
                        AnalizadorJSON aJSON = new AnalizadorJSON();
                        JSONObject resultado = aJSON.peticionHTTPConsultas(url, metodo, edit_consulta.getText().toString());

                        // Log.d("---->", resultado.toString());

                        try {
                            JSONArray datos = resultado.getJSONArray("objetos");
                            String cad = "";
                            for (int i = 0; i < datos.length(); i++) {
                                String nombre = datos.getJSONObject(i).getString("nom");

                                ids.add(datos.getJSONObject(i).getString("id"));
                                listaObjetos.add(new ObjetoVo(nombre, "", R.drawable.objeto));



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adaptador = new AdaptadorObjetos(listaObjetos);

                                adaptador.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent in = new Intent(context, ActivityCambios.class);
                                        in.putExtra("registro", ids.get(recyclerView.getChildAdapterPosition(v)).toString());
                                        startActivity(in);

                                        Log.d("---->", ids.toString());
                                    }
                                });
                                /*adapter = new AdaptadorRegistros(registros);
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getApplicationContext(), "Seleccion" +
                                                registros.get(recyclerView.getChildAdapterPosition(v)).toString(), Toast.LENGTH_LONG).show();
                                        Intent in = new Intent(context, ActivityCambios.class);
                                        in.putExtra("registro", registros.get(recyclerView.getChildAdapterPosition(v)).toString());
                                        startActivity(in);
                                    }
                                });*/
                                recyclerView.setAdapter(adaptador);

                            }
                        });
                    }//run
                }).start();

                //---------------------------------------------------

                adaptador = new AdaptadorObjetos(listaObjetos);
                adaptador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(context, ActivityCambios.class);
                        in.putExtra("registro", ids.get(recyclerView.getChildAdapterPosition(v)).toString());
                        startActivity(in);

                    }
                });
                //adapter = new AdaptadorRegistros(registros);
                /*adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Seleccion" +
                                registros.get(recyclerView.getChildAdapterPosition(v)).toString(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context, ActivityCambios.class);
                    i.putExtra("registro", registros.get(recyclerView.getChildAdapterPosition(v)).toString());
                        startActivity(i);

                    }
                });*/
                recyclerView.setAdapter(adaptador);
                ids.clear();
                listaObjetos.clear();
            }
        });

        //String registros[] = {"Padme", "Leia", "Luke", "Han"}; //registros de prueba

        // ------------- Obtener los registros ---------------

        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = "http://paginafelipe.programacionisc2018.com/API_REST_MySQL/api_consultas.php";
                String metodo = "POST";
                AnalizadorJSON aJSON = new AnalizadorJSON();
                JSONObject resultado = aJSON.peticionHTTPConsultas(url, metodo, edit_consulta.getText().toString());

                // Log.d("---->", resultado.toString());

                try {
                    JSONArray datos = resultado.getJSONArray("objetos");
                    String cad = "";
                    for (int i = 0; i < datos.length(); i++) {
                        String nombre = datos.getJSONObject(i).getString("nom");

                        listaObjetos.add(new ObjetoVo(nombre, "", R.drawable.objeto));
                        ids.add(datos.getJSONObject(i).getString("id"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adaptador = new AdaptadorObjetos(listaObjetos);
                        adaptador.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent in = new Intent(context, ActivityCambios.class);
                                in.putExtra("registro", ids.get(recyclerView.getChildAdapterPosition(v)).toString());
                                startActivity(in);

                            }
                        });
                                /*adapter = new AdaptadorRegistros(registros);
                                adapter.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getApplicationContext(), "Seleccion" +
                                                registros.get(recyclerView.getChildAdapterPosition(v)).toString(), Toast.LENGTH_LONG).show();
                                        Intent in = new Intent(context, ActivityCambios.class);
                                        in.putExtra("registro", registros.get(recyclerView.getChildAdapterPosition(v)).toString());
                                        startActivity(in);
                                    }
                                });*/
                        recyclerView.setAdapter(adaptador);

                    }
                });
            }//run
        }).start();

        //---------------------------------------------------

        adaptador = new AdaptadorObjetos(listaObjetos);
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ActivityCambios.class);
                in.putExtra("registro", ids.get(recyclerView.getChildAdapterPosition(v)).toString());
                startActivity(in);

            }
        });
        /*adapter = new AdaptadorRegistros(registros);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Seleccion" +
                        registros.get(recyclerView.getChildAdapterPosition(v)).toString(), Toast.LENGTH_LONG).show();
                Intent in = new Intent(context, ActivityCambios.class);
                in.putExtra("registro", registros.get(recyclerView.getChildAdapterPosition(v)).toString());
                startActivity(in);
            }
        });
        recyclerView.setAdapter(adapter);
        registros.clear();*/
        try {
            recyclerView.setAdapter(adaptador);
            ids.clear();
            listaObjetos.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

class AdaptadorRegistros extends RecyclerView.Adapter<AdaptadorRegistros.MyViewHolder> implements View.OnClickListener {
    private ArrayList datos;
    public AdaptadorRegistros(ArrayList datos){
        this.datos = datos;
    }
    @NonNull
    @Override

    public AdaptadorRegistros.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(textView);
        textView.setOnClickListener(this);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.textView.setText((Integer) datos.get(position));
        holder.textView.setText((CharSequence) datos.get(position));
    }
    @Override
    public int getItemCount() {
        return datos.size();
    }

    private View.OnClickListener listener;
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    //clase INTERNA
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(TextView t){
            super(t);
            textView = t;
        }
    }
}//Clase AdaptadorRegistros



class AdaptadorObjetos extends RecyclerView.Adapter<AdaptadorObjetos.ViewHolderObjetos> implements View.OnClickListener{
    ArrayList<ObjetoVo> listaObjetos;
    public AdaptadorObjetos(ArrayList<ObjetoVo> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    @NonNull
    @Override
    public ViewHolderObjetos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element,null,false);
        view.setOnClickListener(this);
        return new ViewHolderObjetos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderObjetos holder, int position) {
        holder.nombre.setText(listaObjetos.get(position).getNombre());
        holder.info.setText(listaObjetos.get(position).getInfo());
        holder.foto.setImageResource(listaObjetos.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return listaObjetos.size();
    }

    private View.OnClickListener listener;


    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }


    public class ViewHolderObjetos extends RecyclerView.ViewHolder {
            TextView nombre, info;
            ImageView foto;

            public ViewHolderObjetos(View itemView) {
                super(itemView);
                nombre = (TextView) itemView.findViewById(R.id.idNombre);
                info = (TextView) itemView.findViewById(R.id.idInfo);
                foto = (ImageView) itemView.findViewById(R.id.idImagen);

            }
        }
}


