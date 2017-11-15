package com.iot.usach.incubadora.bot;

import com.iot.usach.incubadora.repository.DatoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

@Component
public class IncubadoraBot extends TelegramLongPollingBot{
    private static final Logger logger = LoggerFactory.getLogger(IncubadoraBot.class);
    private long chatID;


    @Autowired
    private SpringSerialPortConnector springSerialPortConnector;

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;


    public void enviarMensaje(String mensaje){

        SendMessage response = new SendMessage();
        response.setChatId(chatID);
        response.setText(mensaje);

        try {
            this.sendApiMethod(response);
            //this.sendMessage(response);
            logger.info("Sent message \"{}\" to {}", response.getText(), chatID);
        } catch (TelegramApiException e) {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", response.getText(), chatID, e.getMessage());
        }

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(chatId);
            String text = message.getText();
            response.setText(text);

            logger.info("Soy el BOT y me llegó: \n-> {}", message.toString());
            this.chatID = update.getMessage().getChatId();

            try {
                springSerialPortConnector.sendMessage(text);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                this.sendApiMethod(response);
                //this.sendMessage(response);
                logger.info("Sent message \"{}\" to {}", text, chatId);
            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    /*
    @Override
    public void onUpdatesReceived(List<Update> updates) {

    }

    @Override
    public void onClosing() {

    }

    */


}
