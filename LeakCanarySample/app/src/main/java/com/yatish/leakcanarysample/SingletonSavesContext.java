package com.yatish.leakcanarysample;

import android.content.Context;

/**
 * Created by yatish on 27/9/17.
 */

public class SingletonSavesContext {
    private Context context;
    private static SingletonSavesContext instance;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static SingletonSavesContext getInstance() {
        if (instance == null) {
            instance = new SingletonSavesContext();
        }
        return instance;
    }
}
