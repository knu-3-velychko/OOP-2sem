package services;

import dao.UserDAO;
import entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDao;

    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    public Long checkUser(String username, String password) {
        return userDao.findUserByLogin(username, password);
    }

    public boolean isAdmin(String username, String password) {
        return userDao.isAdmin(username, password);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
