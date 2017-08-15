package com.example.hambre.myapplication;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import java.util.HashMap;
        import java.util.Map;

class QueroserumChef extends AppCompatActivity {

    public Button btncozinha;
    public String lat,lng;
    public EditText Nome,Endereço;
    private RequestQueue requestQueue;
    private static final String URL = "http://tellunar.com.br/control_cozinha.php";
    private StringRequest request;
    private static final String PREF_NAME = "LoginChefPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queroserchef);

        Nome = (EditText) findViewById(R.id.Nome);
        Endereço = (EditText) findViewById(R.id.Endereço);

        requestQueue = Volley.newRequestQueue(this);

        btncozinha = (Button) findViewById(R.id.cozinha);
        btncozinha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String Nome1 = Nome.getText().toString();
                String Endereço1 = Endereço.getText().toString();


                new GetCoordinates().execute(Endereço1.replace(" ","+"));


            }
        });
    }

    private class GetCoordinates extends AsyncTask<String,Void,String>{

        ProgressDialog dialog = new ProgressDialog(QueroserumChef.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String response;
            try {

                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s",address);
                response = http.getHttpData(url);
                return response;

            }catch (Exception ex){

            }
            return null;
        }


        protected void onPostExecute(String s) {

            try {

                JSONObject jsonObject = new JSONObject(s);

                lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();


                if(dialog.isShowing())

                    dialog.dismiss();

            }catch(JSONException e){

                e.printStackTrace();

            }

            request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.names().get(0).equals("sucess")) {

                            Toast.makeText(getApplicationContext(),jsonObject.getString("sucess"), Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getApplicationContext(), MenuChefActivity.class));

                        } else {

                            Toast.makeText(getApplicationContext(),jsonObject.getString("  Erro "), Toast.LENGTH_LONG).show();
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
                protected Map<String,String> getParams() throws AuthFailureError {

                    SharedPreferences sp;
                    sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                    String Id = sp.getString("ID","");

                    HashMap<String,String> hashMap = new HashMap<String, String>();
                    hashMap.put("cozinheiro",Nome.getText().toString());
                    hashMap.put("latitude",lat);
                    hashMap.put("longitude",lng);
                    hashMap.put("id", Id);

                    return hashMap;

                }
            };
            requestQueue.add(request);

        }
    }

}






