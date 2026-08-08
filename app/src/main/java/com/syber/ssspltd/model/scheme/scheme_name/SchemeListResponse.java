package com.syber.ssspltd.model.scheme.scheme_name;

import java.util.ArrayList;
import java.util.List;

public class SchemeListResponse {

    private ArrayList<SchemeItem> data;
    private String message;
    private boolean success;
    private boolean error;
    private String responsecode;

    public List<SchemeItem> getData() {
        return data;
    }

    public void setData(ArrayList<SchemeItem> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return error;
    }

    public String getResponsecode() {
        return responsecode;
    }
}
