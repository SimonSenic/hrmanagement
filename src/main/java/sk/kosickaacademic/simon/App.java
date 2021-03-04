package sk.kosickaacademic.simon;

import sk.kosickaacademic.simon.database.Database;
import sk.kosickaacademic.simon.entity.User;

import java.util.ArrayList;

public class App 
{
    public static void main( String[] args )
    {
        Database db = new Database();
        //db.insertNewUser(new User(98, "Meky", "Zbirka", 0));
        //db.getMales();
        //db.getUsersByAge(50, 100);
        //db.changeAge(3, 86);

        ArrayList<User> list = db.getUser("Meky");
        for(User temp : list) System.out.println(temp.toString());
    }
}
