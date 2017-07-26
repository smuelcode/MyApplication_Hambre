package com.example.hambre.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


public class Cadastro extends AppCompatActivity {

    private static final String PREF_NAME = "MainActivityPreferences";
    private EditText email1,senha1,senha2;
    private Button btncadastro;
    private RequestQueue requestQueue;
    private static final String URL = "http://tellunar.com.br/use_control.php";
    private StringRequest request;
    public String ID;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        email1 = (EditText)findViewById(R.id.email);
        senha1 = (EditText)findViewById(R.id.senha1);
        senha2 = (EditText)findViewById(R.id.senha2);
        btncadastro =(Button)findViewById(R.id.cadastro);

        requestQueue = Volley.newRequestQueue(this);

        btncadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Email1 = email1.getText().toString();
                String Senha1 = senha1.getText().toString();
                String Senha2 = senha2.getText().toString();

                if (Email1.isEmpty()|| Senha1.isEmpty() || Senha2.isEmpty()) {

                    alert("Falta algum campo para ser preenchido");

                }else if(Senha1.equals(Senha2)) {

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

                                    Toast.makeText(getApplicationContext(),jsonObject.getString("sucess"), Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(getApplicationContext(), Main2Activity.class));

                                } else {

                                    Toast.makeText(getApplicationContext(),jsonObject.getString("error"), Toast.LENGTH_LONG).show();
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
                            hashMap.put("email",email1.getText().toString());
                            hashMap.put("password",senha1.getText().toString());
                            return hashMap;

                        }
                    };
                    requestQueue.add(request);


                }else {

                    alert("Senhas desiguais,tente novamente.");
                }


            }
        });
    }



    private void alert(String s){

        Toast.makeText(this,s,Toast.LENGTH_LONG).show();


    }
}
