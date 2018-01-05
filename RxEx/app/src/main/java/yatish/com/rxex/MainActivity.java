package yatish.com.rxex;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import rx.Observable;
import rx.Observer;

import rx.Subscriber;
import yatish.com.rxex.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Observable<String> myObservable;
    Observer<String> myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activity 1");
        createObservableAndObserver();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myObservable.subscribe(myObserver);
            }
        });

    }

    private void createObservableAndObserver(){

        myObservable = Observable.create(
                new Observable.OnSubscribe<String>(){
                    @Override
                    public void call(Subscriber<? super String> subscriber) {

                        subscriber.onNext(binding.editText.getText().toString());
                        subscriber.onCompleted();

                    }
                }
        );

        myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

                binding.textView.setText(s);

            }
        };


    }
}
