package project3.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project3.pojo.Result;
import project3.pojo.User;
import project3.service.UserService;
import project3.service.impl.BroadcastService;
import project3.utils.JwtUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private BroadcastService broadcastService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.info("使用者登入:{}", user);
        User existingUser = userService.login(user);

        // 檢查使用者是否存在，並比對密碼
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            log.info("使用者{}嘗試登入", user.getUsername());
            // 設置狀態為 "閒置"
            existingUser.setStatus("閒置");

            // 更新 IP 和端口
            existingUser.setIp(user.getIp());
            existingUser.setPort(user.getPort());
            existingUser.setUpdateTime(LocalDateTime.now());

            log.info("更新使用者{}資料", user.getUsername());
            userService.update(existingUser); // 更新使用者資料

            Map<String, Object> claims = new HashMap<>();
            //claims.put("id", existingUser.getId());
            claims.put("name", existingUser.getName());
            claims.put("username", existingUser.getUsername());

            log.info("為使用者{}配置jwt", user.getUsername());
            String jwt = JwtUtils.generateJwt(claims);

            //broadcastService.broadcastUpdate("玩家" + existingUser.getName() + "(id:" + user.getUsername() + ")已上線" + BroadcastService.suffixGenerator("login"));

            return Result.success(jwt);
        }

        return Result.error("帳號或密碼錯誤!");
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user, HttpServletRequest request) {
        log.info("使用者嘗試註冊，註冊id為:{}", user);
        user.setIp(request.getRemoteAddr());
        user.setPort(Integer.toString(request.getRemotePort()));
        User success = userService.register(user);

        // 註冊檢查
        if (success != null) {
            return Result.success();
        } else {
            return Result.error("此帳號名稱已被使用!");
        }
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        log.info("使用者更新資料:{}", user);
        User newUser = userService.update(user);

        return Result.success(newUser);
    }

    @PostMapping("/logout")
    public Result logout(@RequestBody User user) {
        log.info("使用者登出:{}", user.getUsername());
        userService.logout(user.getUsername());
        //broadcastService.broadcastUpdate("玩家" + user.getUsername() + "已登出");
        return Result.success("登出成功");
    }

    private static final ConcurrentHashMap<String, Long> userLastHeartbeat = new ConcurrentHashMap<>();

    @PostMapping("/heartbeat")
    public Result heartbeat(@RequestBody User user) {
        log.info("收到 {} 的心跳訊號", user.getUsername());
        userLastHeartbeat.put(user.getUsername(), System.currentTimeMillis());
        return Result.success("咚");
    }

    // 這個方法可以用來檢查用戶是否超時下線
    @Scheduled(fixedRate = 20000)
    public void checkUserTimeout() {
        long currentTime = System.currentTimeMillis();
        userLastHeartbeat.forEach((Username, lastHeartbeat) -> {
            if (currentTime - lastHeartbeat > TimeUnit.SECONDS.toMillis(10)) { // 設定超時時間為10秒
                log.info("用戶 {} 已超時下線", Username);
                //broadcastService.broadcastUpdate("玩家" + Username + "已下線");
                // 進行必要的下線處理
                userLastHeartbeat.remove(Username);
                userService.logout(Username);
            }
        });
    }
}
