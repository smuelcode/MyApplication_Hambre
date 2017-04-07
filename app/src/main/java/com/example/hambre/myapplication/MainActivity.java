package com.example.hambre.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    private Button btncadastrar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* FacebookSdk.sdkInitialize(getApplicationContext());
         initializeControls();
         loginwithFB();*/

        btncadastrar = (Button) findViewById(R.id.cadastrar);


        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainActivity.this,Cadastro.class);
                startActivity(it);

            }
        });


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
























































































