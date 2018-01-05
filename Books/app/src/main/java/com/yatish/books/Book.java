package com.yatish.books;

/**
 * Created by yatish on 13/5/17.
 */

public class Book {

    private String mAuthor;

    private String mTitle;

    public Book(String author, String title){

        mAuthor = author;
        mTitle = title;

    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmTitle() {
        return mTitle;
    }


}
