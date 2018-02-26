package com.gaopai.maekhongbikebackend.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class ReserveEquipmentPK implements Serializable{
    private static final long serialVersionUID = 527860208244734990L;

    private Reserve reserve;

    private Equipment equipment;

    public ReserveEquipmentPK(){

    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Reserve getReserve() {
        return reserve;
    }

    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
