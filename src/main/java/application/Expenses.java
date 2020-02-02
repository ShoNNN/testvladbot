package application;

import bot.DBClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expenses {
    int amount;
    String categoryText;

    public void addExpense(String str){

        DBClient.insert("expense", parseAmount(str), getNowFormatted(), parseCategoryName(str));
    }

    public void getTodayStatistics(){

    }

    public void getMounthStatistics(){

    }

    public void getLast(){

    }

    public void deleteExpense(){

    }

    /* return today date */
    public String getNowFormatted(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.format(new Date());
    }

    public static String parseAmount(String text){
        String amount = null;
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){
            amount = text.substring(matcher.start(), matcher.end());
        }
        return amount;
    }

    /* ----- переписать на один метод с тернарным оператором -----*/
    public static String parseCategoryName(String text){
        String categoryName = null;
        Pattern pattern = Pattern.compile("[а-яА-Яa-zA-Z]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){
            categoryName = text.substring(matcher.start(), matcher.end());
        }
        return categoryName;
    }
}
