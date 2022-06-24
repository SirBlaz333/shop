package com.my.cmd.container;

import com.my.cmd.impl.CaptchaCommand;
import com.my.cmd.impl.LoginCommand;
import com.my.cmd.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private final Map<String, Command> commands;

    public CommandContainer(){
        commands = new HashMap<>();
        commands.put("login", new LoginCommand());
        commands.put("captcha", new CaptchaCommand());
    }

    public Command getCommand(String commandName){
        return commands.get(commandName);
    }

}
