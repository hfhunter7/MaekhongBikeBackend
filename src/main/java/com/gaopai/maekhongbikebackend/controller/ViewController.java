package com.gaopai.maekhongbikebackend.controller;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.bean.view.ViewBean;
import com.gaopai.maekhongbikebackend.exception.ResourceNotFoundException;
import com.gaopai.maekhongbikebackend.service.JWTService;
import com.gaopai.maekhongbikebackend.service.ViewService;
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
@RequestMapping(value = "/v1/view")
@Api(value = "view", description = "View API")
public class ViewController extends AbstractRestHandler implements Serializable {


    private static final long serialVersionUID = 5427544650438968353L;

    @Autowired
    private ViewService viewService;

    @Autowired
    private JWTService jwtService;


    @RequestMapping(value = "/{trainerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Show All View for Trainer", notes = "Show All View for Trainer")
    public @ResponseBody
    ResponseEntity<?> showViewForTrainer(
            @PathVariable Long  trainerId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            ObjectNode jsonNodes = viewService.createViewJson(trainerId);
            return ResponseEntity.status(HttpStatus.OK).body(jsonNodes);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @RequestMapping(value = "/{trainerId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add View", notes = "Add View")
    public @ResponseBody
    ResponseEntity<?> addView(
            @PathVariable Long  trainerId,
            @RequestBody ViewBean body,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            viewService.addView(trainerId,body);
            return ResponseEntity.status(HttpStatus.OK).body(responseSuccessMessage());

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
