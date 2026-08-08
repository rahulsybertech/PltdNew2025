package com.syber.ssspltd.model.scheme.scheme_detail;

import java.util.ArrayList;
import java.util.List;

public class SchemeData {

    private ArrayList<SchemeDetails> schemeDetailsResponse;
    private Object schemeSummaryResponse; // null in API

    public List<SchemeDetails> getSchemeDetailsResponse() {
        return schemeDetailsResponse;
    }

    public void setSchemeDetailsResponse(ArrayList<SchemeDetails> schemeDetailsResponse) {
        this.schemeDetailsResponse = schemeDetailsResponse;
    }

    public Object getSchemeSummaryResponse() {
        return schemeSummaryResponse;
    }

    public void setSchemeSummaryResponse(Object schemeSummaryResponse) {
        this.schemeSummaryResponse = schemeSummaryResponse;
    }
}
