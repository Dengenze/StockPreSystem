package Dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@AllArgsConstructor
@ToString
@Accessors(chain = true)
public class User {
    //用户id
    @TableId(value = "userid")
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

    public User() {
        //生成随机昵称：用户+时间戳
        this.nickname = "用户" + System.currentTimeMillis();

        this.ifbanned = 0;
    }
}
