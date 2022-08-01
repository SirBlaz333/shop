package com.my.service.cart;

import com.my.service.cart.impl.CartClearAction;
import com.my.service.cart.impl.CartPutAction;
import com.my.service.cart.impl.CartRemoveAction;
import com.my.service.cart.impl.CartRemoveAllAction;
import com.my.service.product.ProductService;

import java.util.HashMap;

public class CartActionContainer {
    private final HashMap<String, CartAction> actions;

    public CartActionContainer(ProductService productService) {
        actions = new HashMap<>();

        CartPutAction cartPutAction = new CartPutAction(productService);
        CartRemoveAction cartRemoveAction = new CartRemoveAction();
        CartRemoveAllAction cartRemoveAllAction = new CartRemoveAllAction();
        CartClearAction cartClearAction = new CartClearAction();

        actions.put("put", cartPutAction);
        actions.put("remove", cartRemoveAction);
        actions.put("removeAll", cartRemoveAllAction);
        actions.put("clear", cartClearAction);
    }

    public CartAction getAction(String key) {
        return actions.get(key);
    }
}
