package rojgarsamachar.india.in;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {

    WebView myWebView;
    ProgressBar progressBar;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);


       /* myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);*/

        if(android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP){

            Thread thread = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(3000);
                                progressBar.setVisibility(ProgressBar.GONE);
                            } catch (InterruptedException e) {
                                return;
                            }

                        }
                    }

            );
            thread.start();

            myWebView.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            });


        }else{
            myWebView.setWebViewClient(new WebViewClient(){



                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    myWebView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onPageCommitVisible(WebView view, String url) {
                    super.onPageCommitVisible(view, url);
                    progressBar.setVisibility(ProgressBar.GONE);
                    myWebView.setVisibility(View.VISIBLE);

                }

            });


        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {

            myWebView.loadUrl("http:/www.rojgarsamachar.ind.in/");

        }else{

            progressBar.setVisibility(ProgressBar.INVISIBLE);
            mEmptyStateTextView.setText(R.string.no_internet);
            Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

}


