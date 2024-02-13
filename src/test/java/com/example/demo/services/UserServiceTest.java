package com.example.demo.services;

import com.example.demo.JWT.CustomerUserDetailsService;
import com.example.demo.JWT.JwtUtil;
import com.example.demo.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.example.demo.models.Role;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    void login_WithValidCredentials_ShouldReturnToken() {
        // Mock objects
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        CustomerUserDetailsService customerUserDetailsService = mock(CustomerUserDetailsService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        // Create a UserService instance with mocked dependencies
        UserService userService = new UserService(authenticationManager, customerUserDetailsService, jwtUtil);

        // Mocking userDetails
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.USER);

        // Mocking authentication
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);

        // Mocking authentication manager behavior
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);

        // Mocking userDetails service behavior
        when(customerUserDetailsService.getUserDetails()).thenReturn(user);

        // Mocking JWT token generation
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("mocked-token");

        // Prepare test data
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "test@example.com");
        requestMap.put("password", "password123");

        // Call the login method
        ResponseEntity<String> responseEntity = userService.login(requestMap);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("token"));
    }
}
