package com.emlakburada.emlakburada.model;

import com.emlakburada.emlakburada.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UserType userType;
    private int userId;
    private String name;
    private String email;
    private String photo;
    private String bio;
    private Set<Advert> favorites = new HashSet<>();
    private List<Advert> publishedAdverts = new ArrayList<>();
    private List<Message> messageBox;

    public User(UserType userType, String name, String email) {
        this.userType = userType;
        this.name = name;
        this.email = email;
    }
}