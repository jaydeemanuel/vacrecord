package com.jaydeemanuel.finalproj.ui.home;

public class recyclerview_list {
    private Integer image;
    private String text;

    public recyclerview_list(Integer image, String text) {
        this.image = image;
        this.text = text;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

