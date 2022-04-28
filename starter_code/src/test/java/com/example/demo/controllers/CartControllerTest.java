package com.example.demo.controllers;

import com.example.demo.constants.MockConstants;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.demo.constants.MockConstants.getItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/** 
* CartController Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 27, 2022</pre> 
* @version 1.0 
*/
@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest { 
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private CartController cartController;

    private ModifyCartRequest mcr;

    @Before
    public void before(){
        when(userRepository.findByUsername("zane")).thenReturn(MockConstants.getUser());
        when(itemRepository.findById(1L)).thenReturn(Optional.of(getItem()));

        mcr = MockConstants.getRequest();
    }


    /**
    *
    * Method: addTocart(@RequestBody ModifyCartRequest request)
    *
    */
    @Test
    public void testAddToCart() {
        ResponseEntity<Cart> response = cartController.addToCart(mcr);

        assertNotNull(response);
        assertNotNull(response.getBody());
        Cart cart = response.getBody();
        Item item = cart.getItems().get(0);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getItems().size());
        assertEquals(1L, (long)item.getId());
        assertEquals("testName", item.getName());
        assertEquals("testDescription", item.getDescription());
        assertEquals(BigDecimal.valueOf(100), item.getPrice());
    }

    @Test
    public void testAddToCart_NotFound() {
        mcr.setUsername("not_found");
        ResponseEntity<Cart> response = cartController.addToCart(mcr);
        assertEquals(404, response.getStatusCodeValue());
    }

    /**
    *
    * Method: removeFromcart(@RequestBody ModifyCartRequest request)
    *
    */
    @Test
    public void testRemoveFromCart() throws Exception {
        ResponseEntity<Cart> response = cartController.removeFromCart(mcr);
        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().getItems().size());
    }

    @Test
    public void testRemoveFromCart_NotFound() {
        mcr.setUsername("not_found");
        ResponseEntity<Cart> response = cartController.removeFromCart(mcr);
        assertEquals(404, response.getStatusCodeValue());
    }
} 
