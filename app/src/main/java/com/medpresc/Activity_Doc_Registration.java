package com.medpresc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.medpresc.SpinnerAdapters.District;
import com.medpresc.SpinnerAdapters.DocRegType;
import com.medpresc.SpinnerAdapters.InstituteName;
import com.medpresc.SpinnerAdapters.Speciality;
import com.medpresc.SpinnerAdapters.State;
import com.medpresc.utils.ProgressGenerator;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

public class Activity_Doc_Registration extends AppCompatActivity implements  ProgressGenerator.OnCompleteListener {
    DbHandler db;
    MaterialSpinner sp_State,spDistrict,spInstitute,spSpeciality,spDocRegType;
    Context context;
    ArrayAdapter<District> districtAdapter;
    ArrayAdapter<State> stateAdapter;
    ArrayAdapter<InstituteName> instituteNameAdapter;
    ArrayAdapter<Speciality> specialityAdapter;
    ArrayAdapter<DocRegType> docRegTypeAdapter;
    Boolean state_spinner_flag=false;
    FloatingActionButton fab;
    String[] initDistrict = {"District"};
     ActionProcessButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__doc__registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
        context=this;
        final ProgressGenerator progressGenerator = new ProgressGenerator(this);
        setStateSpinner();
        setDistrictSpinner(initDistrict);
        setInstName();
        setSpSpeciality();
        setSpDocRegType();

        sp_State.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                  if(state_spinner_flag) {
                                                      State state=((State) sp_State.getSelectedItem());
                                                     setDistrictSpinner(state.getStateId());
                                                  }
                                                   state_spinner_flag=true;
                                               }
                                               @Override
                                               public void onNothingSelected(AdapterView<?> parent) {
                                               }
                                           });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(btnSignIn);
            }
        });
    }

    void initialize()
    {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        db=new DbHandler(Activity_Doc_Registration.this);
        sp_State = (MaterialSpinner) findViewById(R.id.spState);
        spDistrict=(MaterialSpinner)findViewById(R.id.spDistrict);
        spInstitute=(MaterialSpinner)findViewById(R.id.spInstitute);
        spSpeciality=(MaterialSpinner)findViewById(R.id.spSpeciality);
        spDocRegType=(MaterialSpinner)findViewById(R.id.spDocRegistration);
        btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);

    }

    void setDistrictSpinner(String scode)
    {
        districtAdapter = new ArrayAdapter<District>(context, android.R.layout.simple_spinner_item, db.getDistrict(scode));
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDistrict.setAdapter(districtAdapter);
    }
// For initialzz
    void setDistrictSpinner(String[] initdistrict)
    {
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, initdistrict);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDistrict.setAdapter(districtAdapter);
    }

    void setStateSpinner()
    {
        stateAdapter = new ArrayAdapter<State>(this, android.R.layout.simple_spinner_item,db.getState());
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_State.setAdapter(stateAdapter);
    }



    void setInstName()
    {
        instituteNameAdapter=new ArrayAdapter<InstituteName>(this,android.R.layout.simple_spinner_item,db.getInstName());
        instituteNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInstitute.setAdapter(instituteNameAdapter);
    }

    void setSpSpeciality()
    {
        specialityAdapter=new ArrayAdapter<Speciality>(this,android.R.layout.simple_spinner_item,db.getSpecName());
        specialityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpeciality.setAdapter(specialityAdapter);
    }

    void setSpDocRegType()
    {
        docRegTypeAdapter=new ArrayAdapter<DocRegType>(this,android.R.layout.simple_spinner_item,db.getDocRegType());
        docRegTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDocRegType.setAdapter(docRegTypeAdapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Activity_Doc_Registration.this,AndroidDatabaseManager.class));
        finish();
        super.onBackPressed();
    }

    @Override
    public void onComplete() {

    }
}
