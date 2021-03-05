package sk.kosickaacademic.simon;

import org.json.JSONArray;
import org.json.JSONObject;
import sk.kosickaacademic.simon.entity.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Util {
    public String getJSON(ArrayList<User> list){
        if(list.isEmpty()) return "{}";
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
            array.put(userObject);
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
        array.put(userObject);
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
}
