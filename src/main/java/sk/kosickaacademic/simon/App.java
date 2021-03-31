package sk.kosickaacademic.simon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import sk.kosickaacademic.simon.database.DatabaseMongo;
import sk.kosickaacademic.simon.database.DatabaseMySQL;
import sk.kosickaacademic.simon.database.SportDatabaseMongo;
import sk.kosickaacademic.simon.entity.User;

import java.util.ArrayList;

@ComponentScan(basePackages = "sk.kosickaacademic.simon.controller")
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        DatabaseMySQL db = new DatabaseMySQL();
        //db.insertNewUser(new User(69, "boRAt", "sagdiyev", 5));
        //db.getMales();
        //db.getUsersByAge(50, 100);
        //db.changeAge(3, 86);

        //ArrayList<User> list = db.getMales();
        //for(User temp : list) System.out.println(temp);

        DatabaseMongo db2 = new DatabaseMongo();
        //db2.insertNewUser(new User(53, "Joe", "Bidden", 0));
        //System.out.println(db2.getAllUsers());

        SportDatabaseMongo db3 = new SportDatabaseMongo();
        //ArrayList list = new ArrayList<String>();
        //list.add("Ice Hockey");
        //list.add("Basketball");
        //list.add("Golfing");
        //db3.insertUserData("Janik", list);
        //db3.deleteUser("Janik");

        SpringApplication.run(App.class, args);
    }
}
