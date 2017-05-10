package com.example.hambre.myapplication;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class UsuarioOAD {

    private static final String  URL = "http://192.168.2.5:8080/ProjetoWS/services/UsuarioOAD?wsdl";
    private static final String  namespace = "http://webservice.hambre.com.br/";
    private static final String  SOAPACTION ="inserirUsuario";

    private static final String  INSERIR ="inserirUsuario";
    private static final String  BUSCAR ="buscarUsuario";
    private static final String  EXCLUIR ="excluirUsuario";


        public boolean inserirUsuario(Usuario usuario){

            SoapObject inserirUsuario = new SoapObject(namespace,INSERIR);
            SoapObject user = new SoapObject(namespace,"usuario");

            user.addProperty("codigo",usuario.getCodigo());
            user.addProperty("email",usuario.getEmail());
            user.addProperty("senha",usuario.getSenha());

            inserirUsuario.addSoapObject(user);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(inserirUsuario);
            envelope.implicitTypes = true;
            envelope.dotNet = false;
            envelope.setAddAdornments(false);

            HttpTransportSE http = new HttpTransportSE(URL);
            http.debug = true;

            try {

                http.call("urn:inserirUsuario",envelope);

                SoapPrimitive resposta = (SoapPrimitive)envelope.getResponse();

                 return Boolean.parseBoolean(resposta.toString());

              } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }

      /*  public boolean excluirUsuario(int codigo){

            Usuario usuario = new Usuario();


            return true;
        }

        public Usuario buscarUsuario(int codigo){

            Usuario user = new Usuario();



            return user;
        }*/

    }


