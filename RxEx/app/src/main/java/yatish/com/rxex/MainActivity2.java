package yatish.com.rxex;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import yatish.com.rxex.databinding.ActivityMainBinding;

/**
 * Created by yatish on 5/1/18.
 */

public class MainActivity2 extends AppCompatActivity {

    ActivityMainBinding binding;
    Observable<String> myObservable;
    Observer<String> myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activity 2");
        createObservableAndObserver();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

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

                        try{
                            Thread.sleep(3000);
                        }catch (Exception e){

                        }

                        String[] strings = binding.editText.getText().toString().split("\n");
                        StringBuilder builder = new StringBuilder();

                        for (int i = 0 ;i < strings.length; i++){

                            builder.append((i+1)+"."+strings[i]+"\n");
                        }

                        subscriber.onNext(builder.toString());
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
