package com.yatish.books;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BooksList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String LOG_TAG = BooksList.class.getName();

    private static final String BOOKS_API = "https://www.googleapis.com/books/v1/volumes?q=android";

    private static final int BOOKS_LOADER = 1;

    private BooksAdapter mAdapter;

    private TextView mEmptyStateTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        ListView bookList = (ListView) findViewById(R.id.books_list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        bookList.setEmptyView(mEmptyStateTextView);

        mAdapter = new BooksAdapter(this, new ArrayList<Book>());

        bookList.setAdapter(mAdapter);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(BOOKS_LOADER, null, this);


        }else{

            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.no_internet);

        }

        makeHttpRequestV();

    }


    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, BOOKS_API);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_books);

        mAdapter.clear();

        if(books != null && !books.isEmpty()){

            mAdapter.addAll(books);

        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

        mAdapter.clear();

    }


    private  String makeHttpRequestV()  {

        String jsonResponse = "";

        RequestQueue rq = Volley.newRequestQueue(this);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "https://www.googleapis.com/books/v1/volumes?q=android", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject baseJsonObject = response;

                            JSONArray booksArray = baseJsonObject.getJSONArray("items");
                            Log.d("Yatish123",booksArray.toString());

                        }catch (Exception e){

                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("sf","sdf");
                    }
                }
        );

        rq.add(req);


        return jsonResponse;

    }
}
