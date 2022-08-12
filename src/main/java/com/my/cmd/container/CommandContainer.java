package com.my.cmd.container;

import com.my.cmd.Command;
import com.my.cmd.impl.CreateOrderCommand;
import com.my.cmd.impl.DisplayAvatarCommand;
import com.my.cmd.impl.LoginCommand;
import com.my.cmd.impl.LogoutCommand;
import com.my.cmd.impl.ProcessCartCommand;
import com.my.cmd.impl.RegistrationCommand;
import com.my.cmd.impl.ShowLoginPageCommand;
import com.my.cmd.impl.ShowProductsCommand;
import com.my.init.ApplicationInitializer;
import com.my.service.order.OrderService;
import com.my.service.product.ProductService;
import com.my.service.time.TimeService;
import com.my.service.user.UserService;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategy;
import com.my.web.captcha.container.strategy.CaptchaContainerFactory;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategies;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private final Map<String, Command> commands;

    public CommandContainer(ApplicationInitializer applicationInitializer) {
        CaptchaContainerFactory factory = new CaptchaContainerFactory();
        CaptchaContainerStrategies strategy = applicationInitializer.getStrategy();
        long timeout = applicationInitializer.getTimeoutMillis();
        CaptchaContainerStrategy container = factory.create(strategy, timeout);

        UserService userService = applicationInitializer.getUserService();
        ProductService productService = applicationInitializer.getProductService();
        OrderService orderService = applicationInitializer.getOrderService();
        TimeService timeService = applicationInitializer.getTimeService();

        ShowLoginPageCommand showLoginPageCommand = new ShowLoginPageCommand(container);
        RegistrationCommand registrationCommand = new RegistrationCommand(container, userService);
        LogoutCommand logoutCommand = new LogoutCommand();
        LoginCommand loginCommand = new LoginCommand(container, userService);
        DisplayAvatarCommand displayAvatarCommand = new DisplayAvatarCommand();
        ShowProductsCommand showProductsCommand = new ShowProductsCommand(productService);
        ProcessCartCommand processCartCommand = new ProcessCartCommand(productService);
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(orderService, productService, timeService);

        commands = new HashMap<>();
        commands.put("registration", registrationCommand);
        commands.put("showLoginPage", showLoginPageCommand);
        commands.put("logout", logoutCommand);
        commands.put("login", loginCommand);
        commands.put("displayAvatar", displayAvatarCommand);
        commands.put("products", showProductsCommand);
        commands.put("processCart", processCartCommand);
        commands.put("createOrder", createOrderCommand);
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }

}
