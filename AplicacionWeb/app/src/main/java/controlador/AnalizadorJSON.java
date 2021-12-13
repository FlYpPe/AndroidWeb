package controlador;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class AnalizadorJSON {

    InputStream is = null;
    OutputStream os = null;
    JSONObject jsonObject = null;

    HttpURLConnection conexion = null;
    URL url = null;

    //---------- METODO PARA Altas, Bajas y Cambios
    public JSONObject peticionHTTP(String cadenaURL, String metodo, Map datos){

        //{"nc":"1", "n":"1", "pa":"1", "sa":"1", "e":1, "s":1, "c":"1"}

        try {

            //Enviar PETICION ----------------------------
            String ncCodificado = URLEncoder.encode(String.valueOf(datos.get("nc")), "UTF-8");

            String cadenaJSON = "{\"nom\":\"" + URLEncoder.encode(String.valueOf(datos.get("nom")), "UTF-8") +
                    "\", \"prov\":\"" + URLEncoder.encode(String.valueOf(datos.get("prov")), "UTF-8") +
                    "\", \"cat\":\"" + URLEncoder.encode(String.valueOf(datos.get("cat")), "UTF-8") +
                    "\", \"cant\":\"" + URLEncoder.encode(String.valueOf(datos.get("cant")), "UTF-8") +
                    "\", \"pre\":\"" + URLEncoder.encode(String.valueOf(datos.get("pre")), "UTF-8") +
                    "\", \"stoc\":\"" + URLEncoder.encode(String.valueOf(datos.get("stoc")), "UTF-8") +
                    "\", \"desc\":\"" + URLEncoder.encode(String.valueOf(datos.get("desc")), "UTF-8") + "\"}";
            Log.d("--->", cadenaJSON);
            url = new URL(cadenaURL);
            conexion = (HttpURLConnection) url.openConnection();

            //activa el envio a traves de HTTP
            conexion.setDoOutput(true);

            //indicar el metodo de evio
            conexion.setRequestMethod(metodo);

            //tamaño preestablecido o fijo para la cadena a enviar
            conexion.setFixedLengthStreamingMode(cadenaJSON.length());

            //Establecer el formato de peticion
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());

            os.write(cadenaJSON.getBytes());

            os.flush();
            os.close();

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Recibir RESPUESTA ----------------------
        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder cad = new StringBuilder();

            String fila = null;
            while ((fila = br.readLine()) != null ){
                cad.append(fila+"\n");
            }
            is.close();

            jsonObject = new JSONObject(String.valueOf(cad));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return  jsonObject;

    }//metodo PETICION HTTP


    //----------METODO PARA Consultas
    public JSONObject peticionHTTPConsultas(String cadenaURL, String metodo, String filtro){

        //FILTRO

        filtro = "{\"id\":\""+filtro+"\"}";

        try {

            //Enviar PETICION ----------------------------

            //String filtroCodificado = URLEncoder.encode(String.valueOf(filtro), "UTF-8");
            //completar para busquedas con FILTRO

            url = new URL(cadenaURL);
            conexion = (HttpURLConnection) url.openConnection();

            //activa el envio a traves de HTTP
            conexion.setDoOutput(true);

            //indicar el metodo de evio
            conexion.setRequestMethod(metodo);

            //tamao preestablecido o fijo para la cadena a enviar
            conexion.setFixedLengthStreamingMode(filtro.length());
            //NECESARIO PARA CONSUTLAS CON FILTRO

            //Establecer el formato de peticion
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());
            os.write(filtro.getBytes());

            /*

            //NECESARIO PARA CONSUTLAS CON FILTRO */
            os.flush();
            os.close();

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            Log.d("--->", "trono");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("--->", "trono");
            e.printStackTrace();
        }

        //Recibir RESPUESTA ----------------------
        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder cad = new StringBuilder();

            String fila = null;
            while ((fila = br.readLine()) != null ){
                cad.append(fila+"\n");
            }
            is.close();

            String cadena = cad.toString();
            //Log.d("--->", cadena);
            jsonObject = new JSONObject(cadena);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return  jsonObject;

    }//metodo PETICION HTTP

    public JSONObject peticionHTTPValidar(String cadenaURL, String metodo, String usuario, String contrasena){

        //FILTRO

        String filtro = "{\"usuario\":\""+usuario+"\",\"contrasena\":\""+contrasena+"\"}";
        Log.i("mensaje",filtro);
        try {

            //Enviar PETICION ----------------------------



            url = new URL(cadenaURL);
            conexion = (HttpURLConnection) url.openConnection();

            //activa el envio a traves de HTTP
            conexion.setDoOutput(true);

            //indicar el metodo de evio
            conexion.setRequestMethod(metodo);

            //tamaño preestablecido o fijo para la cadena a enviar
            conexion.setFixedLengthStreamingMode(filtro.length());

            //Establecer el formato de peticion
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());

            os.write(filtro.getBytes());

            os.flush();
            os.close();

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Recibir RESPUESTA ----------------------
        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder cad = new StringBuilder();

            String fila = null;
            while ((fila = br.readLine()) != null ){
                cad.append(fila+"\n");
            }
            is.close();

            jsonObject = new JSONObject(String.valueOf(cad));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return  jsonObject;





    }//metodo PETICION HTTP

    public JSONObject peticionHTTPRegistrar(String cadenaURL, String metodo, String usuario, String contrasena){

        //FILTRO

        String filtro = "{\"usuario\":\""+usuario+"\",\"contrasena\":\""+contrasena+"\"}";
        Log.i("mensaje",filtro);
        try {

            //Enviar PETICION ----------------------------



            url = new URL(cadenaURL);
            conexion = (HttpURLConnection) url.openConnection();

            //activa el envio a traves de HTTP
            conexion.setDoOutput(true);

            //indicar el metodo de evio
            conexion.setRequestMethod(metodo);

            //tamaño preestablecido o fijo para la cadena a enviar
            conexion.setFixedLengthStreamingMode(filtro.length());

            //Establecer el formato de peticion
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());

            os.write(filtro.getBytes());

            os.flush();
            os.close();

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Recibir RESPUESTA ----------------------
        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder cad = new StringBuilder();

            String fila = null;
            while ((fila = br.readLine()) != null ){
                cad.append(fila+"\n");
            }
            is.close();

            jsonObject = new JSONObject(String.valueOf(cad));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return  jsonObject;





    }

    public JSONObject peticionHTTPmod(String cadenaURL, String metodo, Map datos){

        //{"nc":"1", "n":"1", "pa":"1", "sa":"1", "e":1, "s":1, "c":"1"}

        try {

            //Enviar PETICION ----------------------------
            String ncCodificado = URLEncoder.encode(String.valueOf(datos.get("nc")), "UTF-8");
            Map<String, String> mapaDatos  =new HashMap<>();
            String cadenaJSON = "{\"id\":\"" + URLEncoder.encode(String.valueOf(datos.get("id")), "UTF-8") +
                    "\", \"nom\":\"" + URLEncoder.encode(String.valueOf(datos.get("nom")), "UTF-8") +
                    "\", \"prov\":\"" + URLEncoder.encode(String.valueOf(datos.get("prov")), "UTF-8") +
                    "\", \"cat\":\"" + URLEncoder.encode(String.valueOf(datos.get("cat")), "UTF-8") +
                    "\", \"cant\":\"" + URLEncoder.encode(String.valueOf(datos.get("cant")), "UTF-8") +
                    "\", \"pre\":\"" + URLEncoder.encode(String.valueOf(datos.get("pre")), "UTF-8") +
                    "\", \"stoc\":\"" + URLEncoder.encode(String.valueOf(datos.get("stoc")), "UTF-8") +
                    "\", \"desc\":\"" + URLEncoder.encode(String.valueOf(datos.get("desc")), "UTF-8") + "\"}";

            url = new URL(cadenaURL);
            Log.i("mensaje",cadenaJSON);
            conexion = (HttpURLConnection) url.openConnection();

            //activa el envio a traves de HTTP
            conexion.setDoOutput(true);

            //indicar el metodo de evio
            conexion.setRequestMethod(metodo);

            //tamaño preestablecido o fijo para la cadena a enviar
            conexion.setFixedLengthStreamingMode(cadenaJSON.length());

            //Establecer el formato de peticion
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());

            os.write(cadenaJSON.getBytes());

            os.flush();
            os.close();

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Recibir RESPUESTA ----------------------
        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder cad = new StringBuilder();

            String fila = null;
            while ((fila = br.readLine()) != null ){
                cad.append(fila+"\n");
            }
            is.close();

            jsonObject = new JSONObject(String.valueOf(cad));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return  jsonObject;

    }//metodo PETICION HTTP

    public JSONObject peticionHTTPelim(String cadenaURL, String metodo, Map datos){

        //{"nc":"1", "n":"1", "pa":"1", "sa":"1", "e":1, "s":1, "c":"1"}

        try {

            //Enviar PETICION ----------------------------
            String ncCodificado = URLEncoder.encode(String.valueOf(datos.get("nc")), "UTF-8");

            String cadenaJSON = "{\"id\":\"" + URLEncoder.encode(String.valueOf(datos.get("id")), "UTF-8")+ "\"}";

            url = new URL(cadenaURL);
            conexion = (HttpURLConnection) url.openConnection();

            //activa el envio a traves de HTTP
            conexion.setDoOutput(true);

            //indicar el metodo de evio
            conexion.setRequestMethod(metodo);

            //tamaño preestablecido o fijo para la cadena a enviar
            conexion.setFixedLengthStreamingMode(cadenaJSON.length());

            //Establecer el formato de peticion
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            os = new BufferedOutputStream(conexion.getOutputStream());

            os.write(cadenaJSON.getBytes());

            os.flush();
            os.close();

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Recibir RESPUESTA ----------------------
        try {
            is = new BufferedInputStream(conexion.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder cad = new StringBuilder();

            String fila = null;
            while ((fila = br.readLine()) != null ){
                cad.append(fila+"\n");
            }
            is.close();

            jsonObject = new JSONObject(String.valueOf(cad));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return  jsonObject;

    }//metodo PETICION HTTP

}//class
