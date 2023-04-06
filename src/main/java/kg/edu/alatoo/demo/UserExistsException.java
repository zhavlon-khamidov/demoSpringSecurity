package kg.edu.alatoo.demo;

public class UserExistsException extends Exception{
    public UserExistsException(String username) {
        super("User with username " + username + " already exists");
    }
}
