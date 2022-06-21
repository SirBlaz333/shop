package com.my.web.command;

import java.util.HashMap;
import java.util.Map;

public class WebCommandContainer {

    private final Map<String, WebCommand> commands;

    public WebCommandContainer(){
        commands = new HashMap<>();
        commands.put("login", new LoginCommand());
        commands.put("captcha", new CaptchaCommand());
    }

    public WebCommand getCommand(String commandName){
        return commands.get(commandName);
    }

}
