package com.gaopai.maekhongbikebackend.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.bean.reserve.CreateReserveBean;
import com.gaopai.maekhongbikebackend.bean.reserve.ReserveEquipmentBean;
import com.gaopai.maekhongbikebackend.domain.*;
import com.gaopai.maekhongbikebackend.exception.DataFormatException;
import com.gaopai.maekhongbikebackend.repository.impl.EquipmentRepositoryService;
import com.gaopai.maekhongbikebackend.repository.impl.ReserveEquipmentRepositoryService;
import com.gaopai.maekhongbikebackend.repository.impl.ReserveRepositoryService;
import com.gaopai.maekhongbikebackend.utils.DateUtil;
import com.gaopai.maekhongbikebackend.utils.Utility;
import com.gaopai.maekhongbikebackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReserveService {
    private static final Logger log = LoggerFactory.getLogger(ReserveService.class);

    @Autowired
    private ReserveRepositoryService reserveRepositoryService;

    @Autowired
    private EquipmentRepositoryService equipmentRepositoryService;

    @Autowired
    private ReserveEquipmentRepositoryService reserveEquipmentRepositoryService;

    @Transactional
    public ObjectNode createReserve(Users user, CreateReserveBean body) throws Exception {
        Reserve reserve = new Reserve();
        reserve.setCreateBy(user.getUsername());
        reserve.setAdult(body.getAdult());
        reserve.setChild(body.getChild());
        reserve.setReserve_date(body.getReserve_date());
        reserve.setRent_status(body.getRent_status());
        reserve.setRoute(body.getRoute());
        reserve.setReserve_number(Utils.randomString(8).toUpperCase());

        try {
            reserve = reserveRepositoryService.save(reserve);

            if (body.getReserveEquipments() != null) {
                if (body.getReserveEquipments().size() != 0) {
                    List<ReserveEquipment> reserveEquipments = new ArrayList<>();
                    for (ReserveEquipmentBean reserveEquipmentBean : body.getReserveEquipments()) {
                        Equipment equipment = equipmentRepositoryService.find(reserveEquipmentBean.getEquipment_id());

                        ReserveEquipmentPK reserveEquipmentPK = new ReserveEquipmentPK();
                        reserveEquipmentPK.setReserve(reserve);
                        reserveEquipmentPK.setEquipment(equipment);

                        ReserveEquipment reserveEquipment = new ReserveEquipment();
                        reserveEquipment.setReserveEquipmentPK(reserveEquipmentPK);
                        reserveEquipments.add(reserveEquipment);
                    }

                    try {
                        reserveEquipmentRepositoryService.save(reserveEquipments);
                    } catch (DataFormatException e) {
                        throw new DataFormatException("set reserve equipment fail.");
                    }
                }
            }
            List<Equipment> equipments = equipmentRepositoryService.findByReserve(reserve);
            return createReserveJson(reserve, equipments);
        } catch (DataFormatException e) {
            throw new DataFormatException("create reserve fail.");
        }

    }

    public ArrayNode getReserveByUser(Users user) throws Exception {
        List<Reserve> reserves = reserveRepositoryService.findByCreateBy(user.getUsername());
        Utility.verifiedIsNullObject(reserves, "courses");

        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
        if (reserves.size() > 0) {
            for (Reserve reserve : reserves) {
                List<Equipment> equipmentList = equipmentRepositoryService.findByReserve(reserve);
                arrayNode.add(createReserveJson(reserve, equipmentList));
            }
        }
        return arrayNode;
    }

    public ObjectNode getReserveDetail(Long id, Users user) throws Exception {
        Reserve reserve = reserveRepositoryService.find(id);
        Utility.verifiedIsNullObject(reserve, "course");
        List<Equipment> equipmentList = equipmentRepositoryService.findByReserve(reserve);
        ObjectNode jsonNodes = createReserveJson(reserve, equipmentList, user);

        return jsonNodes;
    }

    private ObjectNode createReserveJson(Reserve reserve, List<Equipment> equipments) {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("id", reserve.getId());
        jsonNodes.put("create_by", reserve.getCreateBy());
        jsonNodes.put("adult", reserve.getAdult());
        jsonNodes.put("child", reserve.getChild());
        jsonNodes.put("rent_status", reserve.getRent_status());
        jsonNodes.put("reserve_date", reserve.getReserve_date());
        jsonNodes.put("route", reserve.getRoute());
        jsonNodes.put("reserve_number", reserve.getReserve_number());

        jsonNodes.set("equipments", createEquipmentArrayNode(equipments));

        return jsonNodes;
    }

    private ObjectNode createReserveJson(Reserve reserve, List<Equipment> equipments, Users user) {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("id", reserve.getId());
        jsonNodes.put("create_by", reserve.getCreateBy());
        jsonNodes.put("adult", reserve.getAdult());
        jsonNodes.put("child", reserve.getChild());
        jsonNodes.put("rent_status", reserve.getRent_status());
        jsonNodes.put("reserve_date", String.valueOf(reserve.getReserve_date()));
        jsonNodes.put("route", reserve.getRoute());
        jsonNodes.put("reserve_number", reserve.getReserve_number());

        jsonNodes.set("equipments", createEquipmentArrayNode(equipments));

        ObjectNode userArrayNode = new ObjectNode(JsonNodeFactory.instance);
        userArrayNode.put("username", user.getUsername());
        userArrayNode.put("name", user.getName());
        userArrayNode.put("email", user.getEmail());
        userArrayNode.put("phone_number", user.getPhoneNumber());

        jsonNodes.set("user", userArrayNode);

        return jsonNodes;
    }

    private ArrayNode createEquipmentArrayNode(List<Equipment> equipmentList) {
        ArrayNode equipmentArrayNode = new ArrayNode(JsonNodeFactory.instance);
        for (Equipment equipment : equipmentList) {
            ObjectNode equipmentNode = new ObjectNode(JsonNodeFactory.instance);
            equipmentNode.put("id", equipment.getId());
            equipmentNode.put("name", equipment.getName());
            equipmentNode.put("price", String.valueOf(equipment.getPrice()));
            equipmentArrayNode.add(equipmentNode);
        }
        return equipmentArrayNode;
    }
}
