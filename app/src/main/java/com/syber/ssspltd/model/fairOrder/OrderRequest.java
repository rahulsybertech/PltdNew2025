package com.syber.ssspltd.model.fairOrder;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    public String id = null;
    public String salePartyId;
    public String purchasePartyId;
    public String subPartyID;
    public String orderTypeId;
    public String marketerId;
    public String bStationId;
    public int totalQty;
    public int totalAmount;
    public String transportId;
    public String schemeId = null;
    public String remark;
    public String deliveryDateFrom;
    public String deliveryDateTo;
    public String orderStatus;
    public String traceIdentifier;
    public String dispatchTypeID = null;
    public String subPartyAsRemark;
    public String pvtMarka;

    public List<OrderBookSecondary> orderBookSecondaries;
    public List<String> images;
}

