/*
 * Copyright (c) 2019 Cisco and/or its affiliates.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cisco.hicn.forwarder.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.preference.PreferenceFragmentCompat;

import com.cisco.hicn.forwarder.R;
import com.cisco.hicn.forwarder.supportlibrary.Facemgr;
import com.cisco.hicn.forwarder.utility.NetdeviceTypeEnum;

public class WifiPreferencesFragment extends PreferenceFragmentCompat {
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.wifi, rootKey);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (sharedPreferences.getBoolean(getString(R.string.enable_wifi_key), false)) {
            getPreferenceScreen().findPreference(getString(R.string.enable_wifi_key)).setSummary(getString(R.string.enabled));
            getPreferenceManager().findPreference((getString(R.string.wifi_ipv4_preferences_key))).setEnabled(true);
            getPreferenceManager().findPreference((getString(R.string.wifi_ipv6_preferences_key))).setEnabled(true);
        } else {
            getPreferenceScreen().findPreference(getString(R.string.enable_wifi_key)).setSummary(getString(R.string.disabled));
            getPreferenceManager().findPreference((getString(R.string.wifi_ipv4_preferences_key))).setEnabled(false);
            getPreferenceManager().findPreference((getString(R.string.wifi_ipv6_preferences_key))).setEnabled(false);
        }

        getPreferenceScreen().findPreference(getString(R.string.enable_wifi_key)).setOnPreferenceChangeListener((preference, newValue) -> {
            if ((boolean) newValue) {
                getPreferenceScreen().findPreference(getString(R.string.enable_wifi_key)).setSummary(getString(R.string.enabled));
                getPreferenceManager().findPreference((getString(R.string.wifi_ipv4_preferences_key))).setEnabled(true);
                getPreferenceManager().findPreference((getString(R.string.wifi_ipv6_preferences_key))).setEnabled(true);

                Facemgr facemgr = Facemgr.getInstance();
                int wifiSourcePortIPv4 = Integer.parseInt(sharedPreferences.getString(getString(R.string.wifi_source_port_ipv4_key), getString(R.string.default_wifi_source_port_ipv4)));
                String wifiNextHopIPv4 = sharedPreferences.getString(getString(R.string.wifi_nexthop_ipv4_key), getString(R.string.default_wifi_nexthop_ipv4));
                int wifiNextHopPortIPv4 = Integer.parseInt(sharedPreferences.getString(getString(R.string.wifi_nexthop_port_ipv4_key), getString(R.string.default_wifi_nexthop_port_ipv4)));
                facemgr.updateInterfaceIPv4(NetdeviceTypeEnum.NETDEVICE_TYPE_WIFI.getValue(), wifiSourcePortIPv4, wifiNextHopIPv4, wifiNextHopPortIPv4);

                int wifiSourcePortIPv6 = Integer.parseInt(sharedPreferences.getString(getString(R.string.wifi_source_port_ipv6_key), getString(R.string.default_wifi_source_port_ipv6)));
                String wifiNextHopIPv6 = sharedPreferences.getString(getString(R.string.wifi_nexthop_ipv6_key), getString(R.string.default_wifi_nexthop_ipv6));
                int wifiNextHopPortIPv6 = Integer.parseInt(sharedPreferences.getString(getString(R.string.wifi_nexthop_port_ipv6_key), getString(R.string.default_wifi_nexthop_port_ipv6)));
                facemgr.updateInterfaceIPv6(NetdeviceTypeEnum.NETDEVICE_TYPE_WIFI.getValue(), wifiSourcePortIPv6, wifiNextHopIPv6, wifiNextHopPortIPv6);


            } else {
                getPreferenceScreen().findPreference(getString(R.string.enable_wifi_key)).setSummary(getString(R.string.disabled));
                getPreferenceManager().findPreference((getString(R.string.wifi_ipv4_preferences_key))).setEnabled(false);
                getPreferenceManager().findPreference((getString(R.string.wifi_ipv6_preferences_key))).setEnabled(false);

                Facemgr facemgr = Facemgr.getInstance();
                facemgr.unsetInterfaceIPv4(NetdeviceTypeEnum.NETDEVICE_TYPE_WIFI.getValue());
                facemgr.unsetInterfaceIPv6(NetdeviceTypeEnum.NETDEVICE_TYPE_WIFI.getValue());
            }
            return true;


        });

    }
}
