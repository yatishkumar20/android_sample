package com.yatish.ex.singaleinstancebackstack;

import android.app.Activity;

/**
 * Created by yatish on 23/8/17.
 */

public class AndroidKiller {

    static void killActivity(Activity activity){

        if(activity != null){

            activity.finish();

        }

    }
}
