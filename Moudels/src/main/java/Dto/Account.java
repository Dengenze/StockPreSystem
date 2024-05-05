package Dto;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class Account {
    private Integer userid;
    //用户账号
    private String account;

    //用户密码
    private String password;

    //用户邮箱
    private String email;
}
