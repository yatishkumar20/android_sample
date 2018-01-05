package com.yatish.shhh;


import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;


/**
 * Created by yatish on 16/11/17.
 */

public class SettingsFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    OnHeadlineSelectedListener mCallback;

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(boolean enable);
    }

    public SwitchPreference testPref;

    private SharedPreferences sh;

    public static GoogleApiClient mClient;
    private Geofencing mGeofencing;

    public static final String TAG = SettingsActivity.class.getSimpleName();

    private void setPreferenceSummary(Preference preference, Object value) {
        String stringValue = value.toString();
        String key = preference.getKey();

        if (preference instanceof ListPreference) {
            /* For list preferences, look up the correct display value in */
            /* the preference's 'entries' list (since they have separate labels/values). */
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Add 'general' preferences, defined in the XML file */
        addPreferencesFromResource(R.xml.pref_general);

        testPref = (SwitchPreference) findPreference("geo_fence");

        mCallback = (OnHeadlineSelectedListener) getActivity();

       SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            if (!(p instanceof SwitchPreference)) {
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        /* Unregister the preference change listener */
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        /* Register the preference change listener */
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean test = preferences.getBoolean("geo_fence", false);

        if (test) {
            testPref.setSummary("Enabled");
        } else {
            testPref.setSummary("Disabled");
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        sh = getActivity().getSharedPreferences("com.yatish.shhh",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        if (null != preference) {
            if ((preference instanceof ListPreference)) {
                editor.putString("sound", "");
                editor.commit();

                setPreferenceSummary(preference, sharedPreferences.getString(key, ""));

                editor.putString("sound", sharedPreferences.getString(key, ""));
                editor.commit();
            }

            if((preference instanceof SwitchPreference)) {
                boolean test = sharedPreferences.getBoolean("geo_fence", false);
                Log.e(TAG,test+"");
                mCallback.onArticleSelected(test);

                if (test) {
                    testPref.setSummary("Enabled");
                } else {
                    testPref.setSummary("Disabled");
                }
            }
        }
    }

}