package sk.kosickaacademic.simon.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaacademic.simon.Gender;
import sk.kosickaacademic.simon.Util;
import sk.kosickaacademic.simon.database.Database;
import sk.kosickaacademic.simon.entity.User;

import java.util.ArrayList;

@RestController
public class Controller {
    @PostMapping("/user/new")
    public ResponseEntity<String> insertNewUser(@RequestBody String data){
        try{
            JSONObject object = (JSONObject) new JSONParser().parse(data);
            String fName = (String) object.get("fName");
            String lName = (String) object.get("lName");
            String gender = (String) object.get("gender");
            int age = Integer.parseInt(String.valueOf(object.get("age")));
            if(fName==null || fName.equals("") || lName==null || lName.equals("") || age<0){
                JSONObject objError = new JSONObject();
                objError.put("error", "Invalid entry");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(objError.toJSONString());
            }
            Gender g;
            if(gender.equalsIgnoreCase("male")) g=Gender.MALE;
            else if (gender.equalsIgnoreCase("female")) g=Gender.FEMALE;
            else g=Gender.OTHER;
            if(new Database().insertNewUser(new User(age, fName, lName, g.getValue()))){
                JSONObject objMessage = new JSONObject();
                objMessage.put("server", "User added successfully");
                return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(objMessage.toJSONString());
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/user/edit/age")
    public ResponseEntity<String> changeAge(@RequestBody String data){
        try{
            JSONObject object = (JSONObject) new JSONParser().parse(data);
            int id = Integer.parseInt(String.valueOf(object.get("id")));
            int newAge = Integer.parseInt(String.valueOf(object.get("newAge")));
            if(id<0 || newAge<1){
                JSONObject objError = new JSONObject();
                objError.put("error", "Invalid entry");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(objError.toJSONString());
            }
            if(new Database().changeAge(id, newAge)){
                JSONObject objMessage = new JSONObject();
                objMessage.put("server", "Age changed successfully");
                return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(objMessage.toJSONString());
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/users/males")
    public ResponseEntity<String> getMales(){
        ArrayList<User> list = new Database().getMales();
        String json = new Util().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @GetMapping("/users/females")
    public ResponseEntity<String> getFemales(){
        ArrayList<User> list = new Database().getFemales();
        String json = new Util().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers(){
        ArrayList<User> list = new Database().getAllUsers();
        String json = new Util().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @GetMapping("/users/age")
    public ResponseEntity<String> getUsersByAge(@RequestBody String data){
        try{
            JSONObject object = (JSONObject) new JSONParser().parse(data);
            int from = Integer.parseInt(String.valueOf(object.get("from")));
            int to = Integer.parseInt(String.valueOf(object.get("to")));
            if(from<0 || to<0 || from>to){
                JSONObject objError = new JSONObject();
                objError.put("error", "Invalid entry");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(objError.toJSONString());
            }
            ArrayList<User> list = new Database().getUsersByAge(from, to);
            String json = new Util().getJSON(list);
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<String> getUserByID(@PathVariable int id){
        ArrayList<User> list = new Database().getUserByID(id);
        String json = new Util().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @GetMapping("/user/{pattern}")
    public ResponseEntity<String> getUser(@PathVariable String pattern){
        ArrayList<User> list = new Database().getUser(pattern);
        String json = new Util().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }
}
