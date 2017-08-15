package com.example.hambre.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class LoginChef extends AppCompatActivity {

    private static final String PREF_NAME = "LoginChefPreferences";
    private Button btentrar,bt_cadastro;
    private EditText emailchef,senhachef;
    private RequestQueue requestQueue;
    private StringRequest request;
    public  String ID;

    private static final String URL = "http://tellunar.com.br/chef_entrar.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_chef);

        SharedPreferences sp;
        sp = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        String login = sp.getString("login","");
        String Id = sp.getString("ID","");


        ///////////////////  Verificando se já estar logado  \\\\\\\\\\\\\\\\\\\\\\

        if(login.equals("logged")){

            alert("Bem vindo de volta!");
            Intent it = new Intent(this,MenuChefActivity.class);
            startActivity(it);

        }

        requestQueue = Volley.newRequestQueue(this);

        ///////////////// Login sem Facebook \\\\\\\\\\\\\\

        btentrar = (Button)findViewById(R.id.btentrar);
        btentrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                emailchef = (EditText) findViewById(R.id.emailchef);
                senhachef = (EditText) findViewById(R.id.senhachef);

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.names().get(0).equals("sucess")) {

                                SharedPreferences sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                ID = jsonObject.getString("id");
                                Log.e("Id",ID);

                                editor.putString("ID",ID);
                                editor.putString("login","logged");
                                editor.apply();


                                Toast.makeText(getApplicationContext(), jsonObject.getString("sucess"), Toast.LENGTH_LONG).show();

                                goMainScreen();

                            }

                            else {

                                alert("Email ou senha incorreto.");
                            }

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
                        hashMap.put("email",emailchef.getText().toString());
                        hashMap.put("password",senhachef.getText().toString());
                        return hashMap;

                    }
                };

                requestQueue.add(request);

            }
        });

        ///////////////////////////// Cadastrar \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        bt_cadastro = (Button)findViewById(R.id.btcdchef);
        bt_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(LoginChef.this,Cadastrochef.class);
                startActivity(it);


            }
        });


    }

    private void goMainScreen() {

        Intent intent = new Intent(this,MenuChefActivity.class);
        startActivity(intent);

    }
    private void alert(String s){

        Toast.makeText(this,s,Toast.LENGTH_LONG).show();


    }

}

