package sk.kosickaacademic.simon;

import sk.kosickaacademic.simon.controller.SecretController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Login {
    private static Map<String, Date> blacklist = new HashMap<>();
    private static Map<String, Integer> attempts = new HashMap<>();

    public boolean loginUser(String login, String password){
        if(!blacklist.containsKey(login) || blacklist.get(login).getTime()+120000<new Date().getTime()){
            blacklist.remove(login);
            if(attempts.containsKey(login) && attempts.get(login)==3){
                blacklist.put(login, new Date());
                attempts.remove(login);
                return false;
            }
            if(password.equals(SecretController.password)){
                if(attempts.containsKey(login)) attempts.remove(login);
                return true;
            }
            else
                if(!attempts.containsKey(login)) attempts.put(login, 1);
                else if(attempts.get(login)==1) attempts.put(login, 2);
                else attempts.put(login, 3);

        }
        else return false;
        return true;
    }

}
