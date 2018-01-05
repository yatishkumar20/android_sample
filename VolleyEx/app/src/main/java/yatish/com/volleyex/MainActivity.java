package yatish.com.volleyex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//import com.android.volley.Request;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import yatish.com.volleyex.customview.CustomView;
import yatish.com.volleyex.retrofit.RetrofitEx;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    String Req_tag = "json_request";
    EditText editText;
    ProgressDialog dialog;
    MyAsyncTask myAsyncTask;
    DatePicker datePicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);

        /*Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add( Calendar.MONTH, -6 ); // Subtract 6 months
        long minDate = c.getTime().getTime(); // Twice!

        datePicker2 = (DatePicker) findViewById(R.id.datePicker2);

        datePicker2.setMinDate(minDate);
        datePicker2.setMaxDate(System.currentTimeMillis());*/

        //mRequestQueue = Volley.newRequestQueue(this);             //This is for class
        mRequestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();    //Using single ton request

        final ImageView mImageView = (ImageView) findViewById(R.id.imageView);

        ImageRequest imageRequest = new ImageRequest("https://i.imgur.com/Nwk25LA.jpg",
                new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                mImageView.setImageBitmap(response);
            }
            } ,0,0,
                ImageView.ScaleType.FIT_XY,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(imageRequest);
       /* myAsyncTask = new MyAsyncTask(this);
        myAsyncTask.execute();*/

    }

    public void getJSON(View v){

        /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, "http://ip.jsontest.com/", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = "Your Ip Address is: " + response.getString("ip");
                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("name", "Yatish");
                params.put("subject", "Kumar");

                return params;
            }
        };

        jsonObjectRequest.setTag(Req_tag);

        mRequestQueue.add(jsonObjectRequest);*/

        myAsyncTask = new MyAsyncTask(this);
        myAsyncTask.execute();


    }


    class MyAsyncTask extends AsyncTask<String,String,String>{
        String ip ;

        public MyAsyncTask(MainActivity mainActivity){
            dialog = new ProgressDialog(mainActivity);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d("onCancelled","Called");
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please wait.....");
            dialog.show();
        }

        @Override
        protected String doInBackground(String[] params) {

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    EditText editText = (EditText) findViewById(R.id.editText);
                    editText.setText("Hello Volley/OkHttp");
                }
            });

            try {

                Thread.sleep(5000);
                MediaType JSON
                        = MediaType.parse("application/json; charset=utf-8");

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();

                RequestBody requestBody = RequestBody.create(JSON, "");

                Request request = new Request.Builder().url("http://ip.jsontest.com/").get().build();

                okhttp3.Response response = okHttpClient.newCall(request).execute();

                String result = response.body().string();

                JSONObject res = new JSONObject(result);

                 ip = "Your Ip Address is: "+res.getString("ip");

                if(isCancelled()){
                    return null;
                }


            }catch (Exception e){
                e.printStackTrace();
            }



            return ip;
        }


        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            if(isCancelled()){
                return;
            }

            Log.d("onPostExecute","Called"+o);
                editText.setText(ip);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(MainActivity.this, ip, Toast.LENGTH_LONG).show();
            Intent retro = new Intent(MainActivity.this, RetrofitEx.class);
            startActivity(retro);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("OnDestroy","myAsyncTask ="+myAsyncTask.getStatus());
        if(myAsyncTask != null && myAsyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            myAsyncTask.cancel(true);
        }
        if(mRequestQueue != null){
            mRequestQueue.cancelAll(Req_tag);
            Log.d("OnDestroy","Called");
        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_custom_view:
                Intent cv = new Intent(MainActivity.this,CustomView.class);
                startActivity(cv);
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
