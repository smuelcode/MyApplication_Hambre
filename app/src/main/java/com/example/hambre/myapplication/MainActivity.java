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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.LoginButton;

import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;



public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME = "MainActivityPreferences";

    private Button btncadastrar;
    private Button btnlogin;
    private EditText email, senha;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private RequestQueue requestQueue;
    private static final String url = "http://tellunar.com.br/use_control2.php";
    private StringRequest request;
    public  String ID;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        SharedPreferences sp;
        sp = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        String login = sp.getString("login","");


        ///////////////////  Verificando se já estar logado  \\\\\\\\\\\\\\\\\\\\\\

        if(login.equals("logged")){

            alert("Bem vindo de volta!");
            Intent it = new Intent(MainActivity.this,Main2Activity.class);
            startActivity(it);

        }

        callbackManager = CallbackManager.Factory.create();

       ////////////////////////////////// Login com Facebook \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                 goMainScreen();
                 alert(" Bem vindo ");
            }


            @Override
            public void onCancel() {

                alert(" Cancelado.");

            }

            @Override
            public void onError(FacebookException error) {

                alert("Houve algum problema.");

            }

        });


        requestQueue = Volley.newRequestQueue(this);

     //////////////////////////////////// Login sem o Facebook \\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        btnlogin = (Button)findViewById(R.id.button);
        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                email = (EditText) findViewById(R.id.email);
                senha = (EditText) findViewById(R.id.senha);

                 request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {

                         try {

                             JSONObject jsonObject = new JSONObject(response);

                             if (jsonObject.names().get(0).equals("sucess")) {

                                 SharedPreferences sp = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
                                 SharedPreferences.Editor editor = sp.edit();
                                 ID = jsonObject.getString("id");
                                 Log.e("Id",ID);

                                 editor.putString("ID",ID);
                                 editor.putString("login","logged");
                                 editor.apply();


                                 Toast.makeText(getApplicationContext(), jsonObject.getString("sucess"), Toast.LENGTH_LONG).show();

                                 goMainScreen();

                             } else {

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
                   protected Map<String,String> getParams() throws AuthFailureError{

                       HashMap<String,String> hashMap = new HashMap<String, String>();
                       hashMap.put("email",email.getText().toString());
                       hashMap.put("password",senha.getText().toString());
                       return hashMap;

                   }
                 };
                 requestQueue.add(request);

            }
        });

        ///////////////////////////// Cadastrar \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        btncadastrar = (Button)findViewById(R.id.cadastrar);
        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainActivity.this,Cadastro.class);
                startActivity(it);


            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

    }


    private void goMainScreen() {

        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);

    }
    private void alert(String s){

        Toast.makeText(this,s,Toast.LENGTH_LONG).show();


    }

}























































































