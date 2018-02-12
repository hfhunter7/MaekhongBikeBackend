package com.gaopai.maekhongbikebackend.service;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.bean.trainer.CreateBankAccount;
import com.gaopai.maekhongbikebackend.bean.trainer.UpdateTrainerAddressBean;
import com.gaopai.maekhongbikebackend.bean.trainer.UpdateTrainerProfileBean;
import com.gaopai.maekhongbikebackend.domain.Trainer;
import com.gaopai.maekhongbikebackend.exception.DataFormatException;
import com.gaopai.maekhongbikebackend.repository.impl.TrainerRepositoryService;
import com.gaopai.maekhongbikebackend.service.json.LoginJson;
import com.gaopai.maekhongbikebackend.utils.DateUtil;
import com.gaopai.maekhongbikebackend.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService extends LoginJson {

    private static final Logger log = LoggerFactory.getLogger(TrainerService.class);

    @Autowired
    private TrainerRepositoryService trainerRepositoryService;

    public Long updateTrainerAddress(Long id) throws Exception {
        Trainer trainer = trainerRepositoryService.find(id);
        try {
            trainerRepositoryService.updateAddress(trainer);
        } catch (DataFormatException e) {
            throw new DataFormatException("update Choice activity setting fail.");
        }

        return trainer.getId();
    }

    public ArrayNode showAllTrainer() throws Exception {
        List<Trainer> trainers = trainerRepositoryService.findAll();
        Utility.verifiedIsNullObject(trainers, "Trainer");

        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
        if (trainers.size() > 0) {
            for (Trainer trainer : trainers) {
                arrayNode.add(createTrainerJson(trainer));
            }
        }
        return arrayNode;
    }

    private ObjectNode createTrainerJson(Trainer trainer) {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("id", trainer.getId());
        jsonNodes.put("Email", trainer.getEmail());
        jsonNodes.put("Name", trainer.getName());
        jsonNodes.put("Birth Day", trainer.getBirthDate());
        jsonNodes.put("Company", trainer.getCompanyName());
        jsonNodes.put("Google Id", trainer.getGoogleId());
        jsonNodes.put("Phone", trainer.getPhone());
        jsonNodes.put("createdate", DateUtil.toStringEngDateTime(trainer.getCreateDate(), "dd/MM/yyyy HH:mm:ss"));
        jsonNodes.put("modifydate", DateUtil.toStringEngDateTime(trainer.getModifyDate(), "dd/MM/yyyy HH:mm:ss"));
        jsonNodes.put("lastlogin", DateUtil.toStringEngDateTime(trainer.getLastLogin(), "dd/MM/yyyy HH:mm:ss"));
        return jsonNodes;
    }

    public ObjectNode updateProfileTrainer(Trainer trainer, UpdateTrainerProfileBean updateTrainerProfileBean) throws Exception {
        ObjectNode responseNode = new ObjectNode(JsonNodeFactory.instance);
        if (trainer != null) {
            trainer.setDisplayName(updateTrainerProfileBean.getDisplayName());
            trainer.setName(updateTrainerProfileBean.getName());
            trainer.setBirthDate(updateTrainerProfileBean.getBirthday());
            trainer.setPhone(updateTrainerProfileBean.getPhoneNumber());
            trainer.setTitle(updateTrainerProfileBean.getTitle());
            trainer.setCompanyName(updateTrainerProfileBean.getCompanyName());
            trainerRepositoryService.update(trainer);
            responseNode = returnDetailTrainerLogin(trainer);
        }
        return responseNode;
    }
}
