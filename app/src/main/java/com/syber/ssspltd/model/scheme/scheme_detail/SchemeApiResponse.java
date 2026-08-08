package com.syber.ssspltd.model.scheme.scheme_detail;

public class SchemeApiResponse {

    private SchemeData data;
    private String message;
    private boolean success;
    private boolean error;
    private String responsecode;

    public SchemeData getData() {
        return data;
    }

    public void setData(SchemeData data) {
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
