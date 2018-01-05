package com.yatish.filedownloadex;

import retrofit2.Retrofit;

/**
 * Created by yatish on 21/12/17.
 */

public class RetrofitClient {

    public static final String BASE_URL = "https://download.learn2crack.com/";
    public static Retrofit retrofit = null;

    public static Retrofit getCleint(){

        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();

        }

        return retrofit;

    }

}
