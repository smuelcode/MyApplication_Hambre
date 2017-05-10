package com.example.hambre.myapplication;


    public class Usuario {

        private int codigo;
        private String Email;
        private String Senha;

        public Usuario(){

        }
        public Usuario(int codigo, String email, String senha) {

            this.codigo = codigo;
            Email = email;
            Senha = senha;
        }
        public int getCodigo() {
            return codigo;
        }
        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }
        public String getEmail() {
            return Email;
        }
        public void setEmail(String email) {
            this.Email = email;
        }
        public String getSenha() {
            return Senha;
        }
        public void setSenha(String senha) {
            this.Senha = senha;
        }


    }




