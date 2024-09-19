package com.syber.ssspltd.response;

public class GraphData {
    private String title;
    private String value;
    private String color;

    public GraphData(String title, String value, String color) {
        this.title = title;
        this.value = value;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
