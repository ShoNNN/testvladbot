package bot;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
//import org.telegram.telegrambots.ApiContextInitializerext;
import org.telegram.telegrambots.ApiContextInitializer;
//import org.telegram.telegrambots.TelegramBotsApi;
//import org.telegram.telegrambots.api.methods.send.SendMessage;
//import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.exceptions.TelegramApiException;
//import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
//import org.telegram.telegrambots.meta.ApiContext;
//import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Bot extends TelegramLongPollingBot {

    Logger log = Logger.getLogger(Logger.class.getName());

    /** default constructor */
    Bot() {
    }

    /** constructor with options */
    Bot(DefaultBotOptions options) {
        super(options);
    }

    public static void main(String[] args) {

        makeBot();

    }

    public static void makeBot() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для приема сообщений.
     *
     * @param update Содержит сообщение от пользователя.
     */

//    public void onUpdateReceived(Update update) {
//        String message = update.getMessage().getText();
//        sendMsg(update.getMessage().getChatId().toString(), message);
//    }

    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage(); // Это нам понадобится
        String txt = msg.getText();
        if (txt.equals("/start")) {
            sendMsg(msg, "Привет! Меня зовут " + getBotUsername() + " А как твоё имя?");

        }
        String name = msg.getText();
        if (!name.isEmpty() && !name.equals("/start")
                && !txt.equals("Хорошо")
                && !txt.equals("Отлично")
                && !txt.equals("Плохо")){
            sendMsg(msg, "Привет! "+ getClientName(name) +". теперь я могу немного больше!");
            sendMsg(msg, "как у тебя дела?");
        }
        switch (msg.getText()){
            case "Хорошо":
                sendMsg(msg, "Молодец, у меня тоже");
                break;
            case "Отлично":
                sendMsg(msg, "Так держать!");
                break;
            case "Плохо":
                sendMsg(msg, "и такое бывает :D");
                break;
        }
    }

//    /**
//     * Метод для настройки сообщения и его отправки.
//     *
//     * @param chatId id чата
//     * @param s      Строка, которую необходимот отправить в качестве сообщения.
//     */
//
//    public synchronized void sendMsg(String chatId, String s) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        sendMessage.setChatId(chatId);
//        sendMessage.setText(s);
//        sendMessage.setText("Привет!");
//    }

    @SuppressWarnings("deprecation") // Означает то, что в новых версиях метод уберут или заменят
    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
        s.enableMarkdown(true);

        //создаём клавиатуру
        ReplyKeyboardMarkup replyKeyBoadrdMarkup = new ReplyKeyboardMarkup();
        s.setReplyMarkup(replyKeyBoadrdMarkup);
        replyKeyBoadrdMarkup.setSelective(true);
        replyKeyBoadrdMarkup.setResizeKeyboard(true);
        replyKeyBoadrdMarkup.setOneTimeKeyboard(false);

        //создайм список клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();

        //первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();

        //добавляем клавиши
        keyboardFirstRow.add("Отлично");
        keyboardSecondRow.add("Хорошо");
        keyboardThirdRow.add("Плохо");

        //добавляем спислк в клавиатуру
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);

        //устанавливаем этот список на нашу клавиатуру
        replyKeyBoadrdMarkup.setKeyboard(keyboard);

        s.setChatId(msg.getChatId().toString()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        s.setText(text);
        try { //Чтобы не крашнулась программа при вылете Exception
            sendMessage(s);
        } catch (TelegramApiException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     *
     * @return имя бота
     */

    public String getBotUsername() {
        return "the_vlad_test_bot";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     *
     * @return token для бота
     */

    public String getBotToken() {
        return "776333607:AAEugu2_Gm_wEB8GK-9N7W0UR_anh4iXN9E";
    }

    public String getClientName(String clientName) {
        //Message msg = update.getMessage();
        //String clientName = msg.getText();
        return clientName;
    }

}