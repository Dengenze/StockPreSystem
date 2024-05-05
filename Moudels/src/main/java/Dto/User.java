package Dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class User {
    //用户id
    private Integer userid;

    //用户昵称
    private String nickname;

    //用户封禁状态
    private int ifbanned;

    //用户性别
    private String sex;

    //用户地址
    private String address;

    //用户生日
    private Date birthday;

    //用户简介
    private String signal;
}
