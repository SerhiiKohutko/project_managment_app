package org.example.project_managment_app.service;

import org.example.project_managment_app.entities.User;

public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(long userId) throws Exception;

    User updateUsersProjectSize(User user, int projectSize);
}
