package br.com.sensedia.users.adapters;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {
        super("User " + id + " not found.");
    }

}