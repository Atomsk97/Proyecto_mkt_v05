package com.example.proyecto_mkt_v05.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityIsNotWorking {

    public ConnectivityIsNotWorking() {
    }

    public static boolean getConnectivityStatus(Context context){
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(null != activeNetwork){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return  false;
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return false;
        }
        return true;
    }
}
