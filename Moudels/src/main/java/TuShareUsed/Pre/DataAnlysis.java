package TuShareUsed.Pre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DataAnlysis {
    //专门处理GetDailyParams_PreUseReturn内那个data的
    String[] fields;
    ArrayList<Object[]> items;//注意，这玩意不是json，他是一个object数组
    boolean has_more;
}
