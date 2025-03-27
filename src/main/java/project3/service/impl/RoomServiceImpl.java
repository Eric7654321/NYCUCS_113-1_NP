package project3.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project3.mapper.RoomMapper;
import project3.pojo.Room;
import project3.service.RoomService;

import java.util.ArrayList;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomMapper roomMapper;

    @Override
    public void register(Room room) {
        roomMapper.register(room);
    }

    @Override
    public ArrayList<Room> showAllRooms() {
        return roomMapper.showAllRoom();
    }

    @Override
    public void update(Room room) {
        roomMapper.update(room);
    }

    @Override
    public void kill(Room room) {
        roomMapper.kill(room);
    }

    @Override
    public Room showRoomById(String port) {
        return roomMapper.getRoomByPort(port);
    }
}
