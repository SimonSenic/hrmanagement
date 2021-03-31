package sk.kosickaacademic.simon.controller;

import org.json.XML;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaacademic.simon.Gender;
import sk.kosickaacademic.simon.Util;
import sk.kosickaacademic.simon.database.DatabaseMongo;
import sk.kosickaacademic.simon.database.DatabaseMySQL;
import sk.kosickaacademic.simon.database.SportDatabaseMongo;
import sk.kosickaacademic.simon.entity.User;

import java.util.ArrayList;

@RestController
public class Controller {
    @PostMapping("/user/add")
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
            if(new DatabaseMySQL().insertNewUser(new User(age, fName, lName, g.getValue()))
                    && new DatabaseMongo().insertNewUser(new User(age, fName, lName, g.getValue()))){
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
            if(new DatabaseMySQL().changeAge(id, newAge)){
                JSONObject objMessage = new JSONObject();
                objMessage.put("server", "Age changed successfully");
                return ResponseEntity.status(204).contentType(MediaType.APPLICATION_JSON).body(objMessage.toJSONString());
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/users/gender")
    public ResponseEntity<String> getUsersByGender(@RequestParam(value = "gender") String gender){
        if(gender.equalsIgnoreCase("male")){
            ArrayList<User> list = new DatabaseMySQL().getMales();
            String json = new Util().getJSON(list);
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
        }
        else if(gender.equalsIgnoreCase("female")) {
            ArrayList<User> list = new DatabaseMySQL().getFemales();
            String json = new Util().getJSON(list);
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
        }
        return null;
    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers(@RequestParam(value = "type") String type){
        ArrayList<User> list = new DatabaseMySQL().getAllUsers();
        String json = new Util().getJSON(list);
        if(type.equals("xml")){
            String xml = XML.toString(json);
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_XML).body(xml);
        }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @GetMapping("/users/age")
    public ResponseEntity<String> getUsersByAge(@RequestParam(value = "from") int from, @RequestParam(value = "to") int to){
        if(from<0 || from>to){
            JSONObject objError = new JSONObject();
            objError.put("error", "Invalid entry");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(objError.toJSONString());
        }
        ArrayList<User> list = new DatabaseMySQL().getUsersByAge(from, to);
        String json = new Util().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<String> getUserByID(@PathVariable int id){
        ArrayList<User> list = new DatabaseMySQL().getUserByID(id);
        String json = new Util().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @GetMapping("/user/{pattern}")
    public ResponseEntity<String> getUser(@PathVariable String pattern){
        ArrayList<User> list = new DatabaseMySQL().getUser(pattern);
        String json = new Util().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @GetMapping("/")
    public ResponseEntity<String> getOverviewData(){
        ArrayList<User> list = new DatabaseMySQL().getAllUsers();
        String json = new Util().getOverview(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @PostMapping("/sport/user/add")
    public ResponseEntity<String> insertUserData(@RequestBody String data){
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(data);
            String name = (String) object.get("name");
            ArrayList<String> sports = (ArrayList<String>) object.get("sports");
            if(name==null || name.equals("") || sports==null || sports.isEmpty()){
                JSONObject objError = new JSONObject();
                objError.put("error", "Invalid entry");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(objError.toJSONString());
            }
            if(new SportDatabaseMongo().insertUserData(name, sports)){
                JSONObject objMessage = new JSONObject();
                objMessage.put("server" ,"User added successfully");
                return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(objMessage.toJSONString());
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/sport/user/delete")
    public ResponseEntity<String> deleteUser(@RequestParam(value = "name") String name){
        if(name==null || name.equals("")){
            JSONObject objError = new JSONObject();
            objError.put("error", "Invalid entry");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(objError.toJSONString());
        }
        if(new SportDatabaseMongo().deleteUser(name)){
            JSONObject objMessage = new JSONObject();
            objMessage.put("server", "User successfully deleted");
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(objMessage.toJSONString());
        }
        return null;
    }
}
