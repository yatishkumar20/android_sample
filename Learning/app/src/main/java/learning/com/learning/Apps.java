package learning.com.learning;

import android.graphics.drawable.Drawable;

/**
 * Created by yatish on 15/6/17.
 */

public class Apps {

    String appName;
    Drawable appIcon;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }


    public Apps(String appName,Drawable appIcon){

        this.appName = appName;
        this.appIcon = appIcon;

    }



}
