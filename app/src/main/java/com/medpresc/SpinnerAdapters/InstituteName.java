package com.medpresc.SpinnerAdapters;

/**
 * Created by Administrator on 18/05/2016.
 */
public class InstituteName {
    String InstituteId,InstituteName;
    public String getInstituteId() {
        return InstituteId;
    }

    public void setInstituteId(String instituteId) {
        InstituteId = instituteId;
    }

    public String getInstituteName() {
        return InstituteName;
    }

    public void setInstituteName(String instituteName) {
        InstituteName = instituteName;
    }


    public InstituteName(String instituteId, String instituteName) {
        InstituteName = instituteName;
        InstituteId = instituteId;
    }

    public String toString()
    {
        return InstituteName;
    }


}
