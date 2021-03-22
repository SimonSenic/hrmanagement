package sk.kosickaacademic.simon.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaacademic.simon.Log;
import sk.kosickaacademic.simon.Util;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SecretController {
    public final String password = "Kosice2021!";
    Map<String, String> map = new HashMap<>();
    Log log = new Log();

    @GetMapping("/secret")
    public String secret(@RequestHeader("token") String data){
        String token = data.substring(7);
        for(Map.Entry<String, String> temp : map.entrySet()){
            if(temp.getValue().equals(token)) return "secret";
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody String data){
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(data);
            String login = (String) object.get("login");
            String password = (String) object.get("password");
            if(login==null || login.equals("") || password==null || password.equals("")){
                log.printError("Empty login or password");
                JSONObject json = new JSONObject();
                json.put("error", "Empty login or password");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(json.toJSONString());
            }
            if(password.equals(this.password)){
                String token = new Util().getToken();
                map.put(login, token);
                log.printMessage("User logged in successfully");
                JSONObject json = new JSONObject();
                json.put("login", login);
                json.put("token", "Bearer " +token);
                return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json.toJSONString());
            }else{
                log.printError("Invalid password");
                JSONObject json = new JSONObject();
                json.put("error", "Invalid password");
                return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(json.toJSONString());
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(@RequestHeader("token") String data){
        String token = data.substring(7);
        for(Map.Entry<String, String> temp : map.entrySet())
            if(temp.getValue().equals(token)){
                map.remove(temp.getKey());
                log.printMessage("Successfully logged out");
                JSONObject json = new JSONObject();
                json.put("server", "Successfully logged out");
                return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json.toJSONString());
            }
        log.printError("Invalid token");
        JSONObject json = new JSONObject();
        json.put("error", "Invalid token");
        return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(json.toJSONString());
    }
}
