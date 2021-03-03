package sk.kosickaacademic.simon.database;

import sk.kosickaacademic.simon.Log;
import sk.kosickaacademic.simon.entity.User;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    public boolean insertNewUser(User user){
        String query = "INSERT INTO user (fname, lname, age, gender) VALUES(?, ?, ?, ?)";
        try{
            Connection con = getConnection();
            if(con!=null){
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setInt(3, user.getAge());
                ps.setInt(4, user.getGender().getValue());
                int result = ps.executeUpdate();
                if(result==1){
                    log.printMessage("User successfully added");
                    return true;
                }
            }
            con.close();
        }catch (SQLException e){
            log.printError(e.toString());
        }
        return false;
    }
}
