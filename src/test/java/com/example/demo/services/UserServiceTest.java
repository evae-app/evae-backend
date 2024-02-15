package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.JWT.CustomerUserDetailsService;
import com.example.demo.JWT.JwtUtil;
import com.example.demo.models.Authentification;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class UserServiceTest {
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @BeforeEach // Utilisation de @BeforeEach au lieu de @Before pour JUnit 5
    public void setup() {
        authenticationManager = mock(AuthenticationManager.class);
        userService = new UserService(authenticationManager, null, null);
    }

    @Test
    public void testLogin_ValidCredentials() {
        // Mock request map with valid credentials
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("loginConnection", "username");
        requestMap.put("motpasse", "password");

        // Mock authentication
        ResponseEntity<String> expectedResponse = ResponseEntity.ok().body("Login successful");
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("username", "password")))
                .thenReturn(null); // Mocking authentication here as it's not the focus of this test

        // Test login method
        ResponseEntity<String> responseEntity = userService.login(requestMap);

        // Verify response
        assertEquals(expectedResponse.getStatusCode(), responseEntity.getStatusCode());
        // You may want to assert more on the response body depending on your requirements
    }


    @Test
    public void testLogin_WithInvalidCredentials_ShouldReturnBadRequest() {
        // Mocks
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        CustomerUserDetailsService customerUserDetailsService = mock(CustomerUserDetailsService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        // Create UserService instance
        UserService userService = new UserService(authenticationManager, customerUserDetailsService, jwtUtil);

        // Prepare request map with missing credentials
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("loginConnection", "username"); // Missing 'motpasse'

        // Call the method under test
        ResponseEntity<String> responseEntity = userService.login(requestMap);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Bad Credentials"));
    }
}