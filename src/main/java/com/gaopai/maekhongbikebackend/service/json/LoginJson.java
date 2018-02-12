package com.gaopai.maekhongbikebackend.service.json;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.domain.Trainer;
import com.gaopai.maekhongbikebackend.domain.Users;

public class LoginJson {

    public ObjectNode returnDetailTrainerLogin(Trainer user) {
        return trainerDetailJsonObject(user.getId(), user.getEmail(), user.getImageUrl(), user.getGoogleId(), user.getName(), user.getBirthDate(),null, user.getPhone(), null,user.getDisplayName(), null,user.getCompanyName()); // dun have googlename
    }

    public ObjectNode returnDetailUserLogin(Users users) {
        return userDetailJsonObject(users.getUsername(), users.getEmail(), users.getImageUrl(), users.getPassword(), users.getName(), users.getBirthDate(),null, users.getPhoneNumber(), null, users.getDisplayName(), null, users.getCompanyName()); // dun have googlename
    }

    public ObjectNode trainerDetailJsonObject(Long id, String email, String imageUrl, String googleId, String name, String birthday, String googleName, String phoneNumber, String customerName, String displayName,String companyName,String cardId) {
        ObjectNode userNode = new ObjectNode(JsonNodeFactory.instance);
        userNode.put("id", id);
        userNode.put("email", email);
        userNode.put("image_url", imageUrl);
        userNode.put("google_id", googleId);
        userNode.put("name", name);
        userNode.put("birthday", birthday);
        userNode.put("display_name", displayName);
        userNode.put("company_name", companyName);
        userNode.put("card_id", cardId);
        userNode.put("google_name", googleName);
        userNode.put("phone_number", phoneNumber);
        userNode.put("customer_name", customerName);
        userNode.put("display_name", displayName);

        return userNode;
    }

    public ObjectNode userDetailJsonObject(String username, String email, String imageUrl, String password, String name, String birthday, String googleName, String phoneNumber, String customerName, String displayName,String companyName,String cardId) {
        ObjectNode userNode = new ObjectNode(JsonNodeFactory.instance);
        userNode.put("username", username);
        userNode.put("email", email);
        userNode.put("image_url", imageUrl);
        userNode.put("password", password);
        userNode.put("name", name);
        userNode.put("birthday", birthday);
        userNode.put("display_name", displayName);
        userNode.put("company_name", companyName);
        userNode.put("card_id", cardId);
        userNode.put("google_name", googleName);
        userNode.put("phone_number", phoneNumber);
        userNode.put("customer_name", customerName);
        userNode.put("display_name", displayName);

        return userNode;
    }
}
