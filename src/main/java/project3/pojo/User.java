package project3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //private Integer id; //ID
    private String username; //用戶名稱
    private String password; //密碼
    private String name; //姓名
    private String ip; //ip
    private String port; //port
    private LocalDateTime createTime; //創建時間
    private LocalDateTime updateTime; //修改時間
    private String status; //上線狀態
}
