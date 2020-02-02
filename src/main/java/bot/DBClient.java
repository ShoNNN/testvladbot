package bot;

import application.Expenses;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DBClient {

    static Connection c = null;
    static Statement stmt = null;
    static  ResultSet rs = null;

    public static void main(String[] args) {

        Expenses expenses = new Expenses();
        expenses.addExpense("1234 coffee");

//        System.out.println(getLastIndex("expense"));
//        insert("expense", "123", "2020-01-31 14:55:21", "dinner");
//        delete("expense", getLastIndex("expense"));
//        fetchAll("category", "codename", "name", "aliases");

        ConnectDatabse.closeConnection();

    }

    /* дописать возвращаемое значение, и сейчас селектится все плохо */
    public static void select(String tableName, String...values) {
        try {
            c = ConnectDatabse.getConnection();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "select "+ values[0]+ "from " + tableName + ";";
            stmt.executeQuery(sql);
            rs = stmt.getResultSet();


            stmt.close();
            c.commit();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Select was successfully");
    }

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

    public static void delete(String tableName, int rowId){
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
