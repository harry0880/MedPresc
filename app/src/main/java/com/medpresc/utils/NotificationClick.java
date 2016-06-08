package com.medpresc.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import com.medpresc.DbHandler;

/**
 * Created by Administrator on 01/06/2016.
 */
public class NotificationClick extends BroadcastReceiver {
    String docid,patientid,appid,acc;
    DbHandler db;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        db=new DbHandler(context);
        Bundle extra=intent.getExtras();
        docid=extra.getString("DocId");
        patientid=extra.getString("PatientId");
        appid=extra.getString("AppId");
        acc=extra.getString("Accpetance");

        new CallWeb_Service_Confirm_Appointment().execute();

    }

    private class CallWeb_Service_Confirm_Appointment extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... params) {
            db.CallWebService_AppointmentConfirm(docid,patientid,appid,acc);
            return null;
        }
    }
}
