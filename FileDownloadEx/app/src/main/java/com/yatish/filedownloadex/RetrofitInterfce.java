package com.yatish.filedownloadex;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * Created by yatish on 21/12/17.
 */

public interface RetrofitInterfce {

    @GET("files/Node-Android-Chat.zip")
    @Streaming
    Call<ResponseBody> downloadFile();
}
