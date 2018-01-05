package yatish.com.fragmentexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by yatish on 27/11/17.
 */

public class Articlefragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    TextView article;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null){

            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        View v = inflater.inflate(R.layout.article_view, container, false);
        article = (TextView) v.findViewById(R.id.tv_article);

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if(args != null){
            updateArticleView(args.getInt(ARG_POSITION));
        }else if(mCurrentPosition != -1){
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
        article.setText(Ipsum.Articles[position]);
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ARG_POSITION,mCurrentPosition);
    }
}
