package com.gaopai.maekhongbikebackend.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.bean.RegisterBean;
import com.gaopai.maekhongbikebackend.domain.Users;
import com.gaopai.maekhongbikebackend.exception.DataFormatException;
import com.gaopai.maekhongbikebackend.repository.impl.UserRepositoryService;
import com.gaopai.maekhongbikebackend.utils.DateUtil;
import com.gaopai.maekhongbikebackend.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private UserRepositoryService userRepositoryService;

    @Transactional
    public ObjectNode createUser(RegisterBean registerBean) throws Exception {
        Users user = new Users();
        user.setUsername(registerBean.getUsername());
        user.setEmail(registerBean.getEmail());
        user.setPassword(registerBean.getPassword());
        user.setName(registerBean.getName());
        user.setPhoneNumber(registerBean.getPhone_number());
        user.setCreateDate(DateUtil.getCurrentDateTime());
        user.setModifyDate(DateUtil.getCurrentDateTime());
        user.setLastLogin(DateUtil.getCurrentDateTime());

        try{
            user = userRepositoryService.save(user);
        }catch (DataFormatException e) {
            throw new DataFormatException("create course fail.");
        }


        return createUserJson(user);
    }

    private ObjectNode createUserJson(Users user) {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("username", user.getUsername());
        jsonNodes.put("password", user.getPassword());
        jsonNodes.put("name", user.getName());
        jsonNodes.put("email", user.getEmail());
        jsonNodes.put("phone_number", user.getPhoneNumber());

        return jsonNodes;
    }
}

