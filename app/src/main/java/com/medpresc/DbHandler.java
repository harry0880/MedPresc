package com.medpresc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.medpresc.GetSet.DocRegistrationGetSet;
import com.medpresc.SpinnerAdapters.District;
import com.medpresc.SpinnerAdapters.DocRegType;
import com.medpresc.SpinnerAdapters.InstituteName;
import com.medpresc.SpinnerAdapters.Speciality;
import com.medpresc.SpinnerAdapters.State;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Administrator on 05/05/2016.
 */
public class DbHandler extends SQLiteOpenHelper {
    JSONObject jsonResponse ;

    final String NameSpace="http://tempuri.org/";
    String URL="http://192.168.0.100/Service.asmx";

    String LoadMasterMathod = "master";
    String SoapLinkMaster="http://tempuri.org/master";

    String SendDoctorRegistration = "DoctoRegistration";
    String SoapLinkSendDoctorRegistration="http://tempuri.org/DoctoRegistration";

    String Send_Reg_Token="AndroidRegistration";
    String Soap_Send_Reg_Toekn="http://tempuri.org/AndroidRegistration";

    String Appointment_Confirmation="AppoinmentConfirmation";
    String Soap_Appointment_Confirmation="http://tempuri.org/AppoinmentConfirmation";


    public DbHandler(Context context) {
        super(context, DbConstant.DBNAME, null, DbConstant.DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConstant.CREATE_TABLE_District_Master);
        db.execSQL(DbConstant.CREATE_TABLE_Doctor_Reg_type);
        db.execSQL(DbConstant.CREATE_TABLE_Institute_MASTER);
        db.execSQL(DbConstant.CREATE_TABLE_SPECIALITY_MASTER);
        db.execSQL(DbConstant.CREATE_TABLE_STATE_MASTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean Load_Master_tables()
    {
        String res= null;
        SoapObject request=new SoapObject(NameSpace, LoadMasterMathod);
        SoapSerializationEnvelope envolpe=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet=true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP= new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkMaster, envolpe);
            SoapPrimitive response = (SoapPrimitive)envolpe.getResponse();
            res=response.toString();
            //System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        String[] Response= res.split("#"),JsonNames={"State","District","DocRegistration","DocSpeciality","InstName"};
        int lengthJsonArr ;
        try {
            for (int i = 0; i < 5; i++) {
                Response[i]="{ \""+JsonNames[i]+"\" :"+Response[i]+" }";
                jsonResponse = new JSONObject(Response[i]);
                JSONArray jsonMainNode = jsonResponse.optJSONArray(JsonNames[i]);
                lengthJsonArr = jsonMainNode.length();
                for(int j=0; j < lengthJsonArr; j++)
                {
                    ContentValues values = new ContentValues();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                    if(i==0)
                    {

                        values.put(DbConstant.C_Scode,jsonChildNode.optString("scode").toString());
                        values.put(DbConstant.C_Sname,jsonChildNode.optString("sname").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DbConstant.T_State_Master, null, values);
                        writeableDB.close();
                    }
                    if(i==1)
                    {
                        values.put(DbConstant.C_Dist_Scode,jsonChildNode.optString("scode_ds").toString());
                        values.put(DbConstant.C_Dist_Code,jsonChildNode.optString("dcode_ds").toString());
                        values.put(DbConstant.C_Dist_Name,jsonChildNode.optString("ds_detail").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DbConstant.T_District_Master, null, values);
                        writeableDB.close();
                    }
                    if(i==2)
                    {
                        values.put(DbConstant.C_Doc_Reg_ID,jsonChildNode.optString("id").toString());
                        values.put(DbConstant.C_Doc_Reg_Detail,jsonChildNode.optString("type").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DbConstant.T_Doc_Reg_Type, null, values);
                        writeableDB.close();
                    }
                    if(i==3)
                    {
                        values.put(DbConstant.C_Doc_Spl_ID,jsonChildNode.optString("splid").toString());
                        values.put(DbConstant.C_Doc_Spl_Detail,jsonChildNode.optString("spldesc").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DbConstant.T_Doc_Spl_Type, null, values);
                        writeableDB.close();
                    }
                    if(i==4)
                    {
                        values.put(DbConstant.C_Doc_Inst_ID,jsonChildNode.optString("Instid").toString());
                        values.put(DbConstant.C_Doc_Inst_Detail,jsonChildNode.optString("Instname").toString());
                        values.put(DbConstant.C_Scode,jsonChildNode.optString("HealthInstituteSCode"));
                        values.put(DbConstant.C_Dist_Code,jsonChildNode.optString("HealthInstituteDCode"));
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DbConstant.T_Doc_Inst, null, values);
                        writeableDB.close();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return  true;
    }
    public Boolean SendDoctorRegistartion(DocRegistrationGetSet obj)

    {  String res= null;
     SoapObject request=new SoapObject(NameSpace, SendDoctorRegistration);
     PropertyInfo pi = new PropertyInfo();

     pi.setName("docname");
     pi.setValue(obj.getName());
     pi.setType(String.class);
     request.addProperty(pi);

        pi = new PropertyInfo();
     pi.setName("docemail");
     pi.setValue(obj.getEmailID());
     pi.setType(String.class);
     request.addProperty(pi);

        pi = new PropertyInfo();
     pi.setName("docmobile");
     pi.setValue(obj.getPhoneNumber());
     pi.setType(String.class);
     request.addProperty(pi);

        pi = new PropertyInfo();
     pi.setName("stateid");
     pi.setValue(obj.getState());
     pi.setType(String.class);
     request.addProperty(pi);

        pi = new PropertyInfo();
     pi.setName("districtid");
     pi.setValue(obj.getDistrict());
     pi.setType(String.class);
     request.addProperty(pi);

        pi = new PropertyInfo();
     pi.setName("instituteid");
     pi.setValue(obj.getInstitute());
     pi.setType(String.class);
     request.addProperty(pi);

        pi = new PropertyInfo();
     pi.setName("docregistrationid");
     pi.setValue(obj.getRegNumber());
     pi.setType(String.class);
     request.addProperty(pi);

        pi = new PropertyInfo();
     pi.setName("docregistrationno");
     pi.setValue(obj.getRegNumber());
     pi.setType(String.class);
     request.addProperty(pi);

        pi = new PropertyInfo();
     pi.setName("specialityid");
     pi.setValue(obj.getSpeciality());
     pi.setType(String.class);
     request.addProperty(pi);

        pi = new PropertyInfo();
     pi.setName("imei");
     pi.setValue("123");
     pi.setType(String.class);
     request.addProperty(pi);

     SoapSerializationEnvelope envolpe=new SoapSerializationEnvelope(SoapEnvelope.VER11);
     envolpe.dotNet=true;
     envolpe.setOutputSoapObject(request);
     HttpTransportSE androidHTTP= new HttpTransportSE(URL);

     try {
         androidHTTP.call(SoapLinkSendDoctorRegistration, envolpe);
         SoapPrimitive response = (SoapPrimitive)envolpe.getResponse();
         res=response.toString();
         //System.out.println(res);
     } catch (Exception e) {
         e.printStackTrace();
         return false;
     }

     return true;
 }


    public ArrayList<State> getState()

    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DbConstant.T_State_Master+";",null);
        cr.moveToFirst();
        ArrayList<State> statelist=new ArrayList<>();
        do {
        statelist.add(new State(cr.getString(1),cr.getString(0)));
        }while (cr.moveToNext());
    return statelist;
    }

    public ArrayList<District> getDistrict(String Statecode)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DbConstant.T_District_Master+" where "+DbConstant.C_Dist_Scode+"='"+Statecode+"'",null);
        cr.moveToFirst();
        ArrayList<District> districtlist=new ArrayList<District>();
        do {
            districtlist.add(new District(cr.getString(1),cr.getString(2)));
        }while (cr.moveToNext());
        return districtlist;
    }

