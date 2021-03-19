package sk.kosickaacademic.simon;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sk.kosickaacademic.simon.entity.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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
        object.put("records", list.size());
        int countMales=0;
        int countFemales=0;
        int minAge=list.get(0).getAge();
        int maxAge=list.get(0).getAge();
        int avgAge=0;
        for(int i=1; i<list.size(); i++){
            if(list.get(i).getGender().getValue()==0)
                countMales++;
            if(list.get(i).getGender().getValue()==1)
                countFemales++;
            if(minAge>list.get(i).getAge())
                minAge = list.get(i).getAge();
            if(maxAge<list.get(i).getAge())
                maxAge = list.get(i).getAge();
            avgAge+=list.get(i).getAge();
        }
        avgAge=avgAge/list.size();
        object.put("males", countMales);
        object.put("females", countFemales);
        object.put("minAge", minAge);
        object.put("maxAge", maxAge);
        object.put("avgAge", avgAge);
        return object.toString();
    }

    public String getToken(){
        String token="";
        Random rnd = new Random();
        for(int i=0; i<40; i++){
            int a = rnd.nextInt(3);
            if(a==0) token+=(char) (rnd.nextInt(26)+65);
            else if(a==1) token+=(char) (rnd.nextInt(26)+97);
            else token+=(char) (rnd.nextInt(10)+48);
        }
        return token;
    }
}
