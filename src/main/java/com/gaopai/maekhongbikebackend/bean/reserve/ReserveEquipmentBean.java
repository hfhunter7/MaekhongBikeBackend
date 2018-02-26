package com.gaopai.maekhongbikebackend.bean.reserve;

public class ReserveEquipmentBean {
    private long equipment_id;

    public ReserveEquipmentBean(){

    }

    public ReserveEquipmentBean(long equipment_id){
        this.equipment_id = equipment_id;
    }

    public long getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(long equipment_id) {
        this.equipment_id = equipment_id;
    }
}
