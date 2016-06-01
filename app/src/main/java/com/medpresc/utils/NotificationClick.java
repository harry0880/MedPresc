package com.medpresc.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.medpresc.DbHandler;

/**
 * Created by Administrator on 01/06/2016.
 */
public class NotificationClick extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        DbHandler db=new DbHandler(context);

    }
}
