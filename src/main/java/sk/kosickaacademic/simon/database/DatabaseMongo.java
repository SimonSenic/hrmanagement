package sk.kosickaacademic.simon.database;

import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import sk.kosickaacademic.simon.Log;
import sk.kosickaacademic.simon.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseMongo {
    Log log = new Log();
    MongoClient mc = new MongoClient("localhost", 27017);
    MongoDatabase db = mc.getDatabase("myfirstdb");

    public void insertNewUser(User user){
        Document doc = new Document();
        doc.append("fname", user.getFirstName());
        doc.append("lname", user.getLastName());
        doc.append("gender", user.getGender().getValue());
        doc.append("age", user.getAge());
        db.getCollection("users").insertOne(doc);
        log.printMessage("User added successfully");
    }

    public List<User> getAllUsers(){
        List<User> list = new ArrayList<>();

        return list;
    }
}
