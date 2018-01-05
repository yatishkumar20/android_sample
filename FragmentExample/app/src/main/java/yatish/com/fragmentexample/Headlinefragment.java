package yatish.com.fragmentexample;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by yatish on 27/11/17.
 */

public class Headlinefragment extends ListFragment {

    OnHeadlineSelectedListner mCallBack;

    public interface OnHeadlineSelectedListner{
        public void onArticleSelected(int position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines ));

    }

    @Override
    public void onStart() {
        super.onStart();
        if(getFragmentManager().findFragmentById(R.id.articlr_fragment) != null){

            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallBack = (OnHeadlineSelectedListner) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        mCallBack.onArticleSelected(position);

        getListView().setItemChecked(position, true);

    }
}
