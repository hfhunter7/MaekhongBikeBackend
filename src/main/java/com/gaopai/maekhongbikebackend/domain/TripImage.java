package com.gaopai.maekhongbikebackend.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "trip_image")
public class TripImage implements Serializable{

    private static final long serialVersionUID = 6981794111576106015L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String image_url;
    private String filename;

    @ManyToOne(fetch = FetchType.LAZY)
    private Trip trip;

    public TripImage(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
