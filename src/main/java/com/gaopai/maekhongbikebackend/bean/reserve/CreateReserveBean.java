package com.gaopai.maekhongbikebackend.bean.reserve;

import java.util.List;

public class CreateReserveBean {
    private String reserve_date;
    private String route;
    private long adult;
    private long child;
    private String rent_status;
    private String status_payment;
    private List<ReserveEquipmentBean> reserveEquipments;

    public CreateReserveBean(){

    }

    public String getReserve_date() {
        return reserve_date;
    }

    public void setReserve_date(String reserve_date) {
        this.reserve_date = reserve_date;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRent_status() {
        return rent_status;
    }

    public void setRent_status(String rent_status) {
        this.rent_status = rent_status;
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

    public List<ReserveEquipmentBean> getReserveEquipments() {
        return reserveEquipments;
    }

    public void setReserveEquipments(List<ReserveEquipmentBean> reserveEquipments) {
        this.reserveEquipments = reserveEquipments;
    }

    public String getStatus_payment() {
        return status_payment;
    }

    public void setStatus_payment(String status_payment) {
        this.status_payment = status_payment;
    }
}
