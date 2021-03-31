package sk.kosickaacademic.simon.database;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import sk.kosickaacademic.simon.Log;

import java.util.ArrayList;

public class SportDatabaseMongo {
    Log log = new Log();
    MongoClient mc = new MongoClient("localhost", 27017);
    MongoDatabase db = mc.getDatabase("myfirstdb");

    public boolean insertUserData(String name, ArrayList<String> sports){
        if(name==null || name.equals("") || sports.isEmpty()) return false;
        Document doc = new Document();
        doc.append("name", name);
        doc.append("sports", sports);
        db.getCollection("sport").insertOne(doc);
        log.printMessage("User added successfully");
        return true;
    }

    public boolean deleteUser(String name){
        if(name==null || name.equals("")) return false;
        db.getCollection("sport").deleteOne(Filters.eq("name", name));
        log.printMessage("User successfully deleted");
        return true;
    }

    public boolean updateUser(ArrayList<String> sports){
        BasicDBObject object = new BasicDBObject();
        object.append("sports", sports);

        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", object);
        //db.getCollection("sport").updateOne();
        return true;
    }
}
