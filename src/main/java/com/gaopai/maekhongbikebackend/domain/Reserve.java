package com.gaopai.maekhongbikebackend.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "reserve")
public class Reserve implements Serializable{

    private static final long serialVersionUID = 6750960102372922870L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String route;
    private String reserve_date;
    private long adult;
    private long child;
    private String rent_status;
    private String reserve_number;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    public Reserve(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getReserve_date() {
        return reserve_date;
    }

    public void setReserve_date(String reserve_date) {
        this.reserve_date = reserve_date;
    }

    public long getAdult() {
        return adult;
    }

    public void setAdult(long adult) {
        this.adult = adult;
    }

    public long getChild() {
        return child;
    }

    public void setChild(long child) {
        this.child = child;
    }

    public String getRent_status() {
        return rent_status;
    }

    public void setRent_status(String rent_status) {
        this.rent_status = rent_status;
    }

    public String getReserve_number() {
        return reserve_number;
    }

    public void setReserve_number(String reserve_number) {
        this.reserve_number = reserve_number;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
