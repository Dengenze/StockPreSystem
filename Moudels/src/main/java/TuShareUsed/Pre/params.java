package TuShareUsed.Pre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class params {
    //预测算法专用，获取预测算法需要的那几个参数时专用的params字段
    String ts_code;
    String start_date;
    String end_date;
}
