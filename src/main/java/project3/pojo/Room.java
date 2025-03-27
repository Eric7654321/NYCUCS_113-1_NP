package project3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private Integer id; //房間ID
    private String roomName; //房間名稱
    private String owner;
    private String ip; //ip
    private String port; //port
    private String status; //房間狀態
    private String gameMode; //房間模式
    private Integer people; //房間人數
}
