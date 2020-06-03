package app.services;

import app.dao.UserDao;
import app.entities.Card;
import app.entities.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    public int checkUser(String username, String password) {
        return userDao.checkUser(username, password);
    }

    public boolean isAdmin(String username, String password) {
        return userDao.isAdmin(username, password);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
