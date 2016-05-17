package com.medpresc;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.medpresc.SpinnerAdapters.District;
import com.medpresc.SpinnerAdapters.State;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

public class Activity_Doc_Registration extends AppCompatActivity {
    DbHandler db;
    MaterialSpinner sp_State,spDistrict,spInstitute,spSpeciality;
    Context context;
    ArrayAdapter<District> districtAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__doc__registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;
        db=new DbHandler(Activity_Doc_Registration.this);
        sp_State = (MaterialSpinner) findViewById(R.id.spState);
        spDistrict=(MaterialSpinner)findViewById(R.id.spDistrict);

        ArrayAdapter<State> adapter = new ArrayAdapter<State>(this, android.R.layout.simple_spinner_item,db.getState());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_State.setAdapter(adapter);

        sp_State.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                State state = (State) parent.getSelectedItem();
                    districtAdapter = new ArrayAdapter<District>(context, android.R.layout.simple_spinner_item,db.getDistrict(state.getStateId()));
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistrict.setAdapter(districtAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





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
