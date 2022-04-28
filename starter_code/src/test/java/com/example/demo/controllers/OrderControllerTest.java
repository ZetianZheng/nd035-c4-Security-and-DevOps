package com.example.demo.controllers;

import com.example.demo.constants.MockConstants;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/** 
* OrderController Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 27, 2022</pre> 
* @version 1.0 
*/
@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderController orderController;

    private UserOrder userOrder;

    @Before
    public void before() {
        User user = MockConstants.getUserWithCart();
        this.userOrder = UserOrder.createFromCart(user.getCart());
        when(userRepository.findByUsername("zane")).thenReturn(user);
    } 

    /**
     * Method: submit(@PathVariable String username)
     * <p>use argument captor to capture userOrder, and make assertion.</p>
     */
    @Test
    public void testSubmit() {
        ResponseEntity<UserOrder> response = orderController.submit("zane");

        // assert response
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        // use captor to capture order, of course we can use response.getBody() to get the same order:)
        ArgumentCaptor<UserOrder> userOrderCaptor = ArgumentCaptor.forClass(UserOrder.class);
        verify(orderRepository, times(1)).save(userOrderCaptor.capture());

        UserOrder order = userOrderCaptor.getValue();
        assertEquals("testName", order.getItems().get(0).getName());
    }

    /**
     * verify when the userRepository didn't find the user with the specific name
     * <p>assert that response status Code 404</p>
     * @throws Exception
     */
    @Test
    public void testSubmit_EmptyUser() {
        when(userRepository.findByUsername("zane")).thenReturn(null);
        ResponseEntity<UserOrder> response = orderController.submit("zane");

        // assert response
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
  /** 
    * 
    * Method: getOrdersForUser(@PathVariable String username) 
    * 
    */ 
    @Test
    public void testGetOrdersForUser() throws Exception {
        when(orderRepository.findByUser(any(User.class))).thenReturn(Arrays.asList(userOrder));
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("zane");
        // assert response
        assertNotNull(response);
        assertNotNull(response.getBody());

        List<Item> items = response.getBody().get(0).getItems();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, (long)items.get(0).getId());
    }
    /**
     * verify when the userRepository didn't find the user with the specific name
     * <p>assert that response status Code 404</p>
     * @throws Exception
     */
    @Test
    public void testGetOrdersForUser_EmptyUser() throws Exception {
        when(userRepository.findByUsername("zane")).thenReturn(null);
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("zane");
        // assert response
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }


} 
