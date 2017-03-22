package com.example.hambre.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import java.util.Arrays;

public class EntradaActivity extends AppCompatActivity {

    TextView textStatus;
    LoginButton login_Button;
    CallbackManager callbackManager;

    protected void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_entrada);
        initializeControls();
        loginwithFB();

    }


    private void initializeControls(){
        textStatus = (TextView)findViewById(R.id.textStatus);
        login_Button = (LoginButton)findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
    }

    private void loginwithFB(){

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                textStatus.setText("Login Sucess\n"+loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                textStatus.setText("Login Cancelled.");

            }

            @Override
            public void onError(FacebookException error) {
                textStatus.setText("Login Error: "+error.getMessage());

            }
        });

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
              callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    };






















































































