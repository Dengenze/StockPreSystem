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
public class GetDailyParams_PreUseReturn {
    //预测算法专用，API接口返回给我的类
    String request_id;
    Integer code;
    String msg;
    DataAnlysis data;//需要进一步处理；
}
