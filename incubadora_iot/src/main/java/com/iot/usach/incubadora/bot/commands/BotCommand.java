package com.iot.usach.incubadora.bot.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public abstract class BotCommand {
    public final static String COMMAND_INIT_CHARACTER = "/";
    public static final String COMMAND_PARAMETER_SEPARATOR_REGEXP = "\\s+";
    private final static int COMMAND_MAX_LENGTH = 32;

    private final String commandIdentifier;
    private final String description;

    /**
     * Construct a command
     *
     * @param commandIdentifier the unique identifier of this command (e.g. the command string to
     *                          enter into chat)
     * @param description       the description of this command
     */
    public BotCommand(String commandIdentifier, String description) {

        if (commandIdentifier == null || commandIdentifier.isEmpty()) {
            throw new IllegalArgumentException("commandIdentifier for command cannot be null or empty");
        }

        if (commandIdentifier.startsWith(COMMAND_INIT_CHARACTER)) {
            commandIdentifier = commandIdentifier.substring(1);
        }

        if (commandIdentifier.length() + 1 > COMMAND_MAX_LENGTH) {
            throw new IllegalArgumentException("commandIdentifier cannot be longer than " + COMMAND_MAX_LENGTH + " (including " + COMMAND_INIT_CHARACTER + ")");
        }

        this.commandIdentifier = commandIdentifier.toLowerCase();
        this.description = description;
    }

    public final void enviarMensaje(AbsSender absSender, Chat chat, String texto){
        SendMessage response = new SendMessage(chat.getId(), texto);
        try {
            absSender.execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the identifier of this command
     *
     * @return the identifier
     */
    public final String getCommandIdentifier() {
        return commandIdentifier;
    }

    /**
     * Get the description of this command
     *
     * @return the description as String
     */
    public final String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "<b>" + COMMAND_INIT_CHARACTER + getCommandIdentifier() +
                "</b>\n" + getDescription();
    }

    /**
     * Process the message and execute the command
     *
     * @param absSender absSender to send messages over
     * @param message   the message to process
     * @param arguments passed arguments
     */
    void processMessage(AbsSender absSender, Message message, String[] arguments) {
        execute(absSender, message.getFrom(), message.getChat(), arguments);
    }

    /**
     * Execute the command
     *
     * @param absSender absSender to send messages over
     * @param user      the user who sent the command
     * @param chat      the chat, to be able to send replies
     * @param arguments passed arguments
     */
    public abstract void execute(AbsSender absSender, User user, Chat chat, String[] arguments);
}