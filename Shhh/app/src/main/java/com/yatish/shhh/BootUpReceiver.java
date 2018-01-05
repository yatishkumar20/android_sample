package com.yatish.shhh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

/**
 * Created by yatish on 11/12/17.
 */

public class BootUpReceiver extends BroadcastReceiver {

    private Geofencing mGeofencing;
    public GoogleApiClient mClient;

    @Override
    public void onReceive(Context context, Intent intent) {

        mClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();

        mGeofencing = new Geofencing(context, mClient);

        mGeofencing.registerAllGeofences();


    }
}
