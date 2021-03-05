package sk.kosickaacademic.simon.database;

import sk.kosickaacademic.simon.Log;
import sk.kosickaacademic.simon.Util;
import sk.kosickaacademic.simon.entity.User;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
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
                ps.setString(1, new Util().normalizeName(user.getFirstName()));
                ps.setString(2, new Util().normalizeName(user.getLastName()));
                ps.setInt(3, user.getAge());
                ps.setInt(4, user.getGender().getValue());
                int result = ps.executeUpdate();
                if(result==1){
                    log.printMessage("User successfully added");
                    return true;
                }
            }
            closeConnection(con);
        }catch (SQLException e){
            log.printError(e.toString());
        }
        return false;
    }

    private ArrayList<User> executeSelect(PreparedStatement ps) throws SQLException{
        ArrayList<User> list = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        int count = 0;
        while(rs.next()){
            int id = rs.getInt("id");
            String fName = rs.getString("fname");
            String lName = rs.getString("lname");
            int age = rs.getInt("age");
            int gender = rs.getInt("gender");
            list.add(new User(id, age, fName, lName, gender));
            count++;
        }
        log.printMessage("Number of records - " +count);
        return list;
    }

    public ArrayList<User> getMales(){
        ArrayList<User> list = new ArrayList<>();
        String query = "SELECT * FROM user WHERE gender = 0";
        try(Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            return executeSelect(ps);
        }catch (SQLException e){
            log.printError(e.toString());
        }
        return null;
    }

    public ArrayList<User> getFemales(){
        ArrayList<User> list = new ArrayList<>();
        String query = "SELECT * FROM user WHERE gender = 1";
        try(Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            return executeSelect(ps);
        }catch (SQLException e){
            log.printError(e.toString());
        }
        return null;
    }

    public ArrayList<User> getUsersByAge(int from, int to){
        String query = "SELECT * FROM user WHERE age>=? AND age<=?";
        if(to<from) return null;
        try(Connection con = getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, from);
            ps.setInt(2, to);
            return executeSelect(ps);
        }catch (SQLException e){
            log.printError(e.toString());
        }
        return null;
    }

    public ArrayList<User> getAllUsers(){
        String query = "SELECT * FROM user";
        try(Connection con = getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            return executeSelect(ps);
        }catch (SQLException e){
            log.printError(e.toString());
        }
        return null;
    }

    public ArrayList<User> getUserByID(int id){
        String query = "SELECT * FROM user WHERE id=?";
        try(Connection con = getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            return executeSelect(ps);
        }catch (SQLException e){
            log.printError(e.toString());
        }
        return null;
    }

    public ArrayList<User> getUser(String pattern){
        String query = "SELECT * FROM user WHERE fname LIKE '%'?'%' OR lname LIKE '%'?'%' ";
        try(Connection con = getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            return executeSelect(ps);
        }catch (SQLException e){
            log.printError(e.toString());
        }
        return null;
    }

    public boolean changeAge(int id, int newAge){
        String query = "UPDATE user SET age=? WHERE id=?";
        try(Connection con = getConnection()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, newAge);
            ps.setInt(2, id);
            int result = ps.executeUpdate();
            if(result==1){
                log.printMessage("Successfully changed age");
                return true;
            }
        }catch (SQLException e){
            log.printError(e.toString());
        }
        return false;
    }
}
