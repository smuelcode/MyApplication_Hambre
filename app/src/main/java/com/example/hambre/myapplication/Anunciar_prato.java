package com.example.hambre.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

class Anunciar_prato extends AppCompatActivity {

    private EditText Prato,Qtd_prato,inicio,fim,valor;
    public Button btanunciar;
    private static final String URL = "http://tellunar.com.br/anuncio_prato.php";
    private StringRequest request;
    private RequestQueue requestQueue;
    private static final String PREF_NAME = "LoginChefPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anunciar);

        SharedPreferences sp;
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        final String Id = sp.getString("ID","");


        requestQueue = Volley.newRequestQueue(this);

        btanunciar = (Button) findViewById(R.id.anunciar);
        btanunciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Prato = (EditText) findViewById(R.id.prato);
                Qtd_prato = (EditText) findViewById(R.id.quantidade);
                inicio = (EditText) findViewById(R.id.horarioincial);
                fim = (EditText) findViewById(R.id.horariofinal);
                valor = (EditText) findViewById(R.id.Valor);


                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(Prato.getText().toString().isEmpty() || Qtd_prato.getText().toString().isEmpty() ||
                                    inicio.getText().toString().isEmpty() || fim.getText().toString().isEmpty() || valor.getText().toString().isEmpty()){

                                alert("Preencha todos os campos.");

                            }
                            else if (jsonObject.names().get(0).equals("sucess")) {

                                Toast.makeText(getApplicationContext(), jsonObject.getString("sucess"), Toast.LENGTH_LONG).show();

                                startActivity(new Intent(getApplicationContext(), MenuChefActivity.class));

                            } else {

                                Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("prato", Prato.getText().toString());
                        hashMap.put("quantidade", Qtd_prato.getText().toString());
                        hashMap.put("inicio", inicio.getText().toString());
                        hashMap.put("final", fim.getText().toString());
                        hashMap.put("valor", valor.getText().toString());
                        hashMap.put("id", Id);

                        return hashMap;

                    }
                };

                requestQueue.add(request);
            }

        });
    }


    private void alert(String s){

        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
