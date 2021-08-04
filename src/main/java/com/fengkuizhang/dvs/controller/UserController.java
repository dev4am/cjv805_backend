package com.fengkuizhang.dvs.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fengkuizhang.dvs.controller.request.AddUserRequest;
import com.fengkuizhang.dvs.controller.request.LoginRequest;
import com.fengkuizhang.dvs.controller.request.RegisterRequest;
import com.fengkuizhang.dvs.dto.UserDto;
import com.fengkuizhang.dvs.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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
            int maxAge = (int) (duration / 1000);
            Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
            String token = JWT.create()
                    .withClaim("userId", userId)
                    .withExpiresAt(new Date(System.currentTimeMillis() + duration))
                    .sign(algorithm);
            Cookie cookie = new Cookie("dvs_token", token);
            cookie.setPath("/");
//            cookie.setDomain("");
            cookie.setMaxAge(maxAge);
//            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return success();
        }else{
            return fail(HttpStatus.UNAUTHORIZED, "login fail");
        }
    }

    @RequestMapping("/login")
    public ResponseEntity logout(HttpServletResponse response){
        Cookie cookie = new Cookie("dvs_token", "");
//        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
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

}
