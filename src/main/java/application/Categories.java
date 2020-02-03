package application;

import bot.DBClient;

import java.util.Map;

public class Categories {

    String codename;
    String name;
    boolean isBaseExpense;
    String [] aliases;
    static Map<String, String> aliasesMap = null;

    public static String loadCategories(){
        fillAliases();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> iterator: aliasesMap.entrySet()) {
            String key = iterator.getKey();
            String values = iterator.getValue();
            stringBuilder.append("* " + key + " (" + values + ")\n");
        }
        return stringBuilder.toString();
    }

    /* fill field aliases array */
    public static void fillAliases(){
        aliasesMap = DBClient.selectGetGategoryNameAndAliases();
    }

    public void getAllCategories(){

    }

    /* returns a category according to one of its aliases */
    public static String getCategory(String categoryName){
        fillAliases();
        String result = null;
        for (Map.Entry<String, String> iterator: aliasesMap.entrySet()) {
            String key = iterator.getKey();
            String [] array = iterator.getValue().split(", ");
            for (String str:array) {
                if (categoryName.toLowerCase().equals(str.toLowerCase())){
                    result = key;
                }
            }
        }

        return result;
    }

}
