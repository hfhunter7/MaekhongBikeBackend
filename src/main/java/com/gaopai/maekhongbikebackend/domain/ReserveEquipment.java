package com.gaopai.maekhongbikebackend.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reserve_equipment")
public class ReserveEquipment implements Serializable{

    private static final long serialVersionUID = -866336086820927697L;

    private ReserveEquipmentPK reserveEquipmentPK = new ReserveEquipmentPK();

    public ReserveEquipment(){

    }

    @EmbeddedId
    public ReserveEquipmentPK getReserveEquipmentPK() {
        return reserveEquipmentPK;
    }

    public void setReserveEquipmentPK(ReserveEquipmentPK reserveEquipmentPK) {
        this.reserveEquipmentPK = reserveEquipmentPK;
    }
}
