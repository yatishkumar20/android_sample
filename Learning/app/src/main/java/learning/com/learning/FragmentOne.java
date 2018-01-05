package learning.com.learning;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by yatish on 10/11/17.
 */

public class FragmentOne extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Fragment Life Cycle1", "onCreateView()");
        View v = inflater.inflate(R.layout.fragment_o, container, false);
        initView(v);
        return v;

    }

    public void initView(View v){

        Button click = (Button) v.findViewById(R.id.click);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTwo fragmentTwo = new FragmentTwo();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentTwo)
                        .addToBackStack(null)
                        .commit();

            }
        });



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Fragment Life Cycle1", "onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragment Life Cycle1", "onCreate()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Fragment Life Cycle1", "onActivityCreated()");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("Fragment Life Cycle1", "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Fragment Life Cycle1", "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Fragment Life Cycle1", "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Fragment Life Cycle1", "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Fragment Life Cycle1", "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Fragment Life Cycle1", "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Fragment Life Cycle1", "onDetach()");
    }
}
