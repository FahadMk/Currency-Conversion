package com.dgsl.currencyconverter.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkReceiver extends BroadcastReceiver {


    boolean checkInternet(Context context) {
        ConverterUtil converterUtil = new ConverterUtil(context);
        return converterUtil.isNetworkAvailable();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (checkInternet(context)) {
           //online
        }else{
            //offline
        }
    }
}