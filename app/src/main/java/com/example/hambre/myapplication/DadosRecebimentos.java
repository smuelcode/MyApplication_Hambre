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

class DadosRecebimentos extends AppCompatActivity {

    private EditText nome,num_conta,nom_banco,agencia,cpf;
    private Button btenviar;
    private static final String URL = "http://tellunar.com.br/enviar_dados.php";
    private StringRequest request;
    private RequestQueue requestQueue;
    private static final String PREF_NAME = "LoginChefPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_recebimento);

        SharedPreferences sp;
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        final String Id = sp.getString("ID","");


        requestQueue = Volley.newRequestQueue(this);

        btenviar = (Button) findViewById(R.id.dados);
        btenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nome = (EditText) findViewById(R.id.Nom_titular);
                num_conta = (EditText) findViewById(R.id.Num_conta);
                nom_banco = (EditText) findViewById(R.id.Nom_banco);
                agencia = (EditText) findViewById(R.id.agencia);
                cpf = (EditText) findViewById(R.id.cpf);


                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(nome.getText().toString().isEmpty() || num_conta.getText().toString().isEmpty() ||
                                    nom_banco.getText().toString().isEmpty() || agencia.getText().toString().isEmpty() || cpf.getText().toString().isEmpty()){

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
                        hashMap.put("nome", nome.getText().toString());
                        hashMap.put("num_conta", num_conta.getText().toString());
                        hashMap.put("nom_banco", nom_banco.getText().toString());
                        hashMap.put("agencia", agencia.getText().toString());
                        hashMap.put("cpf", cpf.getText().toString());
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

