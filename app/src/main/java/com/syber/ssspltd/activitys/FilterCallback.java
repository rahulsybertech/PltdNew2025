package com.syber.ssspltd.activitys;

import com.syber.ssspltd.NewFilterResponse.FilterType;
import com.syber.ssspltd.adapter.NewFilterPendingOrdAdapter.FilterTypePendingOrder;

public interface FilterCallback {
    public void filterChanged(FilterType mFilterType);
}
