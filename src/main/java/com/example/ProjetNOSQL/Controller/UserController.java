package com.example.ProjetNOSQL.Controller;

import com.example.ProjetNOSQL.dal.Userrepository;
import com.example.ProjetNOSQL.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/")
public class UserController  {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final Userrepository userRepository;

    public UserController(Userrepository userRepository) {
        this.userRepository = userRepository;
    }

    //Getting all the users
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        LOG.info("Getting all users.");
        return userRepository.findAll();
    }

    // gettting a user by id
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Optional<User> getUser(@PathVariable String userId) {
        LOG.info("Getting user with ID: {}.", userId);
        return userRepository.findById(userId);
    }

    // adding new user
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User addNewUsers(@RequestBody User user) {
        LOG.info("Saving user.");
        return userRepository.save(user);
    }

    //getting user settings
    @RequestMapping(value = "/settings/{userId}", method = RequestMethod.GET)
    public Object getAllUserSettings(@PathVariable String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user != null) {
            return user.map(user:getAllUserSettings());
        } else {
            return "User not found.";
        }
    }

    // getting a particular user setting
    @RequestMapping(value = "/settings/{userId}/{key}", method = RequestMethod.GET)
    public String getUserSetting(@PathVariable String userId, @PathVariable String key) {
        Optional<User> user = userRepository.findById(userId);
        if (user != null) {
            return user.getUserSettings().get(key);
        } else {
            return "User not found.";
        }
    }

    // adding a new user Setting
    @RequestMapping(value = "/settings/{userId}/{key}/{value}", method = RequestMethod.GET)
    public String addUserSetting(@PathVariable String userId, @PathVariable String key, @PathVariable String value) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.getUserSettings().put(key, value);
            userRepository.save(user);
            return "Key added";
        } else {
            return "User not found.";
        }
    }
}
