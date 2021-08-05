package com.fengkuizhang.dvs.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fengkuizhang.dvs.model.User;
import com.fengkuizhang.dvs.repository.UserRepository;
import com.fengkuizhang.dvs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTVerifier jwtVerifier;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain ) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(token==null || token.length()==0){
            chain.doFilter(request, response);
            return;
        }

        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String userId = decodedJWT.getClaim("userId").asString();

        User userDetails = userRepository.findById(userId).orElse(null);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        request.setAttribute("loginUser", userDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
