package com.gaopai.maekhongbikebackend.bean;

public class LoginBean {

    private String email;

    private String googleId;

    private String image_url;

    private String name;

    public LoginBean() {

    }

    public LoginBean(String email, String googleId) {
        this.email = email;
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
