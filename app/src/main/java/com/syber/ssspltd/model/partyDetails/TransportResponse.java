package com.syber.ssspltd.model.partyDetails;

import com.google.gson.annotations.SerializedName;
import com.syber.ssspltd.response.SubpartyModel;

import java.util.List;

public class TransportResponse {

    @SerializedName("ResponseCode")
    private int responseCode;

    @SerializedName("ResponseStatus")
    private boolean responseStatus;

    @SerializedName("ResponseMessage")
    private String responseMessage;

    @SerializedName("AvlLimit")
    private int avlLimit;

    @SerializedName("AvgDays")
    private int avgDays;

    @SerializedName("EmailId")
    private String emailId;

    @SerializedName("MobileNo")
    private String mobileNo;

    @SerializedName("SubPartyList")
    private List<SubpartyModel> subPartyList;

    // Getters and Setters

    public static class SubParty {

        @SerializedName("SubPartyId")
        private String subPartyId;

        @SerializedName("SubPartyName")
        private String subPartyName;

        @SerializedName("TransportList")
        private List<Transport> transportList;

        // Getters and Setters

        public static class Transport {

            @SerializedName("TransportId")
            private String transportId;

            @SerializedName("TransportName")
            private String transportName;

            @SerializedName("DefaultStatus")
            private boolean defaultStatus;

            @SerializedName("StationList")
            private List<Station> stationList;

            // Getters and Setters

            public static class Station {

                @SerializedName("StationId")
                private String stationId;

                @SerializedName("StationName")
                private String stationName;

                // Getters and Setters

                public String getStationId() {
                    return stationId;
                }

                public void setStationId(String stationId) {
                    this.stationId = stationId;
                }

                public String getStationName() {
                    return stationName;
                }

                public void setStationName(String stationName) {
                    this.stationName = stationName;
                }
            }

            public String getTransportId() {
                return transportId;
            }

            public void setTransportId(String transportId) {
                this.transportId = transportId;
            }

            public String getTransportName() {
                return transportName;
            }

            public void setTransportName(String transportName) {
                this.transportName = transportName;
            }

            public boolean isDefaultStatus() {
                return defaultStatus;
            }

            public void setDefaultStatus(boolean defaultStatus) {
                this.defaultStatus = defaultStatus;
            }

            public List<Station> getStationList() {
                return stationList;
            }

            public void setStationList(List<Station> stationList) {
                this.stationList = stationList;
            }
        }

        public String getSubPartyId() {
            return subPartyId;
        }

        public void setSubPartyId(String subPartyId) {
            this.subPartyId = subPartyId;
        }

        public String getSubPartyName() {
            return subPartyName;
        }

        public void setSubPartyName(String subPartyName) {
            this.subPartyName = subPartyName;
        }

        public List<Transport> getTransportList() {
            return transportList;
        }

        public void setTransportList(List<Transport> transportList) {
            this.transportList = transportList;
        }
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(boolean responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public int getAvlLimit() {
        return avlLimit;
    }

    public void setAvlLimit(int avlLimit) {
        this.avlLimit = avlLimit;
    }

    public int getAvgDays() {
        return avgDays;
    }

    public void setAvgDays(int avgDays) {
        this.avgDays = avgDays;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public List<SubpartyModel> getSubPartyList() {
        return subPartyList;
    }

    public void setSubPartyList(List<SubpartyModel> subPartyList) {
        this.subPartyList = subPartyList;
    }
}
