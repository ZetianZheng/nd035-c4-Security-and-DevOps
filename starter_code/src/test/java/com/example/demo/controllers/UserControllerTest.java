package com.example.demo.controllers; 

import com.example.demo.constants.MockConstants;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/** 
* UserController Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 27, 2022</pre> 
* @version 1.0 
*/
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private UserController userController;

    @Before
    public void before() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(MockConstants.getUser()));
        when(userRepository.findByUsername("zane")).thenReturn(MockConstants.getUser());
    }

    /**
     * testFindById:
     */
    @Test
    public void testFindById() {
        ResponseEntity<User> response = userController.findById(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

 /** 
    * 
    * Method: findByUserName(@PathVariable String username) 
    * 
    */ 
    @Test
    public void testFindByUserName() {
        ResponseEntity<User> response = userController.findByUserName("zane");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testFindByUserName_EmptyUser() {
        when(userRepository.findByUsername("zane")).thenReturn(null);
        ResponseEntity<User> response = userController.findByUserName("zane");
        assertEquals(404, response.getStatusCodeValue());
    }
    /**
    * Method: createUser(@RequestBody CreateUserRequest createUserRequest) 
    * <p>the password length is less than 7, so this assertion will expect a bad response</p>
    */ 
    @Test
    public void testCreateUser_invalidPassword() {
        ResponseEntity<User> response = userController.createUser(MockConstants.getUserRequest_invalidPwd());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testCreateUser() {
        ResponseEntity<User> response = userController.createUser(MockConstants.getUserRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

} 
