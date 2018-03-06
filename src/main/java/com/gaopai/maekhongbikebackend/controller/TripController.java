package com.gaopai.maekhongbikebackend.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.bean.trip.CreateTripBean;
import com.gaopai.maekhongbikebackend.bean.trip.CreateTripImageBean;
import com.gaopai.maekhongbikebackend.bean.trip.UpdateTripBean;
import com.gaopai.maekhongbikebackend.bean.trip.UpdateTripImageBean;
import com.gaopai.maekhongbikebackend.domain.Users;
import com.gaopai.maekhongbikebackend.exception.ResourceNotFoundException;
import com.gaopai.maekhongbikebackend.repository.impl.TripImageRepositoryService;
import com.gaopai.maekhongbikebackend.service.JWTService;
import com.gaopai.maekhongbikebackend.service.TripService;
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
@RequestMapping(value = "/v1/trip")
@Api(value = "trip", description = "Trip API")
public class TripController extends AbstractRestHandler implements Serializable{

    private static final long serialVersionUID = 3377415481951665685L;
    @Autowired
    private JWTService jwtService;

    @Autowired
    private TripService tripService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "admin create trip", notes = "admin create trip")
    public @ResponseBody
    ResponseEntity<?> createTrip(
            @RequestHeader(value = "Authorization") String Authorization,
            @RequestBody CreateTripBean body,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ObjectNode responseBean = tripService.createTrip(body);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/trip-image/{trip_id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "admin create trip image", notes = "admin create trip image")
    public @ResponseBody
    ResponseEntity<?> createTripImage(
            @RequestHeader(value = "Authorization") String Authorization,
            @RequestBody CreateTripImageBean body,
            @PathVariable Long trip_id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ObjectNode responseBean = tripService.createTripImage(body , trip_id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/image/{trip_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "admin update trip image", notes = "admin update trip image")
    public @ResponseBody
    ResponseEntity<?> updateTripImage(
            @RequestHeader(value = "Authorization") String Authorization,
            @RequestBody UpdateTripImageBean body,
            @PathVariable Long trip_id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ObjectNode responseBean = tripService.updateTripImage(body , trip_id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{trip_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "admin update trip", notes = "admin update trip")
    public @ResponseBody
    ResponseEntity<?> updateTrip(
            @RequestHeader(value = "Authorization") String Authorization,
            @RequestBody UpdateTripBean body,
            @PathVariable Long trip_id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ObjectNode responseBean = tripService.updateTrip(body , trip_id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get trip by id", notes = "Get trip by id")
    public @ResponseBody
    ResponseEntity<?> getTripById(
            @RequestHeader(value = "Authorization") String Authorization,
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ObjectNode responseBean = tripService.getTripById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/trip-image/{trip_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get image trip by trip id", notes = "Get image trip by trip id")
    public @ResponseBody
    ResponseEntity<?> getImageTripByTripId(
            @RequestHeader(value = "Authorization") String Authorization,
            @PathVariable Long trip_id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ArrayNode responseBean = tripService.getTripImageByTripId(user,trip_id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all trip", notes = "Get all trip")
    public @ResponseBody
    ResponseEntity<?> getAllTrip(
            @RequestHeader(value = "Authorization") String Authorization,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ArrayNode responseBean = tripService.getAllTrip(user);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/image-url/{trip_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all trip", notes = "Get all trip")
    public @ResponseBody
    ResponseEntity<?> getAllImageUrlTrip(
            @RequestHeader(value = "Authorization") String Authorization,
            @PathVariable Long trip_id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ArrayNode responseBean = tripService.getTripImageByTripId(user , trip_id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/trip-image/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete trip image by id", notes = "delete trip image by id")
    public @ResponseBody
    ResponseEntity<?> deleteTripImageById(
            @RequestHeader(value = "Authorization") String Authorization,
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ObjectNode responseBean = tripService.deleteTripImageById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{trip_id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete trip by id", notes = "delete image by id")
    public @ResponseBody
    ResponseEntity<?> deleteTripById(
            @RequestHeader(value = "Authorization") String Authorization,
            @PathVariable Long trip_id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            Users user = jwtService.verifyTokenUser(Authorization);
            ObjectNode responseBean = tripService.deleteTripById(trip_id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
