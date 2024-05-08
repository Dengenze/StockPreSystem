package Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.*;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class Account {
    //用户id
    @TableId(value = "userid", type = IdType.AUTO)
    private Integer userid;
    //用户账号
    private String account;

    //用户密码
    private String password;

    //用户邮箱
    private String email;

    public Account(String account, String password, String email) {
        this.account = account;
        this.password = password;
        this.email = email;
    }
}
