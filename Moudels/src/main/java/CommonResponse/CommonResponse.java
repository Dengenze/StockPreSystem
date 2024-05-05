package CommonResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonResponse<T> {
    private int code;
    private String msg;
    private T response;
    private String token;//token,只在登录里用，其他的地方随便丢点东西占位就好

    /*  T :范型用法如下：
    * 假设我的返回值是一个String，那么返回前我这样new EntryReturn：
    * EntryReturn<String> stringEntryReturn = new EntryReturn<String>();
    * 然后就可以往response里放 String 了
    *
    * 如果我的返回值是个 List<String> ，那么返回前我这样new EntryReturn：
    * EntryReturn<List<String>> stringEntryReturn = new EntryReturn<List<String>>();
    * 然后就可以往response里放 List<String> 了
    * */
}
