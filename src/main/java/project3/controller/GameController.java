package project3.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;
import project3.pojo.Feedback;
import project3.pojo.Game;
import project3.pojo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project3.service.GameService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import project3.service.impl.BroadcastService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/files")
public class GameController {

    @Autowired
    GameService gameService;
    @Autowired
    BroadcastService broadcastService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public Result uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestPart("game") Game game) {
        if (file.isEmpty()) {
            return Result.error("上傳錯誤，文件為空");
        }
        log.info("正在儲存遊戲: {}, 檔案名稱: {}...", game.getName(), file.getOriginalFilename());

        try {
            // 修正 uploadDir 路径为绝对路径，保存到系统临时目录下的 "games" 文件夹
            uploadDir = System.getProperty("user.home") + File.separator + "NP" + File.separator+ "Project3" + File.separator + "games" + File.separator;
            //String filePath = "net" + File.separator + "cs" + File.separator + "112" + File.separator + "112550084" + File.separator + "NP" + File.separator+ "Project3" + File.separator + "upload" + File.separator + File.separator + fileName;

            log.info("檔案將儲存至路徑: {}", uploadDir);

            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                if (uploadDirectory.mkdirs()) {
                    log.info("成功創建目錄: {}", uploadDirectory.getAbsolutePath());
                } else {
                    log.error("目錄創建失敗: {}", uploadDirectory.getAbsolutePath());
                    return Result.error("文件目錄創建失敗！");
                }
            }

            String filePath = uploadDir + game.getName() + ".jar";
            File dest = new File(filePath);
            file.transferTo(dest);

            game.setFileName(game.getName() + ".jar");  // 设置新文件名
            game.setFilePath(filePath); // 保存完整路径
            game.setFileSize(file.getSize());
            game.setFileType(file.getContentType());

            gameService.save(game);
            log.info("檔案上傳成功: {}", file.getOriginalFilename());
            //String suffix = BroadcastService.suffixGenerator("gameUpdate");
            //broadcastService.broadcastUpdate(game.getName() + "就在剛剛已經更新了!" + suffix);
            return Result.success();
        } catch (IOException e) {
            log.error("檔案上傳失敗: {}", e.getMessage(), e);
            return Result.error("檔案上傳失敗！");
        }
    }

    @GetMapping("/download/{name}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String name) {
        log.info("嘗試尋找檔案{}", name);
        try {
            // 查詢遊戲檔案位置
            String filePath = gameService.find(name).getFilePath();
            log.info("正在嘗試傳送檔案{}，檔案位置: {}", name, filePath);
            if (filePath == null) {
                log.error("無法在伺服器端遊戲列表找到檔案: {}", name);
                return ResponseEntity.notFound().build();
            }

            File file = new File(filePath);
            if (!file.exists()) {
                log.error("檔案在指定路徑中不存在: {}", filePath);
                return ResponseEntity.notFound().build();
            }

            // 將檔案轉換為 Resource
            Resource resource = new UrlResource(file.toURI());
            if (!resource.exists() || !resource.isReadable()) {
                log.error("轉換檔案時發生錯誤: {}", filePath);
                return ResponseEntity.notFound().build();
            }

            log.info("成功找到檔案: {}", name);

            // 設置下載響應的頭部信息
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            log.error("伺服器下載處理時發生未知錯誤", e);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/list")
    public Result listFiles() {
        List<Game> game = gameService.list();
        log.info("請求伺服器遊戲列表");
        return Result.success(game);
    }

    @PostMapping("/feedback")
    public Result feedBack(@RequestBody Feedback feedback) {
        gameService.addFeedback(feedback);
        return Result.success();
    }

    @GetMapping("/showAllFeedback")
    public Result showAllFeedback() {
        log.info("查詢評論操作");
        List<Feedback> feedbacks = gameService.getAllFeedback();
        return Result.success(feedbacks);
    }
}
