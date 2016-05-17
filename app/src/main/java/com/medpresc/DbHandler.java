package com.medpresc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.medpresc.SpinnerAdapters.District;
import com.medpresc.SpinnerAdapters.State;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
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
    String URL="http://10.145.24.148/Service.asmx";

    String LoadMasterMathod = "master";
    String SoapLinkMaster="http://tempuri.org/master";


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

        String[] Response= res.split("#"),JsonNames={"State","District","DocRegistration","DocSpeciality",};
        int lengthJsonArr ;
        try {
            for (int i = 0; i < 3; i++) {
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

    public ArrayList<State> getState()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DbConstant.T_State_Master+";",null);
        cr.moveToFirst();
        ArrayList<State> statelist=new ArrayList<State>();
        do {
        statelist.add(new State(cr.getString(1),cr.getString(0)));
        }while (cr.moveToNext());
    return statelist;
    }
    public ArrayList<District> getDistrict(String Statecode)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DbConstant.T_District_Master+" where "+DbConstant.C_Dist_Scode+"="+Statecode,null);
        cr.moveToFirst();
        ArrayList<District> districtlist=new ArrayList<District>();
        do {
            districtlist.add(new District(cr.getString(0),cr.getString(1)));
        }while (cr.moveToNext());
        return districtlist;
    }

}
