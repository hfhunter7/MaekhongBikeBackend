package com.gaopai.maekhongbikebackend.bean.trip;

public class UpdateTripImageBean {
    private String image_url;
    private String filename;

    public UpdateTripImageBean(){

    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
