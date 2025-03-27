package project3.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import project3.pojo.SimpleUser;
import project3.pojo.User;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {

    void delete(List<String> usernames);

    void update(User user);

    @Insert("insert into np_project3.user(username, password, name, ip, port, create_time, update_time, status)" +
            "values (#{username}, #{password}, #{name}, #{ip}, #{port}, #{createTime}, #{updateTime}, #{status})")
    void insert(User user);

    @Select("select * from np_project3.user where username = #{username}")
    User getByUsername(User user);

    @Insert("insert into np_project3.user(username, password, name, ip, port, create_time, update_time, status)" +
            "values (#{username}, #{password}, #{name}, #{ip}, #{port}, #{createTime}, #{updateTime}, #{status})")
    void register(User user);

    @Select("select * from np_project3.user where status != 'unavailable'")
    ArrayList<User> getAllStatus();

    @Update("update np_project3.user set status = 'unavailable' where username = #{username}")
    void logout(String username);
}
