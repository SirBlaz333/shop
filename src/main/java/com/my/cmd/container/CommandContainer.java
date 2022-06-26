package com.my.cmd.container;

import com.my.cmd.impl.CaptchaCommand;
import com.my.cmd.impl.RegistrationCommand;
import com.my.cmd.Command;
import com.my.service.user.UserService;
import com.my.web.captcha.container.CaptchaContainer;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private final Map<String, Command> commands;

    public CommandContainer(UserService userService, CaptchaContainer container, long timeout){
        commands = new HashMap<>();
        commands.put("registration", new RegistrationCommand(container, userService));
        commands.put("captcha", new CaptchaCommand(container, timeout));
    }

    public Command getCommand(String commandName){
        return commands.get(commandName);
    }

}
