package com.syber.ssspltd.response;

public class DashBoardList {

    Integer image;
    String title;

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DashBoardList(Integer image, String title) {
        this.image = image;
        this.title = title;
    }
}
