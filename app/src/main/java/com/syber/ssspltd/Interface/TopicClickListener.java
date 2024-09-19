package com.syber.ssspltd.Interface;

import com.syber.ssspltd.response.FinanacialYearListRespon.FYearListResult;

public interface TopicClickListener {
    void onItemClick(FYearListResult topic, int parentPos, boolean checked);
}
