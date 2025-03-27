package project3.pojo;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    private Integer id;
    private String fileName;
    private String filePath;
    private String fileType;
    private long fileSize;
    private String name;
    private String creator;
    private String version;
    private String description;
    private Integer rating;
    private Integer played;
    private LocalDateTime createTime; //創建時間
    private LocalDateTime updateTime; //修改時間
}
