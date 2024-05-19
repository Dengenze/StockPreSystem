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
public class PostToPython {
    //给Python发信息的类
    String url;
    ArrayList<Object[]> data;
}
