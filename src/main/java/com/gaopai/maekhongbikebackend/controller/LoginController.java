package com.gaopai.maekhongbikebackend.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.LoginUsernameBean;
import com.gaopai.maekhongbikebackend.bean.LoginBean;
import com.gaopai.maekhongbikebackend.bean.RegisterBean;
import com.gaopai.maekhongbikebackend.domain.Trainer;
import com.gaopai.maekhongbikebackend.domain.Users;
import com.gaopai.maekhongbikebackend.exception.DataFormatException;
import com.gaopai.maekhongbikebackend.exception.ResourceNotFoundException;
import com.gaopai.maekhongbikebackend.service.JWTService;
import com.gaopai.maekhongbikebackend.service.LoginService;
import com.gaopai.maekhongbikebackend.utils.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@RestController
@RequestMapping(value = "/v1/login")
@Api(value = "login", description = "Login API")
public class LoginController extends AbstractRestHandler implements Serializable {

    private static final long serialVersionUID = 1470727646662631060L;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private JWTService jwtService;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/logout/{appName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Users Logout.", notes = "logout expire token")
    public @ResponseBody
    ResponseEntity<?> logout(
            @PathVariable String appName,
            @RequestHeader(value = "Authorization") String Authorization,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        log.info("login start");
        try {
            if (appName.toLowerCase().equals(Constant.APP_NAME.TRAINER)) {
                Trainer trainer = jwtService.verifyTokenTrainer(Authorization);
                loginService.logout(trainer);
            } else if (appName.toLowerCase().equals(Constant.APP_NAME.ADMIN)) {
                return null;
            } else {
                throw new DataFormatException("bad request appName");
            }

            return ResponseEntity.status(HttpStatus.OK).body(responseSuccessMessage());
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/token/{appName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Users information from login token.", notes = "Authenticate and authorize user that who can login to system. replace {appName} `trainee` for TestCenterTrainee and `trainer` for TestCenterTrainer")
    public @ResponseBody
    ResponseEntity<?> loginByToken(
            @PathVariable String appName,
            @RequestHeader(value = "Authorization") String Authorization,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        log.info("login start");
        try {
            ObjectNode responseBean;
            if (appName.toLowerCase().equals(Constant.APP_NAME.TRAINER)) {
                Trainer trainer = jwtService.verifyTokenTrainerExpire(Authorization);
                responseBean = loginService.loginTokenTrainer(trainer);
            } else if (appName.toLowerCase().equals(Constant.APP_NAME.USER)) {
                Users users = jwtService.verifyTokenUserExpire(Authorization);
                responseBean = loginService.loginTokenUser(users);
            } else if (appName.toLowerCase().equals(Constant.APP_NAME.ADMIN)) {
                return null;
            } else {
                throw new DataFormatException("bad request appName");
            }

            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{appName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Users information from login.", notes = "Authenticate and authorize user that who can login to system. replace {appName} `trainee` for TrainingCenterTrainee and `trainer` for TrainingCenterTrainer")
    public @ResponseBody
    ResponseEntity<?> login(
            @PathVariable String appName,
            @RequestBody LoginBean body,
            @RequestHeader(value = "Authorization") String Authorization,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        log.info("login start");
        try {
            jwtService.verifyAppToken(Authorization);
            ObjectNode responseBean = loginService.takeLoginGoogle(appName, body);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/user/{appName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Users information from login.", notes = "Authenticate and authorize user that who can login to system. replace {appName} `trainee` for TrainingCenterTrainee and `trainer` for TrainingCenterTrainer")
    public @ResponseBody
    ResponseEntity<?> loginWithUsername(
            @PathVariable String appName,
            @RequestBody LoginUsernameBean body,
            @RequestHeader(value = "Authorization") String Authorization,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        log.info("login start");
        try {
            jwtService.verifyAppToken(Authorization);
            ObjectNode responseBean = loginService.takeLoginWithUsername(appName, body);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
