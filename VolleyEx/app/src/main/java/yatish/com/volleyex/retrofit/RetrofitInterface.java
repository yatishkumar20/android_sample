package yatish.com.volleyex.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import yatish.com.volleyex.retrofit.Hero;

/**
 * Created by yatish on 29/11/17.
 */

public interface RetrofitInterface {
    @GET("marvel")
    Call<List<Hero>> getHeros();
}
