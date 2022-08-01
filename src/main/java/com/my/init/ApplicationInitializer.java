package com.my.init;

import com.my.cmd.container.CommandContainer;
import com.my.dao.DBManager;
import com.my.dao.manufacturer.ManufacturerDAO;
import com.my.dao.manufacturer.impl.ManufacturerDAOImpl;
import com.my.dao.mt.MemoryTypeDAO;
import com.my.dao.mt.impl.MemoryTypeDAOImpl;
import com.my.dao.order.OrderDAO;
import com.my.dao.order.OrderStatusDAO;
import com.my.dao.order.OrderedProductsDAO;
import com.my.dao.order.impl.OrderDAOImpl;
import com.my.dao.order.impl.OrderStatusDAOImpl;
import com.my.dao.order.impl.OrderedProductsDAOImpl;
import com.my.dao.product.ProductDAO;
import com.my.dao.product.impl.ProductDAOImpl;
import com.my.dao.user.UserDAO;
import com.my.dao.user.impl.UserDAOImpl;
import com.my.service.order.OrderService;
import com.my.service.order.impl.OrderServiceImpl;
import com.my.service.product.ProductService;
import com.my.service.product.impl.ProductServiceImpl;
import com.my.service.time.TimeService;
import com.my.service.time.impl.TimeServiceImpl;
import com.my.service.user.UserService;
import com.my.service.user.UserServiceImpl;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategies;

public class ApplicationInitializer {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final TimeService timeService;
    private final long timeoutMillis;
    private final CaptchaContainerStrategies strategy;
    private final CommandContainer commandContainer;

    public ApplicationInitializer() {
        timeoutMillis = 180_000;
        strategy = CaptchaContainerStrategies.COOKIE_CONTAINER;
        DBManager dbManager = new DBManager();
        UserDAO userDAO = new UserDAOImpl(dbManager);
        ManufacturerDAO manufacturerDAO = new ManufacturerDAOImpl(dbManager);
        MemoryTypeDAO memoryTypeDAO = new MemoryTypeDAOImpl(dbManager);
        ProductDAO productDAO = new ProductDAOImpl(dbManager);
        OrderDAO orderDAO = new OrderDAOImpl(dbManager);
        OrderedProductsDAO orderedProductsDAO = new OrderedProductsDAOImpl(dbManager);
        OrderStatusDAO orderStatusDAO = new OrderStatusDAOImpl(dbManager);

        userService = new UserServiceImpl(userDAO);
        productService = new ProductServiceImpl(productDAO, manufacturerDAO, memoryTypeDAO);
        orderService = new OrderServiceImpl(orderDAO, orderedProductsDAO, orderStatusDAO);
        timeService = new TimeServiceImpl();

        commandContainer = new CommandContainer(this);
    }

    public ProductService getProductService() {
        return productService;
    }

    public UserService getUserService() {
        return userService;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public CaptchaContainerStrategies getStrategy() {
        return strategy;
    }

    public CommandContainer getCommandContainer() {
        return commandContainer;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public TimeService getTimeService() {
        return timeService;
    }
}
