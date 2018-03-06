package com.gaopai.maekhongbikebackend.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "trip")
public class Trip implements Serializable{

    private static final long serialVersionUID = 502257821306372953L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String trip_name;
    private String description;
    private String image_preview;
    private String filename;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TripImage> tripImages;

    public Trip(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TripImage> getTripImages() {
        return tripImages;
    }

    public void setTripImages(List<TripImage> tripImages) {
        this.tripImages = tripImages;
    }

    public String getImage_preview() {
        return image_preview;
    }

    public void setImage_preview(String image_preview) {
        this.image_preview = image_preview;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
