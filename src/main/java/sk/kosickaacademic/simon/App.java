package sk.kosickaacademic.simon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import sk.kosickaacademic.simon.database.Database;
import sk.kosickaacademic.simon.entity.User;

import java.util.ArrayList;

@ComponentScan(basePackages = "sk.kosickaacademic.simon.controller")
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        Database db = new Database();
        //db.insertNewUser(new User(69, "boRAt", "sagdiyev", 5));
        //db.getMales();
        //db.getUsersByAge(50, 100);
        //db.changeAge(3, 86);

        //ArrayList<User> list = db.getMales();
        //for(User temp : list) System.out.println(temp);

        SpringApplication.run(App.class, args);
    }
}
