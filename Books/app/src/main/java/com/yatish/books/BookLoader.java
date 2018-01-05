package com.yatish.books;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by yatish on 13/5/17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String mURL;

    public BookLoader(Context context, String url){

        super(context);

        mURL = url;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {

        if(mURL == null){
            return null;
        }

        List<Book> bookList = QueryUtils.fetchBooksData(mURL);

        return bookList;

    }
}
