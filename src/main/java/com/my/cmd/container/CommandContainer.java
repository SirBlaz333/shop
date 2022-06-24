package com.my.cmd.container;

import com.my.cmd.impl.CaptchaCommand;
import com.my.cmd.impl.RegistrationCommand;
import com.my.cmd.Command;
import com.my.web.captcha.container.CaptchaContainer;
import com.my.web.captcha.container.impl.CaptchaContainerStrategy;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private final Map<String, Command> commands;

    public CommandContainer(CaptchaContainerStrategy containerStrategy, long timeout){
        commands = new HashMap<>();
        CaptchaContainer container = containerStrategy.getContainer();
        commands.put("registration", new RegistrationCommand(container));
        commands.put("captcha", new CaptchaCommand(container, timeout));
    }

    public Command getCommand(String commandName){
        return commands.get(commandName);
    }

}
