package tim2.CulturalHeritage.helper;

import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ApiErrors {

    private ArrayList<String> messages = new ArrayList<>();

    public ApiErrors(List<ObjectError> allErrors) {
        for (ObjectError e: allErrors) {
            messages.add(e.getDefaultMessage());
        }
    }

    public ApiErrors(String message) {
        messages.add(message);
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }
}
