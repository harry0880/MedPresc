package com.medpresc.SpinnerAdapters;

/**
 * Created by Administrator on 17/05/2016.
 */
public class District {

    String District_Id,District_Name;
    public District(String District_Id,String District_Name)
    {
        this.District_Id=District_Id;
        this.District_Name=District_Name;
    }

    public String getDistrict_Id()
    {
        return District_Id;
    }
    public String getDistrict_Name()
    {
        return District_Name;
    }
    public String toString() {
        return District_Name;
    }
}
