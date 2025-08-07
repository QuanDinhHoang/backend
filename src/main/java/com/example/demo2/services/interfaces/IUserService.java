package com.example.demo2.services.interfaces;

import com.example.demo2.dtos.UserDTO;
import com.example.demo2.exceptions.DataNotFoundException;
import com.example.demo2.models.User;
import org.springframework.stereotype.Service;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;

    String login(String phoneNumber, String password);

}
