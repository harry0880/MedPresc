package com.medpresc.SpinnerAdapters;

/**
 * Created by Administrator on 18/05/2016.
 */
public class DocRegType {
    public String getDocRegId() {
        return DocRegId;
    }

    public void setDocRegId(String docRegId) {
        DocRegId = docRegId;
    }

    public String getDocRegName() {
        return DocRegName;
    }

    public void setDocRegName(String docRegName) {
        DocRegName = docRegName;
    }

    public DocRegType(String docRegId, String docRegName) {
        DocRegId = docRegId;
        DocRegName = docRegName;
    }

    String DocRegId,DocRegName;

    public String toString()
    {
        return DocRegName;
    }
}
