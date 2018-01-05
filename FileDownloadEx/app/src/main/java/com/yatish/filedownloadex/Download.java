package com.yatish.filedownloadex;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yatish on 21/12/17.
 */

public class Download implements Parcelable{

    private int progress;
    private int currentFileSize;
    private int totalFileSize;

    public Download(){

    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(int currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public int getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(int totalFileSize) {
        this.totalFileSize = totalFileSize;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(progress);
        dest.writeInt(currentFileSize);
        dest.writeInt(totalFileSize);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Download(Parcel source){

        progress = source.readInt();
        currentFileSize = source.readInt();
        totalFileSize = source.readInt();
    }

    public static final Parcelable.Creator<Download> CREATOR = new Creator<Download>() {
        @Override
        public Download createFromParcel(Parcel source) {
            return new Download(source);
        }

        @Override
        public Download[] newArray(int size) {
            return new Download[size];
        }
    };
}
