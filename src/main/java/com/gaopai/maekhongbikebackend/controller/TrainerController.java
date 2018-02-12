package com.gaopai.maekhongbikebackend.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.bean.trainer.UpdateTrainerProfileBean;
import com.gaopai.maekhongbikebackend.domain.Trainer;
import com.gaopai.maekhongbikebackend.exception.ResourceNotFoundException;
import com.gaopai.maekhongbikebackend.service.JWTService;
import com.gaopai.maekhongbikebackend.service.TrainerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@RestController
@RequestMapping(value = "/v1/trainer")
@Api(value = "trainer", description = "Trainer API")
public class TrainerController extends AbstractRestHandler implements Serializable {


    private static final long serialVersionUID = -755279321707304595L;
    @Autowired
    private TrainerService trainerService;

    @Autowired
    private JWTService jwtService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "show all Trainer", notes = "show all Trainer")
    public @ResponseBody
    ResponseEntity<?> showAllTrainer(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            ArrayNode jsonNodes = trainerService.showAllTrainer();
            return ResponseEntity.status(HttpStatus.OK).body(jsonNodes);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @RequestMapping(value = "update-profile/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "update trainer profile", notes = "update trainer profile ")
    public @ResponseBody
    ResponseEntity<?> updatTrainerProfile(
            @RequestHeader(value = "Authorization") String Authorization,
            @RequestBody UpdateTrainerProfileBean body,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            Trainer trainer = jwtService.verifyTokenTrainer(Authorization);
            ObjectNode responseBean = trainerService.updateProfileTrainer(trainer, body);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}

