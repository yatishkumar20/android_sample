package com.yatish.books;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yatish on 13/5/17.
 */

public class BooksAdapter extends ArrayAdapter<Book> {


    public BooksAdapter(Context context, List<Book> books){

        super(context,0,books);

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list,parent,false);
        }

        Book book = getItem(position);

        TextView author = (TextView) listItemView.findViewById(R.id.tv_author);

        author.setText(book.getmAuthor());

        TextView title = (TextView) listItemView.findViewById(R.id.tv_title);

        title.setText(book.getmTitle());

        return listItemView;

    }
}
