package com.gaopai.maekhongbikebackend.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.domain.Equipment;
import com.gaopai.maekhongbikebackend.repository.impl.EquipmentRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepositoryService equipmentRepositoryService;

    public ArrayNode showAllEquipment() {
        List<Equipment> equipmentList = equipmentRepositoryService.findAll();

        ArrayNode equipmentArrayNode = new ArrayNode(JsonNodeFactory.instance);
        if (equipmentList.size() > 0) {
            for (Equipment equipment : equipmentList) {
                ObjectNode equipmentNodes = new ObjectNode(JsonNodeFactory.instance);
                equipmentNodes.put("id", equipment.getId());
                equipmentNodes.put("name", equipment.getName());
                equipmentNodes.put("price", equipment.getPrice());
                equipmentArrayNode.add(equipmentNodes);
            }
        }

        return equipmentArrayNode;
    }
}
