package com.example.demo2.services.Impl;

import com.example.demo2.dtos.UserDTO;
import com.example.demo2.exceptions.DataNotFoundException;
import com.example.demo2.models.Role;
import com.example.demo2.models.User;
import com.example.demo2.repositories.RoleRepository;
import com.example.demo2.repositories.UserRepository;
import com.example.demo2.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber= userDTO.getPhoneNumber();
        //Kiem tra so dien thoai nay da ton tai hay chua
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        //convert userDTO thanh User
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .password(userDTO.getPassword())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();

        Role role = roleRepository.findById(userDTO.getRoleId())
                        .orElseThrow(()-> new DataNotFoundException("Role not found"));
        newUser.setRole(role);
        // Kiem tra neu c6 accountId, không yêu cầu password
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) { //if The user is not using a Facebook or Google account to sign up (i.e., social login is not used).
            String password = userDTO.getPassword();
            //String encodedPassword = passwordEncoder.encode(password); //Phan nay se nam trong spring security vi minh chua hoc phan nay
            //newUser.setPassword(encodedPassword);
        }

        return userRepository.save(newUser);
    }

    @Override
    //De return String vi day la tra ve token
    public String login(String phoneNumber, String password) {
        //Doan nay lien quan den spring security
        return null;
    }



}
