package com.my.cmd.container;

import com.my.cmd.impl.*;
import com.my.cmd.Command;
import com.my.service.user.UserService;
import com.my.web.captcha.container.CaptchaContainer;
import com.my.web.captcha.container.impl.CaptchaContainerFactory;
import com.my.web.captcha.container.impl.CaptchaContainerStrategy;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private final Map<String, Command> commands;

    public CommandContainer(UserService userService, CaptchaContainerStrategy strategy, int timeout){
        CaptchaContainerFactory factory = new CaptchaContainerFactory();
        CaptchaContainer container = factory.create(strategy);

        ShowLoginPageCommand showLoginPageCommand = new ShowLoginPageCommand(container);
        RegistrationCommand registrationCommand = new RegistrationCommand(container, userService, timeout, showLoginPageCommand);
        DisplayCaptchaCommand displayCaptchaCommand = new DisplayCaptchaCommand();
        LogoutCommand logoutCommand = new LogoutCommand();
        LoginCommand loginCommand = new LoginCommand(container, userService, timeout, showLoginPageCommand);

        commands = new HashMap<>();
        commands.put("registration", registrationCommand);
        commands.put("showLoginPage", showLoginPageCommand);
        commands.put("displayCaptcha", displayCaptchaCommand);
        commands.put("logout", logoutCommand);
        commands.put("login", loginCommand);
    }

    public Command getCommand(String commandName){
        return commands.get(commandName);
    }

}
