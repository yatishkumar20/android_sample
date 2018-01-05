package yatish.com.rxex;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import yatish.com.rxex.databinding.ActivityMain4Binding;
import yatish.com.rxex.databinding.ActivityMainBinding;

public class Main4Activity extends AppCompatActivity {

    ActivityMain4Binding binding;
    Observer<String> myObserver;
    Subscription subscription;
    int mCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        setTitle("Activity 4");
        createObserver();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main4);

        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.startButton.setEnabled(false);
                binding.stopButton.setEnabled(true);

                subscription = rx.Observable.from(binding.editText.getText().toString().split("\n"))
                        .subscribeOn(Schedulers.io())
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {

                                try{
                                    Thread.sleep(2000);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                if(binding.errorToggle.isChecked())
                                mCount = 2/0;

                                return mCount++ +"."+s;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(myObserver);

            }
        });

        binding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subscription.unsubscribe();

                binding.startButton.setEnabled(true);
                binding.stopButton.setEnabled(false);

            }
        });






    }


    private void createObserver(){


        myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                binding.startButton.setEnabled(true);
                binding.stopButton.setEnabled(false);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(Main4Activity.this,
                        "A \"" + e.getMessage() + "\" Error has been caught",
                        Toast.LENGTH_LONG).show();
                binding.startButton.setEnabled(true);
                binding.stopButton.setEnabled(false);
            }

            @Override
            public void onNext(String s) {

                binding.textView.setText(binding.textView.getText().toString() +"\n"+ s );

            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }
}
