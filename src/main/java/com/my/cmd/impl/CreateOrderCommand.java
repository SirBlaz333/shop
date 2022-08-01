package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.impl.util.CartUtility;
import com.my.cmd.impl.util.ErrorUtility;
import com.my.entity.Cart;
import com.my.entity.Order;
import com.my.entity.OrderStatus;
import com.my.entity.OrderedProducts;
import com.my.entity.User;
import com.my.entity.UserRegFields;
import com.my.entity.builder.OrderBuilder;
import com.my.service.ServiceException;
import com.my.service.order.OrderService;
import com.my.service.product.ProductService;
import com.my.service.time.TimeService;
import com.my.web.page.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateOrderCommand implements Command {
    private final OrderService orderService;
    private final ProductService productService;
    private final TimeService timeService;
    private final ErrorUtility errorUtility;
    private final CartUtility cartUtility;

    public CreateOrderCommand(OrderService orderService, ProductService productService, TimeService timeService) {
        this.orderService = orderService;
        this.productService = productService;
        this.timeService = timeService;
        errorUtility = new ErrorUtility();
        cartUtility = new CartUtility();
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        try{
            Order order = buildOrder(request);
            productService.buyProduct(order.getOrderedProducts());
            orderService.create(order);
            request.getRequestDispatcher(Pages.MAIN).forward(request, response);
        } catch (ServiceException e){
            errorUtility.showError(request, response, Pages.CART, e.getMessage());
        }
    }

    private Order buildOrder(HttpServletRequest request){
        String address = request.getParameter("address");
        OrderBuilder orderBuilder = new OrderBuilder();
        User user = (User) request.getSession().getAttribute(UserRegFields.USER);
        Cart cart = cartUtility.getCart(request.getSession());
        String dateTime = timeService.now();
        return orderBuilder.withAddress(address)
                .withOrderedProducts(new OrderedProducts(cart.getMap()))
                .withOrderStatus(OrderStatus.CONFIRMED)
                .withUser(user)
                .withDateTime(dateTime)
                .build();
    }
}
