package com.syber.ssspltd.response;

public class MoreItems {
    private String onClickId,Name;
    private  Integer img;

    public MoreItems(String onClickId, String name, Integer img) {

        this.onClickId = onClickId;
        Name = name;
        this.img = img;
    }

    public String getOnClickId() {
        return onClickId;
    }

    public void setOnClickId(String onClickId) {
        this.onClickId = onClickId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}
