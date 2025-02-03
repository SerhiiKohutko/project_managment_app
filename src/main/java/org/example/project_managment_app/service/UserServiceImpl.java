package org.example.project_managment_app.service;

import org.example.project_managment_app.config.JWTProvider;
import org.example.project_managment_app.entities.User;
import org.example.project_managment_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JWTProvider.getEmailFromJWT(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new Exception("User not found");
        }
        return null;
    }

    @Override
    public User findUserById(long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new Exception("User not found"));

    }

    @Override
    public User updateUsersProjectSize(User user, int projectSize) {
        user.setProjectSize(user.getProjectSize() + projectSize);
        return userRepository.save(user);
    }
}
