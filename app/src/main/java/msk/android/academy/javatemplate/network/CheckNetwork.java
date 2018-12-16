package msk.android.academy.javatemplate.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import msk.android.academy.javatemplate.App;

public class CheckNetwork {

    public static boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) App.getContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }
}
