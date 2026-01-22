package com.syber.ssspltd.data.model.gallery

data class EventItem(
    val EventID: Int,
    val EventName: String,
    val image_list: List<EventMediaItem>
)
data class EventMediaItem(
    val source_url: String,
    val linktype: String   // "image" or "videolink"
)
