package sk.kosickaacademic.simon.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.kosickaacademic.simon.Gender;
import sk.kosickaacademic.simon.database.Database;
import sk.kosickaacademic.simon.entity.User;

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
            new Database().insertNewUser(new User(age, fName, lName, g.getValue()));
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/user/males")
    public ResponseEntity<String> getMales(){
        JSONArray arr = new JSONArray();


        return null;
    }
}
