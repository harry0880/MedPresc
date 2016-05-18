package com.medpresc.SpinnerAdapters;

/**
 * Created by Administrator on 18/05/2016.
 */
public class Speciality {

    String specialityId,specialityName;
    public String getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(String specialityId) {
        this.specialityId = specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public Speciality(String specialityId, String specialityName) {
        this.specialityId = specialityId;
        this.specialityName = specialityName;
    }

    public String toString()
    {
        return specialityName;
    }


}
