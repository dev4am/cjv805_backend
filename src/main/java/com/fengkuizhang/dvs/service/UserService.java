package com.fengkuizhang.dvs.service;

import com.fengkuizhang.dvs.dto.UserDto;
import com.fengkuizhang.dvs.exception.AppException;
import com.fengkuizhang.dvs.model.User;
import com.fengkuizhang.dvs.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public String validateUser(String email, String password){
        User existUser = userRepository.findByEmail(email);
        if(existUser==null){
            return null;
        }
        if (!BCrypt.checkpw(password, existUser.getPassword())){
            return null;
        }
        return existUser.getId();
    }

    public UserDto addUser(UserDto userDto){
        User existUser = userRepository.findByEmail(userDto.getEmail());
        if(existUser!=null){
            throw new AppException("user exists");
        }

        String hashedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());

        User user = modelMapper.map(userDto, User.class);
        user.setId(null);
        user.setPassword(hashedPassword);
        userRepository.save(user);

        userDto.setId(user.getId());
        return userDto;
    }

    public UserDto findUser(String id){
        return null;
    }

}
