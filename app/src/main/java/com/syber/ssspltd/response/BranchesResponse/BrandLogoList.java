package com.syber.ssspltd.response.BranchesResponse;

public class BrandLogoList {
    String id;
    String image;

    public BrandLogoList(String id, String image) {
        this.id = id;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
