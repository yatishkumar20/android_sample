package yatish.com.rxex;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import yatish.com.rxex.databinding.ActivityMainBinding;

/**
 * Created by yatish on 5/1/18.
 */

public class MainActivity3 extends AppCompatActivity {

    ActivityMainBinding binding;
    Observable<String[]> myObservable;
    Observer<String> myObserver;
    int mCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activity 3");
        createObserver();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.button.setEnabled(false);

                Observable.from(binding.editText.getText().toString().split("\n"))
                        .subscribeOn(Schedulers.io())
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {

                                try {
                                    Thread.sleep(2000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                return mCounter++ + "."+s;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(myObserver);

            }
        });

    }

    private void createObserver(){
        myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                binding.button.setEnabled(true);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

                binding.textView.setText(binding.textView.getText().toString()+"\n"+s);

            }
        };
    }
}
