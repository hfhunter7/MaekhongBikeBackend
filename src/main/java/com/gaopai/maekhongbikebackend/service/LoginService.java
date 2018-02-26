package com.gaopai.maekhongbikebackend.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.LoginUsernameBean;
import com.gaopai.maekhongbikebackend.bean.LoginBean;
import com.gaopai.maekhongbikebackend.bean.RegisterBean;
import com.gaopai.maekhongbikebackend.domain.*;
import com.gaopai.maekhongbikebackend.exception.DataFormatException;
import com.gaopai.maekhongbikebackend.repository.impl.TrainerRepositoryService;
import com.gaopai.maekhongbikebackend.repository.impl.UserRepositoryService;
import com.gaopai.maekhongbikebackend.service.json.LoginJson;
import com.gaopai.maekhongbikebackend.utils.Constant;
import com.gaopai.maekhongbikebackend.utils.DateUtil;
import com.gaopai.maekhongbikebackend.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class LoginService extends LoginJson {

    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private TrainerRepositoryService trainerRepositoryService;

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Autowired
    private TokenTrainerService tokenTrainerService;

    @Autowired
    private TokenUserService tokenUserService;

    public ObjectNode loginTokenTrainer(Trainer trainer) throws Exception {
        try {
            // login
            trainer.setLastLogin(DateUtil.getCurrentDateTime());
            trainerRepositoryService.update(trainer);

            TokenTrainer token = tokenTrainerService.generateToken(trainer.getEmail(), trainer);

            ObjectNode responseNode = returnDetailTrainerLogin(trainer);
            responseNode.put("token", token.getTokenKey());
            return responseNode;
        } catch (DataFormatException e) {
            throw new DataFormatException("invalid token trainer login");
        }
    }

    public ObjectNode loginTokenUser(Users users) throws Exception {
        try {
            // login
            users.setLastLogin(DateUtil.getCurrentDateTime());
            userRepositoryService.update(users);

            TokenUser token = tokenUserService.generateToken(users.getEmail(), users);

            ObjectNode responseNode = returnDetailUserLogin(users);
            responseNode.put("token", token.getTokenKey());
            return responseNode;
        } catch (DataFormatException e) {
            throw new DataFormatException("invalid token trainer login");
        }
    }

    public ObjectNode takeLoginGoogle(String appName, LoginBean loginBean) throws Exception {
        if (appName.toLowerCase().equals(Constant.APP_NAME.TRAINER)) {
            return loginTrainer(loginBean);
        } else if (appName.toLowerCase().equals(Constant.APP_NAME.ADMIN)) {
            return null;
        } else {
            throw new DataFormatException("bad request appName");
        }
    }

    public ObjectNode takeLoginWithUsername(String appName, LoginUsernameBean loginUsernameBean) throws Exception {
        if (appName.toLowerCase().equals(Constant.APP_NAME.USER)) {
            return loginUser(loginUsernameBean);
        } else {
            throw new DataFormatException("bad request appName");
        }
    }

    private ObjectNode loginTrainer(LoginBean loginBean) throws Exception {
        ObjectNode responseNode = new ObjectNode(JsonNodeFactory.instance);

        if (Utility.verifiedGoogleIdNotNull(loginBean.getGoogleId())) {
            Trainer trainer = trainerRepositoryService.findByEmailAndGoogleId(loginBean.getEmail(), loginBean.getGoogleId());

            if (trainer != null) {
                // login
                trainer.setLastLogin(DateUtil.getCurrentDateTime());
                trainerRepositoryService.update(trainer);
            } else {
                // register
                trainer = new Trainer();
                trainer.setEmail(loginBean.getEmail());
                trainer.setGoogleId(loginBean.getGoogleId());
                trainer.setImageUrl(loginBean.getImage_url());
                trainer.setName(loginBean.getName());
                trainer.setCreateDate(DateUtil.getCurrentDateTime());
                trainer.setModifyDate(DateUtil.getCurrentDateTime());
                trainer.setLastLogin(DateUtil.getCurrentDateTime());

                trainer = trainerRepositoryService.save(trainer);
            }

            TokenTrainer token = tokenTrainerService.generateToken(trainer.getEmail(), trainer);

            responseNode = returnDetailTrainerLogin(trainer);
            responseNode.put("token", token.getTokenKey());
        }

        return responseNode;
    }

    private ObjectNode loginUser(LoginUsernameBean loginUsernameBean) throws Exception {
        ObjectNode responseNode = new ObjectNode(JsonNodeFactory.instance);

        if (Utility.verifiedPasswordNotNull(loginUsernameBean.getPassword())) {
            Users users = userRepositoryService.findByUsernameAndPassword(loginUsernameBean.getUsername(), loginUsernameBean.getPassword());

            if (users != null) {
                // login
                users.setLastLogin(DateUtil.getCurrentDateTime());
                userRepositoryService.update(users);
            }

            TokenUser token = tokenUserService.generateToken(users.getEmail(), users);

            responseNode = returnDetailUserLogin(users);
            responseNode.put("token", token.getTokenKey());
        }

        return responseNode;
    }

    /**
     * @param appName  = trainer or trainee
     * @param email    = xxx@xxx.com
     * @param googleId = 0000000000
     * @return tokenKey
     * @throws DataFormatException bad request appName
     * @throws DataFormatException token not found
     */
    public String loginAndGetTokenKey(String appName, String email, String googleId) throws Exception {
        log.info("login and get tokenKey start");
        String tokenKey = "";

        if (appName.toLowerCase().equals(Constant.APP_NAME.TRAINER)) {
            Trainer trainer = trainerRepositoryService.findByEmailAndGoogleId(email, googleId);
            if (trainer == null) {
                throw new DataFormatException(Constant.RESPONSE.MSG.USER_NOT_FOUND);
            }
            TokenTrainer token = tokenTrainerService.generateToken(trainer.getEmail(), trainer);
            tokenKey = token.getTokenKey();
        } else if (appName.toLowerCase().equals(Constant.APP_NAME.ADMIN)) {
            return null;
        } else {
            throw new DataFormatException("bad request appName");
        }
        log.info("tokenKey: " + tokenKey);
        if (tokenKey.equals("")) {
            throw new DataFormatException("token not found");
        }

        return tokenKey;
    }

    public String loginAndGetTokenKeyWithUsername(String appName, String username, String password) throws Exception {
        log.info("login and get tokenKey start");
        String tokenKey = "";

        if (appName.toLowerCase().equals(Constant.APP_NAME.USER)) {
            Users user = userRepositoryService.findByUsernameAndPassword(username, password);
            if (user == null) {
                throw new DataFormatException(Constant.RESPONSE.MSG.USER_NOT_FOUND);
            }
            TokenUser token = tokenUserService.generateToken(user.getEmail(), user);
            tokenKey = token.getTokenKey();
        } else if (appName.toLowerCase().equals(Constant.APP_NAME.ADMIN)) {
            return null;
        } else {
            throw new DataFormatException("bad request appName");
        }
        log.info("tokenKey: " + tokenKey);
        if (tokenKey.equals("")) {
            throw new DataFormatException("token not found");
        }

        return tokenKey;
    }

    public void logout(Trainer trainer) {
        try {
            trainer.getToken().setExpireDate(expireToken());
            trainerRepositoryService.save(trainer);
        } catch (DataFormatException e) {
            throw new DataFormatException("Invalid Token.");
        }
    }

    public void logout(Users user) {
        try {
            user.getToken().setExpireDate(expireToken());
            userRepositoryService.save(user);
        } catch (DataFormatException e) {
            throw new DataFormatException("Invalid Token.");
        }
    }

    private Timestamp expireToken() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getCurrentDate());
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        return DateUtil.getTimeStamp(calendar.getTime());
    }
}
