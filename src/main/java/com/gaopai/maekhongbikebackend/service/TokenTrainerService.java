package com.gaopai.maekhongbikebackend.service;

import com.gaopai.maekhongbikebackend.domain.Trainer;
import com.gaopai.maekhongbikebackend.domain.TokenTrainer;
import com.gaopai.maekhongbikebackend.exception.DataFormatException;
import com.gaopai.maekhongbikebackend.repository.impl.TokenTrainerRepositoryService;
import com.gaopai.maekhongbikebackend.utils.Constant;
import com.gaopai.maekhongbikebackend.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Calendar;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class TokenTrainerService {

	private static final Logger log = LoggerFactory.getLogger(TokenTrainerService.class);

	@Autowired
	private TokenTrainerRepositoryService tokenTrainerRepositoryService;

	public TokenTrainerService() {
		
	}

	public boolean validateTokenTrainer(final String tokenString) {
		boolean isValidate = false;
		TokenTrainer token = tokenTrainerRepositoryService.findTokenByTokenString(tokenString);
		if (null != token) {
			isValidate = true;
		} else {
			throw new DataFormatException(Constant.RESPONSE.MSG.INVALID_TOKEN);
		}

		return isValidate;
	}

	public boolean isExpiredTokenTrainer(final TokenTrainer token) {
		boolean isExpired = false;
//		isExpired = token.getExpiredate().before(DateUtil.getCurrentDateTime());
		return isExpired;
	}
	

	public boolean isValidateAndNotExpiredTokenTrainer(final String tokenString) {
		boolean isValidate = validateTokenTrainer(tokenString);
		boolean isExpired = false;
		if (isValidate) {
			TokenTrainer token = tokenTrainerRepositoryService.findTokenByTokenString(tokenString);
			isExpired = isExpiredTokenTrainer(token);
		} else {
			return false;
		}

		return !isExpired;
	}
	
	public TokenTrainer getTokenTrainer(String tokenKey) throws Exception {
		TokenTrainer token = tokenTrainerRepositoryService.findTokenByTokenString(tokenKey);
		return token;
	}

	public TokenTrainer findTokenTrainerByTokenString(String tokenKey) {
		return tokenTrainerRepositoryService.findTokenByTokenString(tokenKey);
	}

	public TokenTrainer generateToken(String email, Trainer user) throws Exception {
		log.info("generateToken(email, user) start");
		TokenTrainer resultToken = null;
		String temp = email + "|" + System.currentTimeMillis();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(temp.getBytes());

		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		String tokenString = sb.toString();

		TokenTrainer oldToken = this.tokenTrainerRepositoryService.getTokenByUserId(user.getId());

		boolean isTokenExpired = false;

		if (null != oldToken) {
			isTokenExpired = oldToken.getExpireDate().before(DateUtil.getCurrentDateTime());
			log.info("isTokenExpired " + isTokenExpired);
			if (isTokenExpired) {
				resultToken = oldToken;

				resultToken.setTokenKey(tokenString);
				resultToken.setTrainer(user);
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(DateUtil.getCurrentDate());
				calendar.add(Calendar.DAY_OF_YEAR, 3);
				resultToken.setExpireDate(DateUtil.getTimeStamp(calendar.getTime()));

				this.tokenTrainerRepositoryService.save(resultToken);

			} else {
				resultToken = oldToken;
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(DateUtil.getCurrentDate());
				calendar.add(Calendar.DAY_OF_YEAR, 3);
				resultToken.setExpireDate(DateUtil.getTimeStamp(calendar.getTime()));

				this.tokenTrainerRepositoryService.save(resultToken);
			}
		} else {
			isTokenExpired = true;
			TokenTrainer resultTokens = new TokenTrainer();

			resultTokens.setCreateDate(DateUtil.getCurrentDateTime());
			resultTokens.setTokenKey(tokenString);
			resultTokens.setTrainer(user);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtil.getCurrentDate());
			calendar.add(Calendar.DAY_OF_YEAR, 3);
			resultTokens.setExpireDate(DateUtil.getTimeStamp(calendar.getTime()));

			resultToken = this.tokenTrainerRepositoryService.save(resultTokens);
		}

		log.info("generateToken(email, user) end");
		return resultToken;
	}
	
	public TokenTrainer refreshToken(String email, Trainer user) throws Exception {
		log.info("refreshToken(email, user) start");
		TokenTrainer resultToken = null;
		String temp = email + "|" + System.currentTimeMillis();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(temp.getBytes());

		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		String tokenString = sb.toString();
		
		TokenTrainer resultTokens = this.tokenTrainerRepositoryService.getTokenByUserId(user.getId());

		resultTokens.setCreateDate(DateUtil.getCurrentDateTime());
		resultTokens.setTokenKey(tokenString);
		resultTokens.setTrainer(user);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.getCurrentDate());
		calendar.add(Calendar.DAY_OF_YEAR, 3);
		resultTokens.setExpireDate(DateUtil.getTimeStamp(calendar.getTime()));

		resultToken = this.tokenTrainerRepositoryService.save(resultTokens);

		log.info("refreshToken(email, user) end");
		return resultToken;
	}
}
