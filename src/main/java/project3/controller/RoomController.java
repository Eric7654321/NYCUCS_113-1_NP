package project3.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project3.pojo.Result;
import project3.pojo.Room;
import project3.service.RoomService;
import project3.service.UserService;
import project3.service.impl.BroadcastService;

import java.util.ArrayList;

@Slf4j
@RestController
public class RoomController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;
    @Autowired
    BroadcastService broadcastService;

    @PostMapping("/registerRoom")
    public Result register(@RequestBody Room room) {
        log.info("房間註冊:{}", room);
        roomService.register(room);
        //broadcastService.broadcastUpdate("房間" + room.getRoomName() + "(遊戲模式:" + room.getGameMode() + ")已被創建");
        return Result.success(room);
    }

    @GetMapping("/showRooms")
    public Result showRooms() {
        log.info("房間查詢操作");
        ArrayList<Room> rooms = roomService.showAllRooms();
        return Result.success(rooms);
    }

    @PostMapping("/showRoomsByPort")
    public Result showRoomByPort(@RequestBody String port) {
        log.info("房間查詢操作:{}", port);
        Room rooms = roomService.showRoomById(port);
        return Result.success(rooms);
    }

    @PostMapping("/updateRoom")
    public Result updateRoom(@RequestBody Room room) {
        log.info("房間更新:{}", room);
        roomService.update(room);
        return Result.success(room);
    }

    @PostMapping("/killRoom")
    public Result killRoom(@RequestBody Room room) {
        log.info("房間刪除:{}", room);
        roomService.kill(room);
        Room newRoom = roomService.showRoomById(room.getPort());
        //broadcastService.broadcastUpdate("房間" + newRoom.getRoomName() + "(遊戲模式:" + newRoom.getGameMode() + ")已解散");
        return Result.success(room);
    }
}
