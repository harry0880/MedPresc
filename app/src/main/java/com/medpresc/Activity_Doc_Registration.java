package com.medpresc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.GpsSatellite;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.medpresc.GetSet.DocRegistrationGetSet;
import com.medpresc.SpinnerAdapters.District;
import com.medpresc.SpinnerAdapters.DocRegType;
import com.medpresc.SpinnerAdapters.InstituteName;
import com.medpresc.SpinnerAdapters.Speciality;
import com.medpresc.SpinnerAdapters.State;
import com.medpresc.utils.ProgressGenerator;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fr.ganfra.materialspinner.MaterialSpinner;

public class Activity_Doc_Registration extends AppCompatActivity implements  ProgressGenerator.OnCompleteListener {
    DbHandler db;
    MaterialSpinner sp_State,spDistrict,spInstitute,spSpeciality,spDocRegType;
    Context context;

    String instanceId,tokenId;
    ArrayAdapter<District> districtAdapter;
    ArrayAdapter<State> stateAdapter;
    ArrayAdapter<InstituteName> instituteNameAdapter;
    ArrayAdapter<Speciality> specialityAdapter;
    ArrayAdapter<DocRegType> docRegTypeAdapter;
    Boolean state_spinner_flag=false,District_spinner_flag=false,Institute_Spinner_flag=false,Speciality_Spinner_flag=false,Reg_Spinner_flag=false;
    FloatingActionButton fab;
    String[] initDistrict = {"District"};
    ActionProcessButton btnSignIn;
    DocRegistrationGetSet getset;
    EditText etName,etPhone,etEmail,etDocReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__doc__registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
        context=this;
        instanceId= FirebaseInstanceId.getInstance().getId();
        tokenId=FirebaseInstanceId.getInstance().getToken();
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
                                                      getset.setState(state.getStateId());
                                                     setDistrictSpinner(state.getStateId());
                                                      District_spinner_flag=false;
                                                  }
                                                   state_spinner_flag=true;
                                               }
                                               @Override
                                               public void onNothingSelected(AdapterView<?> parent) {
                                               }
                                           });

        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(District_spinner_flag)
                {
                   District district=((District) spDistrict.getSelectedItem());
                    getset.setDistrict(district.getDistrict_Id());
                    setInstName();
                }
                District_spinner_flag=true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spInstitute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(Institute_Spinner_flag)
                getset.setInstitute(((InstituteName)spInstitute.getSelectedItem()).getInstituteId());
                Institute_Spinner_flag=true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSpeciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(Speciality_Spinner_flag)
                {
                    getset.setSpeciality(((Speciality)spSpeciality.getSelectedItem()).getSpecialityId());
                }
                Speciality_Spinner_flag=true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDocRegType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(Reg_Spinner_flag)
                getset.setRegType(((DocRegType)spDocRegType.getSelectedItem()).getDocRegId());
                Reg_Spinner_flag=true;
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

                getset.setName(etName.getText().toString());
                getset.setEmailID(etEmail.getText().toString());
                getset.setPhoneNumber(etPhone.getText().toString());
                getset.setRegNumber(etDocReg.getText().toString());
                new SubmitAsyncTask().execute(getset);
             //   SharedPreferences.Editor prefs=context.getSharedPreferences()

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
        etDocReg=(EditText)findViewById(R.id.etDocRegistration);
        etEmail=(EditText)findViewById(R.id.etMail);
        etName=(EditText)findViewById(R.id.etName);
        etPhone=(EditText)findViewById(R.id.etContact);
        getset= new DocRegistrationGetSet();
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
        instituteNameAdapter=new ArrayAdapter<InstituteName>(this,android.R.layout.simple_spinner_item,db.getInstName(getset.getDistrict(),getset.getState()));
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
    public void onComplete() {
    }

    private class SubmitAsyncTask extends AsyncTask<DocRegistrationGetSet,Void,Boolean>
    {
        ProgressDialog dialog=new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please Wait!!!");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(DocRegistrationGetSet... params) {
            return db.SendDoctorRegistartion(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
           if(dialog.isShowing() && aBoolean)
           {
               dialog.dismiss();
            new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE).setTitleText("Submitted").setContentText("Your Apllication have been submitted ").setConfirmClickListener(null).show();
           new send_Unique_Token_to_server().execute();
           }
            else
           {
               dialog.dismiss();
               new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE).setTitleText("Error!!!").show();

           }
            super.onPostExecute(aBoolean);
        }
    }


    private class send_Unique_Token_to_server extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... params) {
            return db.CallWebService_Send_Token(instanceId,tokenId,getset.getPhoneNumber());
        }
    }
}
