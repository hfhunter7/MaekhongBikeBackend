package com.gaopai.maekhongbikebackend.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.bean.view.ViewBean;
import com.gaopai.maekhongbikebackend.domain.View;
import com.gaopai.maekhongbikebackend.exception.DataFormatException;
import com.gaopai.maekhongbikebackend.repository.impl.ViewRepositoryService;
import com.gaopai.maekhongbikebackend.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewService {

    @Autowired
    private ViewRepositoryService viewRepositoryService;

    public void addView(Long trainerId,ViewBean body) throws Exception {
        View view = new View();
        Utility.verifiedIsNullObject(view, "view");
        view.setCourseId(body.getCourseId());
        view.setCustomerId(body.getCustomerId());
        view.setTrainerId(trainerId);

        try {
            viewRepositoryService.save(view);
        } catch (DataFormatException e) {
            throw new DataFormatException("add view fail.");
        }
    }


    public ObjectNode createViewJson(Long trainerId) {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        Long sum = viewRepositoryService.findSumView(trainerId);
        jsonNodes.put("sum", sum);
        return jsonNodes;
    }

}
