package com.example.hambre.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Button btncadastrar;
    private Button btnlogin;
    private EditText email, senha;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* FacebookSdk.sdkInitialize(getApplicationContext());
         initializeControls();
         loginwithFB();*/

        btnlogin = (Button)findViewById(R.id.button);
        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                email = (EditText) findViewById(R.id.email);
                senha = (EditText) findViewById(R.id.senha);

                 String Email = email.getText().toString();
                 String Senha = senha.getText().toString();

                if(Email.equals("samuel")&& Senha.equals("sasa2009")){

                    alert("Login realizado com sucesso.");

                    Intent intent = new Intent(MainActivity.this,Main2Usuario.class);
                    startActivity(intent);
                    
                }else {

                    alert("Email ou senha incorreto.");
                }
            }
        });


        btncadastrar = (Button)findViewById(R.id.cadastrar);
        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainActivity.this,Cadastro.class);
                startActivity(it);


            }
        });


    }
    private void alert(String s){

        Toast.makeText(this,s,Toast.LENGTH_LONG).show();


    }

}





    /*TextView textStatus;
    LoginButton login_Button;
    CallbackManager callbackManager;

    private void initializeControls(){
        textStatus = (TextView)findViewById(R.id.textStatus);
        login_Button = (LoginButton)findViewById(R.id.fbLogin);
        callbackManager = CallbackManager.Factory.create();
    }

    private void loginwithFB() {

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                textStatus.setText("Login Sucess\n" + loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                textStatus.setText("Login Cancelled.");

            }

            @Override
            public void onError(FacebookException error) {
                textStatus.setText("Login Error: " + error.getMessage());

            }
        });
    }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }*/
























































































