package com.recipes.Recipes.persistance;

import com.recipes.Recipes.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Transactional
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Transactional
    public List<User> getAllUsers() {
        return (List<User>) userRepo.findAll();
    }
}
