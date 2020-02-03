package bot;

import application.Categories;
import application.Expenses;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBClient {

    static Connection c = null;
    static Statement stmt = null;
    static  ResultSet rs = null;

    public static void main(String[] args) {
//
//        Expenses expenses = new Expenses();
//        expenses.addExpense("1234 coffee");
//        System.out.println(expenses.getLast());

//        System.out.println(Categories.getCategory("автобус"));
//        HashMap<String, String> map = selectGetGategoryNameAndAliases();
//        for (Map.Entry<String, String> iterator:map.entrySet()) {
//            System.out.println("key = " + iterator.getKey() + " value = " + iterator.getValue());
//        }

//        System.out.println(getLastIndex("expense"));
//        insert("expense", "123", "2020-01-31 14:55:21", "dinner");
//        delete("expense", getLastIndex("expense"));
//        fetchAll("category", "codename", "name", "aliases");

        ConnectDatabse.closeConnection();

    }

    /* не работает как нужно. дописать возвращаемое значение, и сейчас селектится все плохо */
    public static void select(String tableName, String...values) {
        try {
            c = ConnectDatabse.getConnection();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "select "+ values[0]+ " from " + tableName + ";";
            stmt.executeQuery(sql);
            rs = stmt.getResultSet();

            while (rs.next()){
                System.out.println(rs);
//                list.add(rs.getCursorName());
            }

            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Select was successfully");
    }

    public static HashMap<String, String> selectGetGategoryNameAndAliases(){
        HashMap<String, String> map = new HashMap<>();
        try {
            c = ConnectDatabse.getConnection();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "select codename, aliases from category c ;";
            stmt.executeQuery(sql);
            rs = stmt.getResultSet();

            while (rs.next()) {
                map.put(rs.getString(1), rs.getString(2));
            }

            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage());
            System.exit(0);
        }
        System.out.println("Select was successfully");
        return map;
    }

    public static String selectGetLast10Expense() {
        String result = null;
        try {
            c = ConnectDatabse.getConnection();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "select e.id, e.amount, c.name\n" +
                    " from expense e left join category c\n" +
                    " on c.codename=e.category_codename \n" +
                    " order by created desc limit 10";
            stmt.executeQuery(sql);
            rs = stmt.getResultSet();

            String [] amount = null;
            String [] categoryName = null;
            StringBuilder stringBuilder = new StringBuilder();
            while (rs.next()) {
                amount = rs.getString(2).split(" ");
                categoryName = rs.getString(3).split(" ");
                stringBuilder.append("потрачено " + amount[0] + " на " + categoryName[0] + "\n");
            }

            result = stringBuilder.toString();
            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage());
            System.exit(0);
        }
        System.out.println("Select was successfully");
        return result;
    }

    /* работает */
    public static void insert(String tableName, String...values ) {
        try {
            c = ConnectDatabse.getConnection();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "insert into "+ tableName +" (amount,created,category_codename) " +
                    "values ('"+ values[0] +"', '"+ values[1]+ "', '"+ values[2] +"');";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public static void deleteById(String tableName, int rowId){
        try {
            c = ConnectDatabse.getConnection();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "DELETE FROM "+ tableName +" WHERE id = " + rowId + ";";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Delete was successfully");
    }

    /* почти работает, нужно сделать нормальный форма возвращаемого значения
     * Логику можно применить к методу select() */
    public static void fetchAll(String tableName, String... columns){
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String[]> result = new ArrayList<>();
        try {
            c = ConnectDatabse.getConnection();
            c.setAutoCommit(false);
            stmt = c.createStatement();

            /* build column list for query */
            for (int i = 0; i < columns.length; i++){
                stringBuilder.append(columns[i]);
                if (i == columns.length-1) break;
                stringBuilder.append(", ");
            }

            String sql = "SELECT " + stringBuilder.toString() + " FROM " + tableName + ";";
            stmt.execute(sql);
            rs = stmt.getResultSet();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
//            for (int i = 1; rs.next() && i <= columns.length; i++){
//                String [] row = rs.getString(i).split(" ");
//                result.add(row);
//                String str = rs.getString(1);
//                System.out.print(str + " ");
//            }

        } catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
//        return result;
    }

}
