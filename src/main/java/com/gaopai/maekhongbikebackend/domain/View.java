package com.gaopai.maekhongbikebackend.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "view")
public class View implements Serializable {


    private static final long serialVersionUID = 2061589742158499556L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long trainerId;

    private Long courseId;

    private Long customerId;


    public View(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
