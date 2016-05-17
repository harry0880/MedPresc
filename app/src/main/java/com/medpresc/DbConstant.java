package com.medpresc;

/**
 * Created by Administrator on 05/05/2016.
 */
public class DbConstant {

    public static final String DBNAME = "Master";
    public static final int DBVERSION = 1;

    public static final String T_State_Master="TBLStateMaster";
    public  static final String C_Scode="Scode";
    public  static final String C_Sname="Sname";

    public static final String T_District_Master="TBLDistrictMaster";
    public  static final String C_Dist_Scode="DScode";
    public  static final String C_Dist_Code="Dcode";
    public  static final String C_Dist_Name="Dname";

    public  static final String T_Doc_Reg_Type="TBLDocRegMaster";
    public  static final String C_Doc_Reg_ID="DocRegID";
    public  static final String C_Doc_Reg_Detail="DocRegName";

    public  static final String T_Doc_Spl_Type="TBLDocSplMaster";
    public  static final String C_Doc_Spl_ID="DocSplID";
    public  static final String C_Doc_Spl_Detail="DocSplName";

    public  static final String T_Doc_Inst="TBLInsMaster";
    public  static final String C_Doc_Inst_ID="InstID";
    public  static final String C_Doc_Inst_Detail="DocInstName";

    public static final String CREATE_TABLE_STATE_MASTER = "CREATE TABLE "+ T_State_Master + "(" + C_Scode/*0*/ + " TEXT,"
            + C_Sname/*1*/ +" TEXT);";

    public static final String CREATE_TABLE_District_Master= "CREATE TABLE "+ T_District_Master + "(" + C_Dist_Scode/*0*/ + " TEXT,"
            + C_Dist_Code/*1*/ + " TEXT,"
            + C_Dist_Name/*2*/+ " TEXT);";

    public static final String CREATE_TABLE_Doctor_Reg_type = "CREATE TABLE "+ T_Doc_Reg_Type + "(" + C_Doc_Reg_ID/*0*/ + " TEXT,"
            + C_Doc_Reg_Detail/*1*/ +" TEXT);";

    public static final String CREATE_TABLE_SPECIALITY_MASTER = "CREATE TABLE "+ T_Doc_Spl_Type + "(" + C_Doc_Spl_ID/*0*/ + " TEXT,"
            + C_Doc_Spl_Detail/*1*/ +" TEXT);";

    public static final String CREATE_TABLE_Institute_MASTER = "CREATE TABLE "+ T_Doc_Inst + "(" + C_Doc_Inst_ID/*0*/ + " TEXT,"
            + C_Doc_Inst_Detail/*1*/ +" TEXT);";


}
