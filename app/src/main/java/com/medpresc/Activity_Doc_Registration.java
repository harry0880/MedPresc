package com.medpresc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import com.medpresc.SpinnerAdapters.District;
import com.medpresc.SpinnerAdapters.State;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

public class Activity_Doc_Registration extends AppCompatActivity {

    MaterialSpinner sp_State,spDistrict,spInstitute,spSpeciality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__doc__registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DbHandler db=new DbHandler(Activity_Doc_Registration.this);


        ArrayAdapter<State> adapter = new ArrayAdapter<State>(this, android.R.layout.simple_spinner_item,db.getState());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_State = (MaterialSpinner) findViewById(R.id.spState);
        sp_State.setAdapter(adapter);



        ArrayAdapter<District> districtAdapter = new ArrayAdapter<District>(this, android.R.layout.simple_spinner_item,db.getDistrict());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDistrict = (MaterialSpinner) findViewById(R.id.spDistrict);
        spDistrict.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
