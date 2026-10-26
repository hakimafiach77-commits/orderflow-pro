package ma.fstt.util;
import java.sql.*;
public final class DBConnection {
    private DBConnection(){}
    private static String env(String key,String fallback){String val=System.getenv(key);return val==null||val.isBlank()?fallback:val;}
    static {try{Class.forName("com.mysql.cj.jdbc.Driver");}catch(ClassNotFoundException e){throw new ExceptionInInitializerError(e);}}
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(env("DB_URL","jdbc:mysql://localhost:3306/gestion_commandes?useSSL=false&serverTimezone=UTC"),env("DB_USER","root"),env("DB_PASSWORD",""));
    }
}
