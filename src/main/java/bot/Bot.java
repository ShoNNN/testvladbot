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
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import sun.net.SocksProxy;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Bot extends TelegramLongPollingBot {

    Bot(){}

    Bot(DefaultBotOptions options) {
        super(options);
    }

    Logger log = Logger.getLogger(Logger.class.getName());

    static String PROXY_HOST = "157.230.24.65" /* proxy host */;
    static Integer PROXY_PORT = 80 /* proxy port */;

    public static void main(String[] args) {

//        makeProxy();
        makeBot();

    }

    public static void makeProxy(){
        System.getProperties().put( "proxySet", "true" );
        System.getProperties().put( "socksProxyHost", "127.0.0.1" );
        System.getProperties().put( "socksProxyPort", "9150" );

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

    public static void makeProxyBot(){
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        // Set up Http proxy
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        HttpHost httpHost = new HttpHost(PROXY_HOST, PROXY_PORT);

        RequestConfig requestConfig = RequestConfig.custom().setProxy(httpHost).setAuthenticationEnabled(false).build();
        botOptions.setRequestConfig(requestConfig);
        botOptions.setProxyHost(PROXY_HOST);
        botOptions.setProxyPort(PROXY_PORT);


        try {
            telegramBotsApi.registerBot(new Bot(botOptions));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public static Bot getBot(){
        return new Bot();
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
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
//        try {
//            sendMessage(sendMessage);
//        } catch (TelegramApiException ex) {
//            log.log(Level.SEVERE, "Exception: ", ex.toString());
//        }
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

}
