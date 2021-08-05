package com.fengkuizhang.dvs.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fengkuizhang.dvs.controller.request.AddUserRequest;
import com.fengkuizhang.dvs.controller.request.LoginRequest;
import com.fengkuizhang.dvs.controller.request.RegisterRequest;
import com.fengkuizhang.dvs.dto.UserDto;
import com.fengkuizhang.dvs.model.User;
import com.fengkuizhang.dvs.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${web.cors.domain}")
    private String corsDomain;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest registerRequest){
        UserDto userDto = modelMapper.map(registerRequest, UserDto.class);
        userService.addUser(userDto);
        return success();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response){
        String userId = userService.validateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if(userId!=null){
            long duration = TimeUnit.HOURS.toMillis(24);
            Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
            String token = JWT.create()
                    .withClaim("userId", userId)
                    .withExpiresAt(new Date(System.currentTimeMillis() + duration))
                    .sign(algorithm);
            return success(token);
        }else{
            return fail(HttpStatus.UNAUTHORIZED, "login fail");
        }
    }

    @RequestMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response){
        return success();
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable @NotEmpty String id){
        UserDto userDto = userService.findUser(id);
        if(userDto!=null){
            return success(userDto);
        }else{
            return fail(HttpStatus.NOT_FOUND, "no user found");
        }
    }

    @PostMapping("")
    public ResponseEntity addUser(@RequestBody @Valid AddUserRequest addUserRequest){
        UserDto userDto = modelMapper.map(addUserRequest, UserDto.class);
        userService.addUser(userDto);
        return success();
    }

    @GetMapping("/dashboard")
    public ResponseEntity dashboard(HttpServletRequest request){
        User user = (User)request.getAttribute("loginUser");
        user.setPassword("");
        if(user!=null){
            return success(user);
        }else{
            return fail(HttpStatus.NOT_FOUND, "no user found");
        }
    }

}
