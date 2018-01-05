package learning.com.learning;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import learning.com.learning.R;
import learning.com.learning.TabsActivity;

/**
 * Created by yatish on 7/3/17.
 */

public class Feb extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_january, container, false);


        Log.d("Current Position", TabsActivity.tabIndex+"");

        return v;
    }


}