    public ArrayList<InstituteName> getInstName(String District, String State)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DbConstant.T_Doc_Inst+" where "+DbConstant.C_Scode+"="+State+" and "+DbConstant.C_Dist_Code+"="+District+";",null);
        ArrayList<InstituteName> instituteNames=new ArrayList<InstituteName>();
        if(cr.getCount()<=0)
        {
            instituteNames.add(new InstituteName("0","No Institiute Found"));
            return instituteNames;
        }
        cr.moveToFirst();

        do {
            instituteNames.add(new InstituteName(cr.getString(0),cr.getString(1)));
        }while (cr.moveToNext());
        return instituteNames;
    }

    public ArrayList<Speciality> getSpecName()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DbConstant.T_Doc_Spl_Type+";",null);
        cr.moveToFirst();
        ArrayList<Speciality> specialities=new ArrayList<Speciality>();
        do {
            specialities.add(new Speciality(cr.getString(0),cr.getString(1)));
        }while (cr.moveToNext());
        return specialities;
    }

    public ArrayList<DocRegType> getDocRegType()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DbConstant.T_Doc_Reg_Type+";",null);
        cr.moveToFirst();
        ArrayList<DocRegType> specialities=new ArrayList<DocRegType>();
        do {
            specialities.add(new DocRegType(cr.getString(0),cr.getString(1)));
        }while (cr.moveToNext());
        return specialities;
    }

//Viewing DB
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(Exception sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }


   public String CallWebService_Send_Token(String InstanceId,String token,String Phone)
    {
        String res= null;
        SoapObject request=new SoapObject(NameSpace, Send_Reg_Token);
        PropertyInfo pi = new PropertyInfo();

        pi.setName("AndroidRegID");
        pi.setValue(InstanceId);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("AndroidRegToken");
        pi.setValue(token);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("MobileNo");
        pi.setValue(Phone);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ApplicationType");
        pi.setValue("Doctor");
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envolpe=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet=true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP= new HttpTransportSE(URL);

        try {
            androidHTTP.call(Soap_Send_Reg_Toekn, envolpe);
            SoapPrimitive response = (SoapPrimitive)envolpe.getResponse();
            res=response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return res;
    }

    public String CallWebService_AppointmentConfirm(String Docid,String PatientId,String appointmentId,String acceptance)
    {
        String res= null;
        SoapObject request=new SoapObject(NameSpace, Appointment_Confirmation);
        PropertyInfo pi = new PropertyInfo();

        pi.setName("AppoinmentNumber");
        pi.setValue(appointmentId);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("Doctorid");
        pi.setValue(Docid);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("patientid");
        pi.setValue(PatientId);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("AppoinmentConfirmationbit");
        pi.setValue(acceptance);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envolpe=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet=true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP= new HttpTransportSE(URL);

        try {
            androidHTTP.call(Soap_Appointment_Confirmation, envolpe);
            SoapPrimitive response = (SoapPrimitive)envolpe.getResponse();
            res=response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return res;
    }

}
