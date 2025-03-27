package project3.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import project3.pojo.Room;

import java.util.ArrayList;

@Mapper
public interface RoomMapper {
    //void update(Room room);

    @Insert("insert into np_project3.room(id, room_name, owner, ip, port, status, game_mode, people) " +
            "values (#{id}, #{roomName}, #{owner}, #{ip}, #{port}, #{status}, #{gameMode}, #{people})")
    void register(Room room);

    @Select("select * from np_project3.room where status != 'unavailable' and status != '遊戲中'")
    ArrayList<Room> showAllRoom();

    @Update("update np_project3.room set status = 'unavailable' where ip = #{ip} and port = #{port}")
    void kill(Room room);

    @Select("select * from np_project3.room where port = #{port}")
    Room getRoomByPort(String port);

    @Update("update np_project3.room set people = #{people}, status = #{status}, game_mode = #{gameMode} where ip = #{ip} and port = #{port}")
    void update(Room room);
}
