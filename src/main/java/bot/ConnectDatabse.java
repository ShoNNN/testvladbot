package bot;

import java.sql.*;
import java.util.Properties;

public class ConnectDatabse {

    private static String login = "tirgwqolnnzbwr";
    private static String password = "d14e7d1f71e41493ee889debcec6750656b55a437755627d2d09b5e0c0d54e02";
    private static String host = "ec2-54-235-181-55.compute-1.amazonaws.com";
    private static String dataBase = "dnikh62r3mrpu";
    private static String port = "5432";
    private static String url;
    private static Properties props;

    static Connection conn = null;

    public static Connection getConnection() {
        if (conn != null) return conn;

        url = "jdbc:postgresql://"+host+":"+port+""+"/"+dataBase;
        props = new Properties();
        props.setProperty("user",login);
        props.setProperty("password", password);
        props.setProperty("ssl","true");
        props.setProperty("sslmode", "verify-ca");
        props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, props);
            if (conn != null) {
                System.out.println("Connected to the database!");
            }
            else {
                System.out.println("Failed to make connection!");
            }
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(){
        try {
            ConnectDatabse.getConnection().close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
