package com.iot.usach.incubadora.bot.commands;


import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

// Manejador de comandos
public class CommandManager {

    private Map<String, BotCommand> commandRegistryMap = new HashMap<>();
    private boolean allowCommandsWithUsername;
    private String botUsername;
    private BiConsumer<AbsSender, Message> defaultConsumer;


    public final CommandManager register(BotCommand botCommand) {
        if (!commandRegistryMap.containsKey(botCommand.getCommandIdentifier())) {
            commandRegistryMap.put(botCommand.getCommandIdentifier(), botCommand);
        }
        return this;
    }

    public final boolean executeCommand(AbsSender absSender, Message message) {
        if (message.hasText()) {
            String text = message.getText();
            if (text.startsWith(BotCommand.COMMAND_INIT_CHARACTER)) {
                String commandMessage = text.substring(1);
                String[] commandSplit = commandMessage.split(BotCommand.COMMAND_PARAMETER_SEPARATOR_REGEXP);

                String command = removeUsernameFromCommandIfNeeded(commandSplit[0]);

                if (commandRegistryMap.containsKey(command)) {
                    String[] parameters = Arrays.copyOfRange(commandSplit, 1, commandSplit.length);
                    commandRegistryMap.get(command).processMessage(absSender, message, parameters);
                    return true;
                } else if (defaultConsumer != null) {
                    defaultConsumer.accept(absSender, message);
                    return true;
                }
            }
        }
        return false;
    }

    private String removeUsernameFromCommandIfNeeded(String command) {
        if (allowCommandsWithUsername) {
            return command.replace("@" + botUsername, "").trim();
        }
        return command;
    }
}
