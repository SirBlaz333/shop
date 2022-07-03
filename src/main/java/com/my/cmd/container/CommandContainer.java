package com.my.cmd.container;

import com.my.cmd.impl.ShowRegistrationPageCommand;
import com.my.cmd.impl.DisplayCaptchaCommand;
import com.my.cmd.impl.RegistrationCommand;
import com.my.cmd.Command;
import com.my.service.user.UserService;
import com.my.web.captcha.container.CaptchaContainerStrategy;
import com.my.web.captcha.container.strategy.CaptchaContainerFactory;
import com.my.web.captcha.container.strategy.CaptchaContainerStrategies;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private final Map<String, Command> commands;

    public CommandContainer(UserService userService, CaptchaContainerStrategies strategy, long timeout){
        CaptchaContainerFactory factory = new CaptchaContainerFactory();
        CaptchaContainerStrategy container = factory.create(strategy, timeout);

        ShowRegistrationPageCommand showRegistrationPageCommand = new ShowRegistrationPageCommand(container);
        RegistrationCommand registrationCommand = new RegistrationCommand(container, userService, showRegistrationPageCommand);
        DisplayCaptchaCommand displayCaptchaCommand = new DisplayCaptchaCommand();

        commands = new HashMap<>();
        commands.put("registration", registrationCommand);
        commands.put("showRegistration", showRegistrationPageCommand);
        commands.put("displayCaptcha", displayCaptchaCommand);
    }

    public Command getCommand(String commandName){
        return commands.get(commandName);
    }

}
