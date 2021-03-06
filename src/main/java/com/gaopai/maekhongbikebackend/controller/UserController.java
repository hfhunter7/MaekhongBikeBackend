package com.gaopai.maekhongbikebackend.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.bean.RegisterBean;
import com.gaopai.maekhongbikebackend.bean.UpdateUserProfileBean;
import com.gaopai.maekhongbikebackend.domain.Users;
import com.gaopai.maekhongbikebackend.exception.ResourceNotFoundException;
import com.gaopai.maekhongbikebackend.service.JWTService;
import com.gaopai.maekhongbikebackend.service.UserService;
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
@RequestMapping(value = "/v1/user")
@Api(value = "user", description = "Users API")
public class UserController extends AbstractRestHandler implements Serializable{
    private static final long serialVersionUID = -1889826984512687234L;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "register user", notes = "register user")
    public @ResponseBody
    ResponseEntity<?> register(
            @RequestBody RegisterBean body,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            ObjectNode responseBean = userService.createUser(body);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "update-profile/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "update user profile", notes = "update user profile ")
    public @ResponseBody
    ResponseEntity<?> updatTrainerProfile(
            @RequestHeader(value = "Authorization") String Authorization,
            @RequestBody UpdateUserProfileBean body,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            Users user = jwtService.verifyTokenUser(Authorization);
            ObjectNode responseBean = userService.updateProfileTrainer(user, body);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
