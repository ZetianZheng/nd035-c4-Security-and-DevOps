package com.example.demo.constants;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;

import java.math.BigDecimal;

public class MockConstants {
    private MockConstants(){}
    /**
     * create a request
     * @return a mock request
     */
    public static ModifyCartRequest getRequest() {
        ModifyCartRequest mcr = new ModifyCartRequest();
        mcr.setItemId(1L);
        mcr.setQuantity(1);
        mcr.setUsername("zane");
        return mcr;
    }
    /**
     * create a test Item
     * @return a mock Item
     */
    public static Item getItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("testName");
        item.setDescription("testDescription");
        item.setPrice(BigDecimal.valueOf(100));
        return item;
    }
    /**
     * create a test Cart
     * @return a mock Cart
     */
    public static Cart getCart() {
        Cart cart = new Cart();
        cart.addItem(getItem());
        return cart;
    }

    /**
     * create a test user
     * @return a mock user
     */
    public static User getUserWithCart() {
        User user = new User();
        user.setId(1L);
        user.setUsername("zane");
        user.setPassword("12345678");
        user.setCart(getCart());

        return user;
    }
    /**
     * create a test user
     * @return a mock user
     */
    public static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("zane");
        user.setPassword("12345678");
        user.setCart(new Cart());

        return user;
    }

    public static CreateUserRequest getUserRequest_invalidPwd() {
        CreateUserRequest cur = new CreateUserRequest();
        cur.setUsername("username");
        cur.setPassword("pwd");
        cur.setConfirmPassword("pwd");
        return cur;
    }

    public static CreateUserRequest getUserRequest() {
        CreateUserRequest cur = new CreateUserRequest();
        cur.setUsername("username");
        cur.setPassword("123456789");
        cur.setConfirmPassword("123456789");
        return cur;
    }
}
