package com.syber.ssspltd.Interface;

import com.syber.ssspltd.response.DispatchResponse;
import com.syber.ssspltd.response.ItemModel;
import com.syber.ssspltd.response.MarketerModel;
import com.syber.ssspltd.response.SalepartyModel;
import com.syber.ssspltd.response.SchemeModel;
import com.syber.ssspltd.response.StationModel;
import com.syber.ssspltd.response.SubpartyModel;
import com.syber.ssspltd.response.TransportModel;

public interface OnClick {

    void setSaleParty(SalepartyModel salepartyModel);
    void setMarketer(MarketerModel marketerModel);
    void setSubParty(SubpartyModel subpartyModel);

    void setSubParty(SubpartyModel subpartyModel, int p);

    void setDispatchType(SubpartyModel subpartyModel1, int p);

    void setDispatchType(DispatchResponse.DispatchType subpartyModel1, int p);

    void setTransport(TransportModel transportModel);
      void setStation(StationModel stationModel);
//    void setPurParty(PurpartyModel purpartyModel);
       void setItemName(ItemModel itemModel);
//    void setDtlsItem (ItemModel itemModel);
     void setScheme(SchemeModel schemeModel);
//    void setItem(ItemModel itemModel);
}
