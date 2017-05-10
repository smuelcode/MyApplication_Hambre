package com.example.hambre.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Cadastro extends AppCompatActivity {

    Usuario usuario = new Usuario();
    private EditText email1,senha1,senha2;
    private Button btncadastro;
    int a = 10;
    UsuarioOAD us = new UsuarioOAD();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        email1 = (EditText)findViewById(R.id.email);
        senha1 = (EditText)findViewById(R.id.senha1);
        senha2 = (EditText)findViewById(R.id.senha2);
        btncadastro =(Button)findViewById(R.id.cadastro);

        btncadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Email1 = email1.getText().toString();
                String Senha1 = senha1.getText().toString();
                String Senha2 = senha2.getText().toString();

                if (Email1.isEmpty()|| Senha1.isEmpty() || Senha2.isEmpty()) {

                    alert("Falta algum campo para ser preenchido");

                }else if(Senha1.equals(Senha2)) {

                    usuario.setEmail(Email1);
                    usuario.setSenha(Senha2);
                    usuario.setCodigo(a);
                   boolean resultado = us.inserirUsuario(usuario);
                    Log.d("SERVICE",resultado +"");
                    alert("Cadastro realizado com sucesso");
                    a=+1;
                    Intent intent = new Intent(Cadastro.this,Main2Usuario.class);
                     startActivity(intent);

                }else {

                    alert("Insira outra senha.");
                }


            }
        });
    }
    private void alert(String s){

        Toast.makeText(this,s,Toast.LENGTH_LONG).show();


    }
}
