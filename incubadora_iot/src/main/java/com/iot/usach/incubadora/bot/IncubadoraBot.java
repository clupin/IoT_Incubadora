package com.iot.usach.incubadora.bot;

import com.iot.usach.incubadora.bot.commands.BotCommand;
import com.iot.usach.incubadora.bot.commands.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class IncubadoraBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(IncubadoraBot.class);
    private long chatID;
    private CommandManager commandManager;

    @Autowired
    private SpringSerialPortConnector springSerialPortConnector;

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    public IncubadoraBot() {
        commandManager = new CommandManager();
        commandManager.register(new BotCommand("estado", "") {
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
                try {
                    springSerialPortConnector.sendMessage("00\n");
                } catch (IOException e) {
                    enviarMensaje(absSender, chat, "Ha ocurrido un error de comunicación con la incubadora");
                }
            }
        }).register(new BotCommand("lampara", "") {
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
                try {
                    if(arguments.length > 0){
                        String message = "11";
                        if(arguments[0].equalsIgnoreCase("prender")){
                            message+="255\n";
                        }else if(arguments[0].equalsIgnoreCase("apagar")){
                            message+="0\n";
                        }
                        springSerialPortConnector.sendMessage(message);
                    }else{
                        springSerialPortConnector.sendMessage("10\n");
                    }
                } catch (IOException e) {
                    enviarMensaje(absSender, chat, "Ha ocurrido un error de comunicación con la incubadora");
                }
            }
        }).register(new BotCommand("ventilador", "") {
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
                try {
                    if(arguments.length > 0){
                        String message = "21";
                        if(arguments[0].equalsIgnoreCase("prender")){
                            message+="1\n";
                        }else if(arguments[0].equalsIgnoreCase("apagar")){
                            message+="0\n";
                        }
                        springSerialPortConnector.sendMessage(message);
                    }else{
                        springSerialPortConnector.sendMessage("20\n");
                    }
                } catch (IOException e) {
                    enviarMensaje(absSender, chat, "Ha ocurrido un error de comunicación con la incubadora");
                }
            }
        }).register(new BotCommand("bandeja", "") {
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
                try {
                    if(arguments.length > 0){
                        String message = "31";
                        if(arguments[0].equalsIgnoreCase("girar")){
                            message+="180\n";
                            springSerialPortConnector.sendMessage(message);
                        }
                    }else{
                        springSerialPortConnector.sendMessage("30\n");
                    }
                } catch (IOException e) {
                    enviarMensaje(absSender, chat, "Ha ocurrido un error de comunicación con la incubadora");
                }
            }
        }).register(new BotCommand("temperatura", "") {
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
                try {
                    springSerialPortConnector.sendMessage("40\n");
                } catch (IOException e) {
                    enviarMensaje(absSender, chat, "Ha ocurrido un error de comunicación con la incubadora");
                }
            }
        });
    }

    private InlineKeyboardMarkup generarBotones(String[]... botones){


        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        ArrayList<InlineKeyboardButton> lista = new ArrayList<>();

        for (String[] boton : botones) {
            InlineKeyboardButton inlineButton = new InlineKeyboardButton(boton[0]);
            inlineButton.setCallbackData(boton[1]);
            lista.add(inlineButton);
        }
        rows.add(lista);
        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }


    void enviarMensaje(String mensaje){

        SendMessage response = new SendMessage();
        response.setChatId(chatID);
        logger.info("->>>>{}", mensaje);
        String operacion = mensaje.split("\\|")[1];
        String respuesta = mensaje.split("\\|")[0];
        if(operacion.charAt(1) == '0'){
            switch (operacion.charAt(0)){
                case '0':
                    String[] estados = respuesta.split("/");
                    response.setText("Lampara: "+estados[0]+" \nVentilador: "+(Integer.parseInt(estados[1])==0?"Apagado":"Encendido")+" \nBandeja: "+estados[2]+" \nTemperatura: "+estados[3]);
                    break;
                // Lampara
                case '1':
                    response.setReplyMarkup(generarBotones(new String[]{"Apagar","110"}, new String[]{"Encender","11255"}));
                    response.setText("Lampara: "+(Integer.parseInt(respuesta)==0?"Apagada":"Encendida[{}]".replace("{}", respuesta)));
                    break;
                // Ventilador
                case '2':
                    response.setReplyMarkup(generarBotones(new String[]{"Apagar","210"}, new String[]{"Encender","211"}));
                    response.setText("Ventilador: "+(Integer.parseInt(respuesta)==0?"Apagado":"Encendido"));
                    break;
                // Bandeja
                case '3':
                    response.setReplyMarkup(generarBotones(new String[]{"Girar en 180º","31180"}));
                    response.setText("Bandeja: "+respuesta+"º");
                    break;
                // Temperatura
                case '4':
                    response.setText("Temperatura: "+respuesta+"º");
                    break;
                default:
                    response.setText("Estado:"+respuesta);
                    break;
            }
        }

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
        if(update.hasCallbackQuery()){
            try {
                String msg = update.getCallbackQuery().getData();
                springSerialPortConnector.sendMessage(msg+"\n");
                AnswerCallbackQuery answer = new AnswerCallbackQuery();
                answer.setCallbackQueryId(update.getCallbackQuery().getId());

                //EditMessageText editmessagetext = new EditMessageText();
                //editmessagetext.setChatId(update.getCallbackQuery().getChatInstance());
                //editmessagetext.setMessageId(update.getMessage().)
                logger.info("UPDATE_DATA: {}", msg.substring(2));
                switch (msg.charAt(0)){
                    // Lampara
                    case '1':
                        answer.setText("Lampara "+( Integer.parseInt(msg.substring(2)) == 0?"Apagada":"Encendida" ));
                        springSerialPortConnector.sendMessage("10\n");
                        break;
                    // Ventilador
                    case '2':
                        answer.setText("Ventildaor "+( Integer.parseInt(msg.substring(2)) == 0?"Apagado":"Encendido" ));
                        springSerialPortConnector.sendMessage("20\n");
                        break;
                    // Bandeja
                    case '3':
                        answer.setText("Bandeja "+( "Girada en: "+msg.substring(2) ));
                        springSerialPortConnector.sendMessage("30\n");
                        break;
                    default:
                        break;
                }
                execute(answer);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            logger.info("Soy el BOT y me llegó: \n-> {}", message.toString());
            this.chatID = update.getMessage().getChatId();
            if(message.isCommand()){
                commandManager.executeCommand(this, message);
                logger.info("COMANDO!!!{}");
            }
            logger.info("Soy{}", message);

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
