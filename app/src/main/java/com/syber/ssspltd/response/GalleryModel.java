package com.syber.ssspltd.response;

public class GalleryModel {
    private String source_url;
    private String linktype;

    public GalleryModel(String source_url, String linktype) {
        this.source_url = source_url;
        this.linktype = linktype;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getLinktype() {
        return linktype;
    }

    public void setLinktype(String linktype) {
        this.linktype = linktype;
    }
}
