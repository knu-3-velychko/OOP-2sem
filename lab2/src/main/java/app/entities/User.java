package app.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class User {
    private String firstName;
    private String secondName;
    private String username;
    private String password;
    private List<Card> cards = new ArrayList<>();

    public User(String username, List<Card> cards) {
        this.username = username;
        this.cards = cards;
    }

    public User(String firstName, String secondName, String username, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.username = username;
        this.password = password;
    }
}
