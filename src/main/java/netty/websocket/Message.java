package netty.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 崇鹏豪
 * @Date: 2022/07/30/13:27
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private Long id;
    private Long targetId;
    private Integer type;
    private String msg;
}
