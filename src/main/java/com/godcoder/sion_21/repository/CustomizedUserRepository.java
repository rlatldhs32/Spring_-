package com.godcoder.sion_21.repository;

import com.godcoder.sion_21.model.User;

import java.util.List;

public interface CustomizedUserRepository {
    List<User> findByUsernameCustom(String username);

    List<User> findByUsernameJdbc(String username);
}
