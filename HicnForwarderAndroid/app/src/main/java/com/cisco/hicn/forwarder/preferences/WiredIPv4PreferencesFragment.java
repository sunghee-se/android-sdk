package com.cisco.hicn.forwarder.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.cisco.hicn.forwarder.R;

public class WiredIPv4PreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.wired_ipv4, rootKey);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        getPreferenceScreen().findPreference(getString(R.string.wired_source_port_ipv4_size_key)).setEnabled(sharedPreferences.getBoolean(getString(R.string.enable_nexthop_ipv4_key), false));
        getPreferenceScreen().findPreference(getString(R.string.wired_nexthop_ipv4_key)).setEnabled(sharedPreferences.getBoolean(getString(R.string.enable_nexthop_ipv4_key), false));
        getPreferenceScreen().findPreference(getString(R.string.wired_nexthop_port_ipv4_key)).setEnabled(sharedPreferences.getBoolean(getString(R.string.enable_nexthop_ipv4_key), false));



        getPreferenceScreen().findPreference(getString(R.string.wired_source_port_ipv4_size_key)).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //
                Log.d("facemgr", "newvalue = " + newValue);
                return false;
            }
        });
    }
}
