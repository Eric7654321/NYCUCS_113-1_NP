package project3.service;

import project3.pojo.SimpleUser;
import project3.pojo.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    void delete(List<String> usernames);

    void save(User user);


    User update(User user);

    User login(User user);

    User getUserByUsername(String username);

    User register(User user);

    void logout(String username);

    ArrayList<User> getAllStatus();
}
