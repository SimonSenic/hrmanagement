package sk.kosickaacademic.simon;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sk.kosickaacademic.simon.database.Database;
import sk.kosickaacademic.simon.entity.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Util {
    public String getJSON(ArrayList<User> list){
        if(list.isEmpty() || list==null) return "{}";
        JSONObject object = new JSONObject();
        object.put("datetime", getCurrentTime());
        object.put("size", list.size());
        JSONArray array = new JSONArray();
        for(User temp : list){
            JSONObject userObject = new JSONObject();
            userObject.put("id", temp.getId());
            userObject.put("fName", temp.getFirstName());
            userObject.put("lName", temp.getLastName());
            userObject.put("gender", temp.getGender());
            userObject.put("age", temp.getAge());
            array.add(userObject);
        }
        object.put("users", array);
        return object.toString();
    }

    public String getJSON(User user){
        if(user==null) return "{}";
        JSONObject object = new JSONObject();
        object.put("datetime", getCurrentTime());
        object.put("size", 1);
        JSONArray array = new JSONArray();
        JSONObject userObject = new JSONObject();
        userObject.put("id", user.getId());
        userObject.put("fName", user.getFirstName());
        userObject.put("lName", user.getLastName());
        userObject.put("gender", user.getGender());
        userObject.put("age", user.getAge());
        array.add(userObject);
        object.put("users", array);
        return object.toString();
    }

    public String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String normalizeName(String name){
        if(name==null || name.equals("")) return null;
        return Character.toString(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getOverview(ArrayList<User> list){
        if(list.isEmpty() || list==null) return "{}";
        JSONObject object = new JSONObject();
        Database db = new Database();
        object.put("records", list.size());
        object.put("males", db.getMales().size());
        object.put("females", db.getFemales().size());

        int minAge=list.get(0).getAge();
        int maxAge=list.get(0).getAge();
        int avgAge=0;
        for(int i=1; i<list.size(); i++){
            if(minAge>list.get(i).getAge())
                minAge = list.get(i).getAge();
            if(maxAge<list.get(i).getAge())
                maxAge = list.get(i).getAge();
            avgAge+=list.get(i).getAge();
        }
        avgAge=avgAge/list.size();
        object.put("minAge", minAge);
        object.put("maxAge", maxAge);
        object.put("avgAge", avgAge);

        return object.toString();
    }
}
