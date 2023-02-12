package com.lambda.appointment.notes.service;

import com.lambda.appointment.notes.dto.UserDTO;
import com.lambda.appointment.notes.mapper.UserMapper;
import com.lambda.appointment.notes.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Transactional
    public void addUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User.persist(user);
    }

    public void updateUser(User user) {
        User.persist(user);
    }

    public void removeUser(Long id) {
        User.deleteById(id);
    }

    public List<User> allUsers(){
        return User.listAll();
    }

    public UserDTO getLoggedInUser(String googleUserId) {
        User userDataBase = User.find("googleUserId", googleUserId).firstResult();
        return UserMapper.toDTO(userDataBase);
    }

    public UserDTO getUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User userDataBase = User.findById(userDTO);
        return UserMapper.toDTO(userDataBase);
    }

    public UserDTO getUserByGoogleId(String googleUserId) {
        User userDataBase = User.find("googleUserId", googleUserId).firstResult();
        return UserMapper.toDTO(userDataBase);
    }
}

