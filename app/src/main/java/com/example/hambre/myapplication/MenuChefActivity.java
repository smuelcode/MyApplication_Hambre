package com.example.hambre.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuChefActivity extends AppCompatActivity {

    private Button btanunciar,btpedidos,
            btdados,btinicio,btcadastrar;
    private static final String PREF_NAME = "LoginChefPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chef);

        btanunciar = (Button)findViewById(R.id.Anun_prato);
        btanunciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Anunciar_prato.class));

            }
        });

        btpedidos = (Button)findViewById(R.id.pedidos);
        btpedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Pedidos.class));

            }
        });

        btdados = (Button)findViewById(R.id.dados_recebimento);
        btdados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),DadosRecebimentos.class));

            }
        });

        btinicio = (Button)findViewById(R.id.inicio);
        btinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear( ).apply();

                startActivity(new Intent(getApplicationContext(),Telainicial.class));
            }
        });

        btcadastrar = (Button)findViewById(R.id.cd_cozinha);
        btcadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),QueroserumChef.class));
            }
        });


    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
