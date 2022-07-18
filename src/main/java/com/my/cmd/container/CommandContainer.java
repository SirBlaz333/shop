package com.my.cmd.container;

import com.my.cmd.impl.*;
import com.my.cmd.Command;
import com.my.service.user.UserService;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategy;
import com.my.web.captcha.container.strategy.CaptchaContainerFactory;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategies;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private final Map<String, Command> commands;

    public CommandContainer(UserService userService, CaptchaContainerStrategies strategy, long timeout){
        CaptchaContainerFactory factory = new CaptchaContainerFactory();
        CaptchaContainerStrategy container = factory.create(strategy, timeout);

        ShowLoginPageCommand showLoginPageCommand = new ShowLoginPageCommand(container);
        RegistrationCommand registrationCommand = new RegistrationCommand(container, userService, showLoginPageCommand);
        LogoutCommand logoutCommand = new LogoutCommand();
        LoginCommand loginCommand = new LoginCommand(container, userService, showLoginPageCommand);
        DisplayAvatarCommand displayAvatarCommand = new DisplayAvatarCommand();

        commands = new HashMap<>();
        commands.put("registration", registrationCommand);
        commands.put("showLoginPage", showLoginPageCommand);
        commands.put("logout", logoutCommand);
        commands.put("login", loginCommand);
        commands.put("displayAvatar", displayAvatarCommand);
    }

    public Command getCommand(String commandName){
        return commands.get(commandName);
    }

}
