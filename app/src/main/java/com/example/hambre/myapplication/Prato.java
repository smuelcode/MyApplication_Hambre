package com.example.hambre.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Prato extends AppCompatActivity {

    private static final String PREF_NAME = "MainActivityPreferences";
    private static final String URL = "http://tellunar.com.br/busca_prato.php";
    private TextView nom_coz,nom_prato,num_por,valor,inicio,fim;
    private String cozinheiro,nom_c,nom_p,num_p,valor_p,ini,fin;
    private RequestQueue requestQueue;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato);

        SharedPreferences sp;
        sp = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        cozinheiro = sp.getString("cozinheiro","");


        requestQueue = Volley.newRequestQueue(this);

        nom_coz = (TextView)findViewById(R.id.nome);
        nom_prato = (TextView)findViewById(R.id.saida_prato);
        num_por = (TextView)findViewById(R.id.saida_numero);
        valor = (TextView)findViewById(R.id.saida_valor);
        inicio = (TextView)findViewById(R.id.saida_in);
        fim = (TextView)findViewById(R.id.saida_fim);

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    nom_c = jsonObject.getString("cozinheiro");
                    nom_p = jsonObject.getString("Prato_dia");
                    num_p = jsonObject.getString("Qtd_pratos");
                    valor_p = jsonObject.getString("Valor_prato");
                    ini = jsonObject.getString("Inicio");
                    fin = jsonObject.getString("Termino");

                    nom_coz.setText(nom_c);
                    nom_prato.setText(nom_p);
                    num_por.setText(num_p);
                    valor.setText(valor_p);
                    inicio.setText(ini);
                    fim.setText(fin);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {

                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("nome",cozinheiro);

                return hashMap;

            }
        };

        requestQueue.add(request);
    }
    public void onBackPressed(){

        SharedPreferences sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("cozinheiro").apply();
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));


    }
}







