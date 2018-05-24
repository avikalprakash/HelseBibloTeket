package lue.attendance.com.helsebibloteket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;



/**
 * Created by lue on 11-09-2017.
 */

public class Login extends AppCompatActivity {

    EditText musernme,mpassword;
  //  UserSessionManager session;
    String email;
    String user_name;
    String mobile;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button btnlogin=(Button)findViewById(R.id.btnlogin);
       // session = new UserSessionManager(getApplicationContext());
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SaleasLogin().execute();

            }
        });

        musernme = (EditText) findViewById(R.id.inputusername);
        mpassword = (EditText) findViewById(R.id.editText);

    }

    class SaleasLogin extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        String username = musernme.getText().toString().trim();
        String password = mpassword.getText().toString().trim();



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://gulfroof.com/api/sales-login");
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("user_name", username);
                jsonObject.accumulate("password", password);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readadsResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            try {
                JSONObject objone = new JSONObject(json);
                boolean check  = objone.getBoolean("error");
                if(check) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setMessage("Please Try Again")
                            .setNegativeButton("ok", null)
                            .create()
                            .show();
                }else{
                    JSONArray jsonArray = objone.getJSONArray("message");
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getString("id");
                        user_name = jsonObject.getString("user_name");
                        mobile = jsonObject.getString("mobile");
                        email = jsonObject.getString("email");


                    }
                   // Intent i=new Intent(Login.this,Home.class);
                   // session.createUserLoginSession(username);
                    SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = pref.edit();
                    editor2.putString("seller_email_id", email);
                    editor2.putString("id", id);
                    editor2.putString("seller_name", user_name);
                    editor2.putString("seller_contact", mobile);
                    editor2.commit();
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                  //  startActivity(new Intent(Login.this,Home.class));
                   // finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private String readadsResponse(HttpResponse httpResponse) {

        InputStream is = null;
        String return_text = "";
        try {
            is = httpResponse.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return_text = sb.toString();
            Log.d("return1230", "" + return_text);
        } catch (Exception e) {

        }
        return return_text;
    }


}
