package sk.kosickaacademic.simon.database;

import sk.kosickaacademic.simon.Log;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    Log log = new Log();

    public Connection getConnection() {
        try{
            Properties prop = new Properties();
            InputStream loader = getClass().getClassLoader().getResourceAsStream("db.properties");
            prop.load(loader);
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            Connection con = DriverManager.getConnection(url, username, password);
            log.printMessage("Connection successful");
            return con;
        }catch (Exception e){
            log.printError(e.toString());
        }
        return null;
    }

    public void closeConnection(Connection con){
        try{
            if(con!=null) con.close();
        }catch (SQLException e){
            log.printError(e.toString());
        }
    }
}
