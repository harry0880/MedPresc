package com.medpresc;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class splash extends AppCompatActivity {
DbHandler dbh;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_splash);
        dbh=new DbHandler(splash.this);
        new LoadMaster().execute();
    }

    private class LoadMaster extends AsyncTask<Void,Void,Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... params) {
            return dbh.Load_Master_tables();
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if(bool)
            {
                startActivity(new Intent(splash.this,Activity_Doc_Registration.class));
            }
            else {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE).setTitleText("Opps").setContentText("Please check Internet Connection and try again!!!").show();
            }

        }
    }
}
