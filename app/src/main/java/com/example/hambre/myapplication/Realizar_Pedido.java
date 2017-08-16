package com.example.hambre.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Realizar_Pedido extends AppCompatActivity {

    private static final String PREF_NAME = "MainActivityPreferences";
    private TextView total;
    private double num_p,Valor,Total;
    private String Nome,valor,resultado,quantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar__pedido);

        SharedPreferences sp;
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
         Nome = sp.getString("Nome","");
         valor = sp.getString("Valor","");
         quantidade = sp.getString("Porçao","");

        total = (TextView) findViewById(R.id.textView5);


       if (!quantidade.isEmpty()) {

           num_p = Double.parseDouble(quantidade);
           Valor = Double.parseDouble(valor);
           Total = num_p * Valor;
           resultado = String.valueOf(Total);
           total.setText(resultado);

       }




    }
}
