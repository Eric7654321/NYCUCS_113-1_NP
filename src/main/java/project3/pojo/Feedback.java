package project3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private String gameMode;
    private String writer;
    private Integer rating;
    private String feedBack;
    private LocalDateTime createTime;
    private String gameAuthor;
}
