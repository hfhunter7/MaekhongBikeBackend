package com.gaopai.maekhongbikebackend.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.gaopai.maekhongbikebackend.exception.ResourceNotFoundException;
import com.gaopai.maekhongbikebackend.service.EquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@RestController
@RequestMapping(value = "/v1/equipment")
@Api(value = "equipment", description = "Equipment API")
public class EquipmentController extends AbstractRestHandler implements Serializable {
    private static final long serialVersionUID = 7552381813608813776L;

    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "show all equipment",  notes = "show all equipment")
    public @ResponseBody
    ResponseEntity<?> showAllEquipment(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            ArrayNode jsonNodes = equipmentService.showAllEquipment();
            return ResponseEntity.status(HttpStatus.OK).body(jsonNodes);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
