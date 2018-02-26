package com.gaopai.maekhongbikebackend.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.bean.reserve.CreateReserveBean;
import com.gaopai.maekhongbikebackend.domain.Users;
import com.gaopai.maekhongbikebackend.exception.ResourceNotFoundException;
import com.gaopai.maekhongbikebackend.service.JWTService;
import com.gaopai.maekhongbikebackend.service.ReserveService;
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
@RequestMapping(value = "/v1/reserve")
@Api(value = "reserve", description = "Reserves API")
public class ReserveController extends AbstractRestHandler implements Serializable{
    private static final long serialVersionUID = 4968157238077416074L;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private JWTService jwtService;


    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "user reserve trip", notes = "user reserve trip")
    public @ResponseBody
    ResponseEntity<?> reserve(
            @RequestHeader(value = "Authorization") String Authorization,
            @RequestBody CreateReserveBean body,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ObjectNode responseBean = reserveService.createReserve(user,body);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get reserve by user", notes = "Get reserve by user")
    public @ResponseBody
    ResponseEntity<?> getReserveByUser(
            @RequestHeader(value = "Authorization") String Authorization,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ArrayNode responseBean = reserveService.getReserveByUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get reserve detail by reserve id", notes = "Get reserve detail by reserve id")
    public @ResponseBody
    ResponseEntity<?> getReserveByReserveId(
            @RequestHeader(value = "Authorization") String Authorization,
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ObjectNode responseBean = reserveService.getReserveDetail(id,user);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
