package com.gaopai.maekhongbikebackend.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.bean.reserve.*;
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
import springfox.documentation.spring.web.json.Json;

import java.util.*;

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
        reserve.setUser(user);
        reserve.setAdult(body.getAdult());
        reserve.setChild(body.getChild());
        reserve.setReserve_date(body.getReserve_date());
        reserve.setRent_status(body.getRent_status());
        reserve.setRoute(body.getRoute());
        reserve.setReserve_number(Utils.randomString(8).toUpperCase());
        reserve.setStatus_payment(body.getStatus_payment());


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

            Double price = 0.0;

            if ("เช่าอุปกรณ์".equals(body.getRent_status())) {
                for (Equipment equipment : equipments) {
                    price += equipment.getPrice();
                }
                double price_reserve = ((body.getChild() + body.getAdult()) * price) + ((body.getChild() + body.getAdult()) * 300);
                reserve.setPrice(price_reserve);
            } else {
                double price_reserve = (body.getChild() + body.getAdult()) * 300;
                reserve.setPrice(price_reserve);
            }

            reserveRepositoryService.update(reserve);

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

    @Transactional
    public ArrayNode updateStatusPayment(UpdateStatusPaymentBean body, Long reserve_id, Users user) throws Exception {
        Reserve res = reserveRepositoryService.find(reserve_id);

        res.setStatus_payment(body.getStatus_payment());

        try {
            reserveRepositoryService.update(res);
            List<Reserve> reserves = reserveRepositoryService.findAll();
            ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
            if (reserves.size() > 0) {
                for (Reserve reserve : reserves) {
                    List<Equipment> equipmentList = equipmentRepositoryService.findByReserve(reserve);
                    arrayNode.add(createReserveJson(reserve, equipmentList));
                }
            }
            return arrayNode;
        } catch (DataFormatException e) {
            throw new DataFormatException("update reserve child fail.");
        }

    }

    @Transactional
    public ObjectNode updateChild(UpdateChildBean body, Long reserve_id) throws Exception {
        Reserve res = reserveRepositoryService.find(reserve_id);

        res.setChild(body.getChild());

        List<Equipment> equipments = equipmentRepositoryService.findByReserve(res);
        Double price = 0.0;

        if ("เช่าอุปกรณ์".equals(res.getRent_status())) {
            for (Equipment equipment : equipments) {
                price += equipment.getPrice();
            }
            double price_reserve = ((body.getChild() + res.getAdult()) * price) + ((body.getChild() + res.getAdult()) * 300);
            res.setPrice(price_reserve);
        } else {
            double price_reserve = (body.getChild() + res.getAdult()) * 300;
            res.setPrice(price_reserve);
        }

        try {
            reserveRepositoryService.update(res);

            ObjectNode jsonNodes = createReserveJson(res, equipments);

            return jsonNodes;
        } catch (DataFormatException e) {
            throw new DataFormatException("update reserve adult fail.");
        }

    }

    @Transactional
    public ObjectNode updateAdult(UpdateAdultBean body, Long reserve_id) throws Exception {
        Reserve res = reserveRepositoryService.find(reserve_id);

        res.setAdult(body.getAdult());

        List<Equipment> equipments = equipmentRepositoryService.findByReserve(res);
        Double price = 0.0;

        if ("เช่าอุปกรณ์".equals(res.getRent_status())) {
            for (Equipment equipment : equipments) {
                price += equipment.getPrice();
            }
            double price_reserve = ((res.getChild() + body.getAdult()) * price) + ((res.getChild() + body.getAdult()) * 300);
            res.setPrice(price_reserve);
        } else {
            double price_reserve = (res.getChild() + body.getAdult()) * 300;
            res.setPrice(price_reserve);
        }

        try {
            reserveRepositoryService.update(res);

            ObjectNode jsonNodes = createReserveJson(res, equipments);

            return jsonNodes;
        } catch (DataFormatException e) {
            throw new DataFormatException("update reserve status payment fail.");
        }

    }

    public ArrayNode getAllReserves() throws Exception {
        List<Reserve> reserves = reserveRepositoryService.findAll();
        Utility.verifiedIsNullObject(reserves, "reserves");

        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
        if (reserves.size() > 0) {
            for (Reserve reserve : reserves) {
                List<Equipment> equipmentList = equipmentRepositoryService.findByReserve(reserve);
                arrayNode.add(createReserveJson(reserve, equipmentList));
            }
        }
        return arrayNode;
    }

    public ObjectNode getReserveStat() throws Exception {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        List<Reserve> reserves = reserveRepositoryService.findAll();
        Utility.verifiedIsNullObject(reserves, "reserves");

        List<String> reserveDateList = new ArrayList<>();

        HashMap<String, Double> stat = new HashMap<>();

        List<Double> stat_price = new ArrayList<>();
        HashMap<String, List<Double>> price_reserve = new HashMap<>();
        HashMap<String, List<Integer>> count_reserve = new HashMap<>();
        HashMap<String, List<String>> date_reserve = new HashMap<>();
        //ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);

        List<Integer> counts = new ArrayList<>();
        String[] m = {};
        double price;
        int count;
        for (String mon : months) {
            price = 0.0;
            count = 0;
            String month = "";
            for (Reserve reserve : reserves) {
                m = reserve.getReserve_date().split("-");
                if (mon.equals(m[1])) {
                    month = m[1];
                    price += reserve.getPrice();
                    count += 1;
                }
            }
            if (!"".equals(month)) {
                reserveDateList.add(month);
            }
            if (price != 0.0) {
                stat_price.add(price);
            }
            if (count != 0) {
                counts.add(count);
            }
        }

        price_reserve.put("price", stat_price);
        date_reserve.put("date", reserveDateList);
        count_reserve.put("count", counts);

        ArrayNode arrayPriceNode = createReserveStatJson(price_reserve , date_reserve, count_reserve);

        jsonNodes.set("stat" , arrayPriceNode);

        return jsonNodes;
    }

    private ArrayNode createReserveStatJson(HashMap<String, List<Double>> price_reserve , HashMap<String, List<String>> date_reserve ,HashMap<String, List<Integer>> count_reserve) {
        ArrayNode priceArrNode = new ArrayNode(JsonNodeFactory.instance);
        List<Double> priceList = (ArrayList) price_reserve.get("price");
        List<String> dateList = (ArrayList) date_reserve.get("date");
        List<Integer> countList = (ArrayList) count_reserve.get("count");

        List<String> monthJson = new ArrayList<>();

        for(int i = 0; i < getListMonth().size() ;i++){
            for(String s : dateList){
                if(s.equals(""+(i+1))){
                    monthJson.add(getListMonth().get(i));
                }
            }
        }

        for (int i = 0; i < priceList.size(); i++) {
            ObjectNode priceNode = new ObjectNode(JsonNodeFactory.instance);
            priceNode.put("date", monthJson.get(i));
            priceNode.put("จำนวนการจอง" , countList.get(i));
            priceNode.put("ยอดเงินจากการจอง (บาท)", priceList.get(i));
            priceArrNode.add(priceNode);
        }

        return priceArrNode;
    }

    public ObjectNode getReserveDetail(Long id, Users user) throws Exception {
        Reserve reserve = reserveRepositoryService.find(id);
        Utility.verifiedIsNullObject(reserve, "reserve");
        List<Equipment> equipmentList = equipmentRepositoryService.findByReserve(reserve);
        ObjectNode jsonNodes = createReserveJson(reserve, equipmentList);

        return jsonNodes;
    }

    public List<String> getListMonth(){
        List<String> monthList = new ArrayList<>();
        String january = "January";
        String february = "February";
        String march = "March";
        String april = "April";
        String may = "May";
        String june = "June";
        String july = "July";
        String august = "August";
        String september = "September";
        String october = "October";
        String november = "November";
        String december = "December";

        monthList.add(january);
        monthList.add(february);
        monthList.add(march);
        monthList.add(april);
        monthList.add(may);
        monthList.add(june);
        monthList.add(july);
        monthList.add(august);
        monthList.add(september);
        monthList.add(october);
        monthList.add(november);
        monthList.add(december);

        return monthList;
    }

    private ObjectNode createReserveJson(Reserve reserve, List<Equipment> equipments) {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("id", reserve.getId());
        jsonNodes.put("create_by", reserve.getUser().getName());
        jsonNodes.put("adult", reserve.getAdult());
        jsonNodes.put("child", reserve.getChild());
        jsonNodes.put("rent_status", reserve.getRent_status());
        jsonNodes.put("reserve_date", reserve.getReserve_date());
        jsonNodes.put("route", reserve.getRoute());
        jsonNodes.put("reserve_number", reserve.getReserve_number());
        jsonNodes.put("status_payment", reserve.getStatus_payment());
        jsonNodes.put("price", reserve.getPrice());

        jsonNodes.set("equipments", createEquipmentArrayNode(equipments));

        ObjectNode userNode = new ObjectNode(JsonNodeFactory.instance);
        userNode.put("username", reserve.getUser().getUsername());
        userNode.put("name", reserve.getUser().getName());
        userNode.put("email", reserve.getUser().getEmail());
        userNode.put("phone_number", reserve.getUser().getPhoneNumber());

        jsonNodes.set("user", userNode);

        return jsonNodes;
    }

    private ObjectNode createReserveJson(Reserve reserve, List<Equipment> equipments, Users user) {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("id", reserve.getId());
        jsonNodes.put("create_by", reserve.getUser().getName());
        jsonNodes.put("adult", reserve.getAdult());
        jsonNodes.put("child", reserve.getChild());
        jsonNodes.put("rent_status", reserve.getRent_status());
        jsonNodes.put("reserve_date", String.valueOf(reserve.getReserve_date()));
        jsonNodes.put("route", reserve.getRoute());
        jsonNodes.put("reserve_number", reserve.getReserve_number());
        jsonNodes.put("price", reserve.getPrice());

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

    public void deleteReserveById(Long id, Users user) {
        Reserve reserve = reserveRepositoryService.find(id);
        Equipment equipment = null;

        if (reserve == null) {
            throw new DataFormatException("invalid id");
        }

        try {
            List<Equipment> equipmentList = equipmentRepositoryService.findByReserve(reserve);
            List<ReserveEquipment> reserveEquipments = reserveEquipmentRepositoryService.findByReserve(reserve);

            try {
                for (ReserveEquipment reserveEquipment : reserveEquipments) {
                    for (Equipment eq : equipmentList) {
                        if (reserveEquipment.getReserveEquipmentPK().getEquipment().getId() == eq.getId()) {
                            equipment = reserveEquipment.getReserveEquipmentPK().getEquipment();
                        }
                    }
                    reserveEquipmentRepositoryService.deleteReserveEquipment(reserve.getId(), equipment.getId());
                }
            } catch (DataFormatException e) {
                throw new DataFormatException("delete reserve equipment fail");
            }


            reserveRepositoryService.delete(reserve);
        } catch (DataFormatException e) {
            throw new DataFormatException("delete reserve fail.");
        }
    }
}
