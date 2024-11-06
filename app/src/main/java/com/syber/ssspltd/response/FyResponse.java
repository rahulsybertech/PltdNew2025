package com.syber.ssspltd.response;



import org.json.JSONArray;
import org.json.JSONObject;

public class FyResponse {

    private boolean responseStatus;
    private String responseMessage;
    private boolean statusLock;
    private boolean supplierOrderStatus;
    private boolean blackListReportStatus;
    private FYear[] fYearListResult;

    // Constructor to parse JSON response
    public FyResponse(String jsonResponse) {
        if (jsonResponse != null && !jsonResponse.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                this.responseStatus = jsonObject.optBoolean("ResponseStatus", false);
                this.responseMessage = jsonObject.optString("ResponseMessage", "");
                this.statusLock = jsonObject.optBoolean("StatusLock", false);
                this.supplierOrderStatus = jsonObject.optBoolean("SupplierOrderStatus", false);
                this.blackListReportStatus = jsonObject.optBoolean("BlackListReportStatus", false);

                JSONArray fYearListArray = jsonObject.optJSONArray("FYearListResult");
                if (fYearListArray != null) {
                    fYearListResult = new FYear[fYearListArray.length()];
                    for (int i = 0; i < fYearListArray.length(); i++) {
                        JSONObject fYearObject = fYearListArray.optJSONObject(i);
                        if (fYearObject != null) {
                            fYearListResult[i] = new FYear(
                                    fYearObject.optString("SRNO", null),
                                    fYearObject.optString("ID", null),
                                    fYearObject.optString("FYEAR", null),
                                    fYearObject.optString("FY_StartDate", null),
                                    fYearObject.optString("FY_EndDate", null),
                                    fYearObject.optString("DBNAME", null),
                                    fYearObject.optString("DEFAULTDB", null)
                            );
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("Error parsing the JSON response: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid or empty JSON response.");
        }
    }

    // FYear inner class
    public static class FYear {
        private String srNo;
        private String id;
        private String fYear;
        private String fyStartDate;
        private String fyEndDate;
        private String dbName;
        private String defaultDb;

        public FYear(String srNo, String id, String fYear, String fyStartDate, String fyEndDate, String dbName, String defaultDb) {
            this.srNo = srNo;
            this.id = id;
            this.fYear = fYear;
            this.fyStartDate = fyStartDate;
            this.fyEndDate = fyEndDate;
            this.dbName = dbName;
            this.defaultDb = defaultDb;
        }

        // Getters
        public String getSrNo() {
            return srNo;
        }

        public String getId() {
            return id;
        }

        public String getFYear() {
            return fYear;
        }

        public String getFyStartDate() {
            return fyStartDate;
        }

        public String getFyEndDate() {
            return fyEndDate;
        }

        public String getDbName() {
            return dbName;
        }

        public String getDefaultDb() {
            return defaultDb;
        }
    }

    // Getters for outer class
    public boolean isResponseStatus() {
        return responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public boolean isStatusLock() {
        return statusLock;
    }

    public boolean isSupplierOrderStatus() {
        return supplierOrderStatus;
    }

    public boolean isBlackListReportStatus() {
        return blackListReportStatus;
    }

    public FYear[] getFYearListResult() {
        return fYearListResult;
    }

    public static void main(String[] args) {
        // Sample usage
        String jsonResponse = "{...}"; // Your JSON response here
        FyResponse parser = new FyResponse(jsonResponse);

        // Example access
        System.out.println("Response Message: " + parser.getResponseMessage());
        if (parser.getFYearListResult() != null) {
            for (FYear year : parser.getFYearListResult()) {
                System.out.println("Fiscal Year: " + year.getFYear());
            }
        }
    }
}
