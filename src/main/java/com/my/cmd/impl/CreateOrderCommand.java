package com.my.cmd.impl;

import com.my.cmd.Command;
import com.my.cmd.Method;
import com.my.cmd.impl.util.CartUtility;
import com.my.cmd.impl.util.RedirectionUtility;
import com.my.entity.Cart;
import com.my.entity.Cpu;
import com.my.entity.order.Order;
import com.my.entity.order.OrderStatus;
import com.my.entity.order.OrderProduct;
import com.my.entity.user.User;
import com.my.entity.user.UserRegFields;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateOrderCommand implements Command {
    public static final String ADDRESS = "address";
    private final OrderService orderService;
    private final ProductService productService;
    private final TimeService timeService;
    private final RedirectionUtility redirectionUtility;
    private final CartUtility cartUtility;

    public CreateOrderCommand(OrderService orderService, ProductService productService, TimeService timeService) {
        this.orderService = orderService;
        this.productService = productService;
        this.timeService = timeService;
        this.redirectionUtility = new RedirectionUtility();
        this.cartUtility = new CartUtility();
    }

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response, Method method) throws ServletException, IOException {
        try {
            Order order = buildOrder(request);
            productService.buyProduct(order.getOrderedProducts());
            orderService.create(order);
            cartUtility.createNewCart(request.getSession());
            request.getRequestDispatcher(Pages.MAIN).forward(request, response);
        } catch (ServiceException e) {
            setAvailableAmountInCart(cartUtility.getCart(request.getSession()), productService);
            redirectionUtility.showErrorRedirect(response, Pages.CART, e.getMessage());
        }
    }

    private Order buildOrder(HttpServletRequest request) {
        String address = request.getParameter(ADDRESS);
        OrderBuilder orderBuilder = new OrderBuilder();
        User user = (User) request.getSession().getAttribute(UserRegFields.USER);
        Cart cart = cartUtility.getCart(request.getSession());
        String dateTime = timeService.now();
        List<OrderProduct> orderProducts = convertToOrderedProductList(cart.getMap());
        return orderBuilder.withAddress(address)
                .withOrderedProducts(orderProducts)
                .withOrderStatus(OrderStatus.CONFIRMED)
                .withUser(user)
                .withDateTime(dateTime)
                .build();
    }

    private List<OrderProduct> convertToOrderedProductList(Map<Cpu, Integer> map) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (Cpu cpu : map.keySet()) {
            int id = cpu.getId();
            double price = cpu.getPrice();
            int quantity = map.get(cpu);
            OrderProduct orderProduct = new OrderProduct(id, price, quantity);
            orderProducts.add(orderProduct);
        }
        return orderProducts;
    }

    private void setAvailableAmountInCart(Cart cart, ProductService productService){
        Map<Cpu, Integer> map = cart.getMap();
        for(Cpu cpu : map.keySet()){
            int amount = Math.min(map.get(cpu), productService.getProductAmount(cpu));
            cart.set(cpu, amount);
        }
    }
}
