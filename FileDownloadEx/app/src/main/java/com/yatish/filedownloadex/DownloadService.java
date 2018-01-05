package com.yatish.filedownloadex;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yatish on 21/12/17.
 */

public class DownloadService extends IntentService {

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;

    public DownloadService() {
        super("Download Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.e("Yatish","ppppppppppp");

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Download")
                .setContentText("Downloading File")
                .setAutoCancel(true);

        notificationManager.notify(0,notificationBuilder.build());

        initDownload();

    }

    public void initDownload(){

        RetrofitInterfce retrofitInterfce = RetrofitClient.getCleint().create(RetrofitInterfce.class);

        Call<ResponseBody> request = retrofitInterfce.downloadFile();

        try{
            downloadFile(request.execute().body());
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void downloadFile(ResponseBody responseBody) throws IOException{

        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = responseBody.contentLength();
        InputStream inputStream = new BufferedInputStream(responseBody.byteStream(), 1024*8);
        File outPutFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"file.zip");
        OutputStream outputStream = new FileOutputStream(outPutFile);

        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;

        while((count = inputStream.read(data)) != -1){

            total += count;
            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
            double current = Math.round(total / (Math.pow(1024, 2)));

            int progress = (int) ((total * 100) / fileSize);
            long currentTime = System.currentTimeMillis() - startTime;

            Download download = new Download();
            download.setTotalFileSize(totalFileSize);

            if(currentTime > 1000 * timeCount){

                download.setCurrentFileSize((int) current);
                download.setProgress(progress);
                sendNotification(download);
                timeCount++;

            }

            outputStream.write(data, 0, count);

        }

        onDownloadComplete();
        outputStream.flush();
        outputStream.close();
        inputStream.close();


    }

    private void sendNotification(Download download){
        sendIntent(download);
        notificationBuilder.setProgress(100, download.getProgress(),false);
        notificationBuilder.setContentText("Downloading file "+ download.getCurrentFileSize() +"/"+totalFileSize +" MB");
        notificationManager.notify(0,notificationBuilder.build());

    }

    private void onDownloadComplete(){
        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0,0,false);
        notificationBuilder.setContentText("File Downloaded");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(Download download){
        Intent intent = new Intent(MainActivity.MESSAGE_PROGRESS);
        intent.putExtra("download", download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }
}
