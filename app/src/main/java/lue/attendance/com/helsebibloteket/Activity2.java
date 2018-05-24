package lue.attendance.com.helsebibloteket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

public class Activity2 extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String a="";
    public  static final String MyPref="MyPref";
    public  static final String A="a";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        sharedPreferences=getSharedPreferences(MyPref, this.MODE_PRIVATE);
        a=sharedPreferences.getString(A, "");
        Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
        new Example().execute();
    }

    class Example extends AsyncTask<String, Void, String>{
        String username="avikal";
        String password="12345";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Activity2.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
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
                s = readResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                boolean b  = jsonObject.getBoolean(s);
                if (b){
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                }else {
                    JSONArray jsonArray = jsonObject.getJSONArray(s);
                    for (int i=0;i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                     String   id = jsonObject.getString("id");
                     String   user_name = jsonObject.getString("user_name");
                     String   mobile = jsonObject.getString("mobile");
                     String  email = jsonObject.getString("email");
                     Toast.makeText(getApplicationContext(), id+", "+user_name+", "+mobile+", "+email, Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    private String readResponse(HttpResponse httpResponse) {

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
