package com.syber.ssspltd.response.WhysssRespons;
public class WhySSS_Response {
    private String SN_number,Name;
    public WhySSS_Response(String SN_number, String name) {
        this.SN_number = SN_number;
        Name = name;
    }

    public String getSN_number() {
        return SN_number;
    }

    public void setSN_number(String SN_number) {
        this.SN_number = SN_number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
