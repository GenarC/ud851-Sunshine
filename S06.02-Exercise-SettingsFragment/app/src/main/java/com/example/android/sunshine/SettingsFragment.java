package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by cm_gn on 2/2/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_screen);

        PreferenceScreen ps = getPreferenceScreen();
        SharedPreferences sp = ps.getSharedPreferences();

        int size = ps.getPreferenceCount();

        for(int i = 0; i < size; i++){
            Preference p = ps.getPreference(i);

            if(!(p instanceof CheckBoxPreference)){
                setPreferenceSummary(p,sp.getString(p.getKey(),""));
            }
        }
    }

    private void setPreferenceSummary(Preference p, Object obj){
        String value = obj.toString();
        if (p instanceof ListPreference) {
            // For list preferences, figure out the label of the selected value
            ListPreference listPreference = (ListPreference) p;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                // Set the summary to that label
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else if (p instanceof EditTextPreference) {
            // For EditTextPreferences, set the summary to the value's simple string representation.
            p.setSummary(value);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference p = findPreference(key);

        if(!(p instanceof CheckBoxPreference)){
            setPreferenceSummary(p,sharedPreferences.getString(p.getKey(),""));
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        getPreferenceScreen()
                .getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        getPreferenceScreen()
                .getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
