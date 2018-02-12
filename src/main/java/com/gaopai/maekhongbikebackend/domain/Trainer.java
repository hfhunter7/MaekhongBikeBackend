package com.gaopai.maekhongbikebackend.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "trainer")
public class Trainer implements Serializable {

    private static final long serialVersionUID = -5458390746218844369L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String googleId;

    private String name;

    private String email;

    private String displayName;
    private String birthDate ;
    private String phone;
    private String companyName;
    private String title;
    private String imageUrl;

    private Timestamp createDate;

    private Timestamp modifyDate;

    private Timestamp lastLogin;

    @OneToOne(mappedBy = "trainer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public TokenTrainer token;

    public Trainer() {

    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {

        return displayName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Timestamp modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public TokenTrainer getToken() {
        return token;
    }

    public void setToken(TokenTrainer token) {
        this.token = token;
    }
}
