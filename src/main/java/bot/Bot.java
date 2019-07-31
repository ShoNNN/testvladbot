package bot;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
//import org.telegram.telegrambots.ApiContextInitializerext;
import org.telegram.telegrambots.ApiContextInitializer;
//import org.telegram.telegrambots.TelegramBotsApi;
//import org.telegram.telegrambots.api.methods.send.SendMessage;
//import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.exceptions.TelegramApiException;
//import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import sun.net.SocksProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bot extends TelegramLongPollingBot {

    Logger log = Logger.getLogger(Logger.class.getName());

    public static void main(String[] args) {

        makeBot();

    }

    public static void makeBot(){
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
     * @param update Содержит сообщение от пользователя.
     */

    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);


    }

    /**
     * Метод для настройки сообщения и его отправки.
     * @param chatId id чата
     * @param s Строка, которую необходимот отправить в качестве сообщения.
     */

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        setButtons(sendMessage);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException ex) {
            log.log(Level.SEVERE, "Exception: ", ex.toString());
        }
    }

    /** @deprecated */
    @Deprecated
    public final Message sendMessage(SendMessage sendMessage) throws TelegramApiException {
        if (sendMessage == null) {
            throw new TelegramApiException("Parameter sendMessage can not be null");
        } else {
            return (Message)this.sendApiMethod(sendMessage);
        }
    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */

    public String getBotUsername() {
        return "the_vlad_test_bot";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */

    public String getBotToken() {
        return "776333607:AAEugu2_Gm_wEB8GK-9N7W0UR_anh4iXN9E";
    }

    public synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Привет"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("Help!"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}
