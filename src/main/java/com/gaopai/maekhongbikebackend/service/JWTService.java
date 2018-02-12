package com.gaopai.maekhongbikebackend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.gaopai.maekhongbikebackend.bean.AuthorizationBean;
import com.gaopai.maekhongbikebackend.domain.TokenUser;
import com.gaopai.maekhongbikebackend.domain.Trainer;
import com.gaopai.maekhongbikebackend.domain.TokenTrainer;
import com.gaopai.maekhongbikebackend.domain.Users;
import com.gaopai.maekhongbikebackend.exception.DataFormatException;
import com.gaopai.maekhongbikebackend.repository.impl.TrainerRepositoryService;
import com.gaopai.maekhongbikebackend.repository.impl.UserRepositoryService;
import com.gaopai.maekhongbikebackend.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JWTService {
	
	private static final Logger log = LoggerFactory.getLogger(JWTService.class);

	@Autowired
	private TokenTrainerService tokenTrainerService;

	@Autowired
	private TokenUserService tokenUserService;

	@Autowired
	private TrainerRepositoryService trainerRepositoryService;

	@Autowired
	private UserRepositoryService userRepositoryService;

	public String generateToken(String token, String emailToken) throws Exception {
		try {
			return JWT.create()
					.withClaim("appKey", Constant.APP_TOKEN)
					.withClaim("tokenKey", token)
					.withClaim("emailToken", emailToken)
					.sign(Algorithm.HMAC256(Constant.SECRET_KEY));
		} catch (JWTCreationException exception) {
			throw new Exception(Constant.RESPONSE.MSG.UNAUTHORIZED);
		}
	}

	public AuthorizationBean authorizationToken(String token) throws Exception {
		AuthorizationBean authorizationBean = new AuthorizationBean();
		try {
		    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Constant.SECRET_KEY)).build();
		    
		    JWT jwt = (JWT) verifier.verify(token);
		    Map<String, Claim> claims = jwt.getClaims();
		    
		    Claim appKey = claims.get("appKey");
		    Claim tokenKey = claims.get("tokenKey");
		    
		    authorizationBean.setAppToken(appKey.asString());
			authorizationBean.setTokenKey(tokenKey.asString());
			return authorizationBean;
		} catch (JWTVerificationException exception){
			throw new Exception(Constant.RESPONSE.MSG.UNAUTHORIZED);
		}
	}

	public Trainer verifyTokenTrainer(String token) throws Exception {
		AuthorizationBean authorizationBean = authorizationToken(token);

		if (authorizationBean.getAppToken().equalsIgnoreCase(Constant.APP_TOKEN)) {
			if (!authorizationBean.getTokenKey().equals("")) {
				TokenTrainer tokenTrainer = tokenTrainerService.findTokenTrainerByTokenString(authorizationBean.getTokenKey());
				if (tokenTrainer != null) {
					return tokenTrainer.getTrainer();
				} else {
					throw new DataFormatException(Constant.RESPONSE.MSG.INVALID_TOKEN);
				}
			}
		} else {
			throw new DataFormatException(Constant.RESPONSE.MSG.INVALID_APP_TOKEN);
		}
		return null;
	}

	public Users verifyTokenUser(String token) throws Exception {
		AuthorizationBean authorizationBean = authorizationToken(token);

		if (authorizationBean.getAppToken().equalsIgnoreCase(Constant.APP_TOKEN)) {
			if (!authorizationBean.getTokenKey().equals("")) {
				TokenUser tokenUser = tokenUserService.findTokenTrainerByTokenString(authorizationBean.getTokenKey());
				if (tokenUser != null) {
					return tokenUser.getUsers();
				} else {
					throw new DataFormatException(Constant.RESPONSE.MSG.INVALID_TOKEN);
				}
			}
		} else {
			throw new DataFormatException(Constant.RESPONSE.MSG.INVALID_APP_TOKEN);
		}
		return null;
	}

	public Trainer verifyTokenTrainerExpire(String token) throws Exception {
		AuthorizationBean authorizationBean = authorizationToken(token);

		if (authorizationBean.getAppToken().equalsIgnoreCase(Constant.APP_TOKEN)) {
			if (!authorizationBean.getTokenKey().equals("")) {
				Trainer trainer = trainerRepositoryService.findByToken(authorizationBean.getTokenKey());

				if (trainer != null) {
					return trainer;
				} else {
					throw new DataFormatException(Constant.RESPONSE.MSG.INVALID_TOKEN);
				}
			} else {
				throw new DataFormatException(Constant.RESPONSE.MSG.UNAUTHORIZED);
			}
		} else {
			throw new DataFormatException(Constant.RESPONSE.MSG.INVALID_APP_TOKEN);
		}
	}

	public Users verifyTokenUserExpire(String token) throws Exception {
		AuthorizationBean authorizationBean = authorizationToken(token);

		if (authorizationBean.getAppToken().equalsIgnoreCase(Constant.APP_TOKEN)) {
			if (!authorizationBean.getTokenKey().equals("")) {
				Users users = userRepositoryService.findByToken(authorizationBean.getTokenKey());

				if (users != null) {
					return users;
				} else {
					throw new DataFormatException(Constant.RESPONSE.MSG.INVALID_TOKEN);
				}
			} else {
				throw new DataFormatException(Constant.RESPONSE.MSG.UNAUTHORIZED);
			}
		} else {
			throw new DataFormatException(Constant.RESPONSE.MSG.INVALID_APP_TOKEN);
		}
	}

	public void verifyAppToken(String token) throws Exception {
		if (!authorizationToken(token).getAppToken().equals(Constant.APP_TOKEN)) {
			throw new DataFormatException(Constant.RESPONSE.MSG.INVALID_APP_TOKEN);
		}
	}
	
	public AuthorizationBean verifyEmailToken(String token) throws Exception {
		// This line will throw an exception if it is not a signed JWS (as expected)

		AuthorizationBean authorizationBean = new AuthorizationBean();
		try {
		    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Constant.SECRET_KEY)).build(); //Reusable verifier instance
		    
		    JWT jwt = (JWT) verifier.verify(token);
		    Map<String, Claim> claims = jwt.getClaims();    //Key is the Claim name
		    
		    Claim appKey = claims.get("appKey");
		    Claim tokenKey = claims.get("tokenKey");
		    Claim emailToken = claims.get("emailToken");
		    
		    authorizationBean.setAppToken(appKey.asString());
			authorizationBean.setTokenKey(tokenKey.asString());
			authorizationBean.setEmailToken(emailToken.asString());
			
			if (!authorizationBean.getAppToken().equals(Constant.APP_TOKEN)) {
				throw new DataFormatException(Constant.RESPONSE.MSG.INVALID_APP_TOKEN);
			}
			
			return authorizationBean;
		} catch (JWTVerificationException exception){
			throw new Exception(Constant.RESPONSE.MSG.UNAUTHORIZED);
		}
	}
}
