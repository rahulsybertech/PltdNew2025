
package com.syber.ssspltd.response.NewDashboadRespo.InsDis;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class InterestDiscountDetail {

    @SerializedName("Amount")
    private String mAmount;
    @SerializedName("CompanyCode")
    private String mCompanyCode;

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getCompanyCode() {
        return mCompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        mCompanyCode = companyCode;
    }

}
