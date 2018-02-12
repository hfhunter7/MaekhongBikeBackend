package com.gaopai.maekhongbikebackend.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.bean.LoginBean;
import com.gaopai.maekhongbikebackend.bean.RegisterBean;
import com.gaopai.maekhongbikebackend.exception.ResourceNotFoundException;
import com.gaopai.maekhongbikebackend.service.JWTService;
import com.gaopai.maekhongbikebackend.service.LoginService;
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
@RequestMapping(value = "/v1/aut")
@Api(value = "aut", description = "Aut API")
public class AuthorizationController extends AbstractRestHandler implements Serializable {

    private static final long serialVersionUID = 1470727646662631060L;

    private static final Logger log = LoggerFactory.getLogger(AuthorizationController.class);

    @Autowired
    private JWTService jwtService;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login/{appName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Login and get aut", notes = "Login and get aut")
    public @ResponseBody
    ResponseEntity<?> createJWTEncode(
            @PathVariable String appName,
            @RequestBody LoginBean body,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            String token = loginService.loginAndGetTokenKey(appName, body.getEmail(), body.getGoogleId());
            String jwts = jwtService.generateToken(token, body.getEmail());
            ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
            jsonNodes.put("aut", jwts);
            return ResponseEntity.status(HttpStatus.OK).body(jsonNodes);
        } catch (ResourceNotFoundException e) {
            log.error("exception: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/loginWithUsername/{appName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Login and get aut", notes = "Login and get aut")
    public @ResponseBody
    ResponseEntity<?> createJWTEncodeWithUsername(
            @PathVariable String appName,
            @RequestBody RegisterBean body,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            String token = loginService.loginAndGetTokenKeyWithUsername(appName, body.getUsername(), body.getPassword());
            String jwts = jwtService.generateToken(token, body.getEmail());
            ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
            jsonNodes.put("aut", jwts);
            return ResponseEntity.status(HttpStatus.OK).body(jsonNodes);
        } catch (ResourceNotFoundException e) {
            log.error("exception: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Authorization", notes = "Get Authorization.")
    public @ResponseBody
    ResponseEntity<?> createJWT(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            String jwts = jwtService.generateToken("9nJmETiyIAYaVCAMoy", "testcenter@9picompany.com");
            ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
            jsonNodes.put("aut", jwts);
            return ResponseEntity.status(HttpStatus.OK).body(jsonNodes);
        } catch (ResourceNotFoundException e) {
            log.error("exception: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
