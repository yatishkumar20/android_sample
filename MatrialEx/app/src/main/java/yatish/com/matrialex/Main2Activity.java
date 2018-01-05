package yatish.com.matrialex;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class Main2Activity extends Activity {

    int count = 0;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv = findViewById(R.id.tv);

        new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"Thread1");
        new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"Thread2");

        /*new MyAsyncTask().execute("Thread1");
        new MyAsyncTask().execute("Thread2");*/
    }


    private class MyAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            tv.setText(o);
        }

        @Override
        protected String doInBackground(String[] params) {

            for(int i=0;i<20;i++) {
                count++;
                Log.e("doInBackground()", params[0]+"-"+count);
            }
            return count+"";
        }
    }
}
