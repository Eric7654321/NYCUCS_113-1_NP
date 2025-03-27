package project3.service;

import project3.pojo.Room;

import java.util.ArrayList;

public interface RoomService {
    void register(Room room);

    ArrayList<Room> showAllRooms();

    void update(Room room);

    void kill(Room room);

    Room showRoomById(String port);
}
