package com.gaopai.maekhongbikebackend.service;

import com.gaopai.maekhongbikebackend.domain.TokenUser;
import com.gaopai.maekhongbikebackend.domain.Users;
import com.gaopai.maekhongbikebackend.exception.DataFormatException;
import com.gaopai.maekhongbikebackend.repository.impl.TokenUserRepositoryService;
import com.gaopai.maekhongbikebackend.utils.Constant;
import com.gaopai.maekhongbikebackend.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Calendar;

@Service
public class TokenUserService {
    private static final Logger log = LoggerFactory.getLogger(TokenUserService.class);

    @Autowired
    private TokenUserRepositoryService tokenUserRepositoryService;

    public TokenUser getTokenTrainer(String tokenKey) throws Exception {
        TokenUser token = tokenUserRepositoryService.findTokenByTokenString(tokenKey);
        return token;
    }

    public TokenUser findTokenTrainerByTokenString(String tokenKey) {
        return tokenUserRepositoryService.findTokenByTokenString(tokenKey);
    }

    public TokenUser generateToken(String username, Users users) throws Exception {
        log.info("generateToken(email, users) start");
        TokenUser resultToken = null;
        String temp = username + "|" + System.currentTimeMillis();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(temp.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        String tokenString = sb.toString();

        TokenUser oldToken = this.tokenUserRepositoryService.getTokenByUsername(users.getUsername());

        boolean isTokenExpired = false;

        if (null != oldToken) {
            isTokenExpired = oldToken.getExpireDate().before(DateUtil.getCurrentDateTime());
            log.info("isTokenExpired " + isTokenExpired);
            if (isTokenExpired) {
                resultToken = oldToken;

                resultToken.setTokenKey(tokenString);
                resultToken.setUsers(users);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtil.getCurrentDate());
                calendar.add(Calendar.DAY_OF_YEAR, 3);
                resultToken.setExpireDate(DateUtil.getTimeStamp(calendar.getTime()));

                this.tokenUserRepositoryService.save(resultToken);

            } else {
                resultToken = oldToken;

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtil.getCurrentDate());
                calendar.add(Calendar.DAY_OF_YEAR, 3);
                resultToken.setExpireDate(DateUtil.getTimeStamp(calendar.getTime()));

                this.tokenUserRepositoryService.save(resultToken);
            }
        } else {
            isTokenExpired = true;
            TokenUser resultTokens = new TokenUser();

            resultTokens.setCreateDate(DateUtil.getCurrentDateTime());
            resultTokens.setTokenKey(tokenString);
            resultTokens.setUsers(users);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtil.getCurrentDate());
            calendar.add(Calendar.DAY_OF_YEAR, 3);
            resultTokens.setExpireDate(DateUtil.getTimeStamp(calendar.getTime()));

            resultToken = this.tokenUserRepositoryService.save(resultTokens);
        }

        log.info("generateToken(email, users) end");
        return resultToken;
    }

    public boolean validateTokenUser(final String tokenString) {
        boolean isValidate = false;
        TokenUser token = tokenUserRepositoryService.findTokenByTokenString(tokenString);
        if (null != token) {
            isValidate = true;
        } else {
            throw new DataFormatException(Constant.RESPONSE.MSG.INVALID_TOKEN);
        }

        return isValidate;
    }

    public boolean isExpiredTokenTrainer(final TokenUser token) {
        boolean isExpired = false;
//		isExpired = token.getExpiredate().before(DateUtil.getCurrentDateTime());
        return isExpired;
    }

    public boolean isValidateAndNotExpiredTokenTrainer(final String tokenString) {
        boolean isValidate = validateTokenUser(tokenString);
        boolean isExpired = false;
        if (isValidate) {
            TokenUser token = tokenUserRepositoryService.findTokenByTokenString(tokenString);
            isExpired = isExpiredTokenTrainer(token);
        } else {
            return false;
        }

        return !isExpired;
    }
}
