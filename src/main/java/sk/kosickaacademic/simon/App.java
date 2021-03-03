package sk.kosickaacademic.simon;

import sk.kosickaacademic.simon.database.Database;
import sk.kosickaacademic.simon.entity.User;

public class App 
{
    public static void main( String[] args )
    {
        Database db = new Database();
        //db.insertNewUser(new User(98, "Meky", "Zbirka", 0));
    }
}
