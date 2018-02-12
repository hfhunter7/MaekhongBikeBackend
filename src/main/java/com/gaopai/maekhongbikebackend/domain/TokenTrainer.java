package com.gaopai.maekhongbikebackend.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tokenTrainer")
public class TokenTrainer implements Serializable {

	private static final long serialVersionUID = -1899498013732277982L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String tokenKey;

	private Timestamp createDate;

	private Timestamp expireDate;

	private String service;

	@OneToOne(fetch = FetchType.LAZY)
	private Trainer trainer;

	public TokenTrainer() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
}
