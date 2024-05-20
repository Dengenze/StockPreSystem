package TuShareUsed.GrossClassForAPI;

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
public class TuShareJson<T> {
//和TuShare接口交互时专用的类，转成json后直接post请求过去
    String api_name;
    String token="ecf89e8050ef23b2088a5241470de5eb3b5f05c371f578736b8eb587";
    T params;//不同接口传入不同的params
    String fields;
}
