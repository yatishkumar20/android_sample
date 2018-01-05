package learning.com.learning;

/**
 * Created by yatish on 23/2/17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OneFragment extends Fragment{

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Fragment Life Cycle", "onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragment Life Cycle", "onCreate()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Fragment Life Cycle", "onActivityCreated()");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("Fragment Life Cycle", "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Fragment Life Cycle", "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Fragment Life Cycle", "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Fragment Life Cycle", "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Fragment Life Cycle", "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Fragment Life Cycle", "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Fragment Life Cycle", "onDetach()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        Log.d("Fragment Life Cycle", "onCreateView()");

        this.initView(v);
        return v;
    }

    public void initView(View v){

        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);

        List<String> one = new ArrayList<String>();
        one.add("One of One");
        one.add("One of Two");
        one.add("One of Three");
        one.add("One of Four");
        one.add("One of Five");

        List<String> two = new ArrayList<String>();
        two.add("Two of One");
        two.add("Two of Two");
        two.add("Two of Three");
        two.add("Two of Four");
        two.add("Two of Five");

        List<String> three = new ArrayList<String>();
        three.add("Three of One");
        three.add("Three of Two");
        three.add("Three of Three");
        three.add("Three of Four");
        three.add("Three of Five");

        List<String> four = new ArrayList<String>();
        four.add("Four of One");
        four.add("Four of Two");
        four.add("Four of Three");
        four.add("Four of Four");
        four.add("Four of Five");

        List<String> five = new ArrayList<String>();
        five.add("Five of One");
        five.add("Five of Two");
        five.add("Five of Three");
        five.add("Five of Four");
        five.add("Five of Five");

        List<String> titles = new ArrayList<String>();
        titles.add("One");
        titles.add("Two");
        titles.add("Three");
        titles.add("Four");
        titles.add("Five");

        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


        expandableListDetail.put("One", one);
        expandableListDetail.put("Two", two);
        expandableListDetail.put("Three", three);
        expandableListDetail.put("Four", four);
        expandableListDetail.put("Five", five);

        expandableListAdapter = new CustomExpandableListAdapter(getContext(), titles, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                Intent tabsExp = new Intent(getActivity(),TabsActivity.class);
                startActivity(tabsExp);

                return false;
            }
        });


    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

}