package yatish.com.volleyex.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yatish.com.volleyex.R;

public class RetrofitEx extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_ex);

        RetrofitInterface retrofitService = RetrofitClient.getClient().create(RetrofitInterface.class);

        Call<List<Hero>> call = retrofitService.getHeros();

        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> heros = response.body();
                for(Hero s : heros){
                    Log.d("Heros", s.getName());
                }

            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {

            }
        });

    }
}
