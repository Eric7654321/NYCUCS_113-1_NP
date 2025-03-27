package project3.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project3.mapper.UserMapper;
import project3.pojo.SimpleUser;
import project3.pojo.User;
import project3.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void delete(List<String> usernames) {
        userMapper.delete(usernames);
    }

    @Override
    public void save(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public User update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user); // 確保 userMapper 能正確更新
        return user;
    }

    @Override
    public User login(User user) {
        return userMapper.getByUsername(user);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return userMapper.getByUsername(user);
    }

    @Override
    public User register(User user) {
        User userCheck = userMapper.getByUsername(user);
        if(userCheck != null){
            return null;
        }
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setStatus("unavailable");
        userMapper.register(user);
        return user;
    }

    @Override
    public void logout(String username) {
        userMapper.logout(username);
    }

    @Override
    public ArrayList<User> getAllStatus() {
        ArrayList<User> users = userMapper.getAllStatus();

        return users;
    }


}
