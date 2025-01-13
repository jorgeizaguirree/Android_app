package upm.es.proyecto_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoadLogInDetails {

    private final Map<String, Integer> logInDetails = new HashMap<>();
    int CORRECT = 1, PASS_WRONG = 2, USER_NOT_FOUND = 3;

    public LoadLogInDetails(BufferedReader br) throws IOException {
        for (String line; (line = br.readLine()) != null;) {
            String[] parts = line.split(";");
            logInDetails.put(parts[0], Integer.parseInt(parts[1]));
        }
    }
    public LoadLogInDetails(){
        String test = "test";
        int hashCode = test.hashCode();
        logInDetails.put("user", hashCode);
    }


    public int findUser(String user, String password){
        int hashCode = password.hashCode();
        if (logInDetails.containsKey(user)){
            if (logInDetails.get(user) == hashCode){
                return CORRECT;
            } else {
                return PASS_WRONG;
            }
        } else {
            return USER_NOT_FOUND;
        }
    }
}
