package project3.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import project3.pojo.Msg;
import project3.pojo.Result;
import project3.pojo.SimpleUser;
import project3.pojo.User;
import project3.service.UserService;
import project3.service.impl.BroadcastService;

import java.util.ArrayList;

@Slf4j
@RestController
public class StatusController {
    @Autowired
    private UserService userService;
    @Autowired
    private BroadcastService broadcastService;

    @GetMapping("/status")
    public Result status(){
        ArrayList<User> users = userService.getAllStatus();
        //broadcastService.broadcastUpdate("有個小王八蛋在偷看大家的資料(っ °Д °;)っ");
        return Result.success(users);
    }

    @PostMapping("/message")
    public Result sendMsg(@RequestBody Msg msg){
        String name = userService.getUserByUsername(msg.getUser()).getName();
        //broadcastService.broadcastUpdate("[聊天室] " + name + "(" + msg.getUser() + "): " + msg.getMessage());
        return Result.success(msg);
    }
}
