package com.yatish.books;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yatish on 13/5/17.
 */

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils(){

    }

    public static List<Book> fetchBooksData(String requestUrl){

        URL url = createURL(requestUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Book> bookList = extractDataFromJson(jsonResponse);

        return bookList;
    }

    private static URL createURL(String stringUrl){

        URL url = null;

        try{

            url = new URL(stringUrl);

        }catch (MalformedURLException e){

            Log.e(LOG_TAG , "Problem building the URL ", e);
        }

        return url;

    }

    private static String makeHttpRequest(URL url) throws IOException{

        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        }catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the books JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line  = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

    private static List<Book> extractDataFromJson(String booksJSON){

        if (TextUtils.isEmpty(booksJSON)){
            return null;
        }

        List<Book> books = new ArrayList<>();

        try {

            JSONObject baseJsonObject = new JSONObject(booksJSON);

            JSONArray booksArray = baseJsonObject.getJSONArray("items");

            for (int i=0;i<booksArray.length();i++) {

                JSONObject currentBook = booksArray.getJSONObject(i);

                JSONObject bookVolInfo = currentBook.getJSONObject("volumeInfo");

                String title = bookVolInfo.getString("title");

                boolean authors = bookVolInfo.has("authors");

                String author = "";

                if(authors){

                    JSONArray authorsOfBook = bookVolInfo.getJSONArray("authors");

                    if (authorsOfBook.length() > 0) {
                        for (int j = 0; j < authorsOfBook.length(); j++) {
                            author = author +" "+ authorsOfBook.getString(j);
                        }

                    }

                }

                Book book = new Book(title, author);

                books.add(book);



            }

        }catch (JSONException e){

            Log.e(LOG_TAG, "Problem parsing the books JSON results", e);
        }

        return books;
    }




}
