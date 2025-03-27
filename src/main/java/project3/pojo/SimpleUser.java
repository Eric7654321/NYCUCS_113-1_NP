package project3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUser {
    private String username; //用戶名稱
    private String name; //姓名
    private String status; //上線狀態
    private String ip; //ip
    private String port; //port
}
