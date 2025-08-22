package com.syber.ssspltd.response;

public class SchemeModel {

    private String scheme;
    private String ID;

    public SchemeModel(String scheme,String ID) {
        this.scheme = scheme;
        this.ID = ID;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
