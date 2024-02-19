package com.example.demo.services;
import com.example.demo.JWT.CustomerUserDetailsService;
import com.example.demo.JWT.JwtUtil;
import com.example.demo.models.Authentification;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomerUserDetailsService customerUserDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(userService, "authenticationManager", authenticationManager);
        ReflectionTestUtils.setField(userService, "customerUserDetailsService", customerUserDetailsService);
        ReflectionTestUtils.setField(userService, "jwtUtil", jwtUtil);
    }

    /*@Test
    public void testLogin_ValidCredentials() {
        // Mock request map with valid credentials
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("loginConnection", "username");
        requestMap.put("motpasse", "password");

        // Mock successful authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        // Mock UserDetails
        UserDetails userDetails = User.withUsername("username").password("password").roles("USER").build();
        when(customerUserDetailsService.loadUserByUsername("username")).thenReturn(userDetails);

        // Mock JWT generation
        when(jwtUtil.generateToken("username", "ROLE_USER")).thenReturn("dummyToken");

        // Test login method
        ResponseEntity<String> responseEntity = userService.login(requestMap);

        // Verify response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("dummyToken"));
    }*/
}
