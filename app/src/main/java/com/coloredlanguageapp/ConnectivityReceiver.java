package com.coloredlanguageapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.NetworkInterface;

public class ConnectivityReceiver extends BroadcastReceiver {
    public static ConnectivityReceiverListener connectivityReceiverListener;

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();

        if(connectivityReceiverListener!=null){
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);

        }

    }
    //method to check manually (click on button)
    public static boolean isConnected(){
        ConnectivityManager connectivityManager= (ConnectivityManager) MyApp.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();

    }

    //Interface
    public interface ConnectivityReceiverListener{
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
