package com.example.hambre.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Telainicial extends AppCompatActivity {

    public Button btusuario;
    public Button btchef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telainicial);

        btusuario = (Button) findViewById(R.id.btuser);
        btusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        btchef = (Button) findViewById(R.id.btchef);
        btchef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),LoginChef.class));

            }
        });
    }

    public void onBackPressed(){


    }
}
