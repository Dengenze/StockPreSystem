package Dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 响应前端请求User的返回对象
 */

@Data
public class UserRespVo {

    //用户id
    private int userid;

    //用户账号
    private String account;

    //用户封禁状态
    private int ifbanned;

    //用户昵称
    private String nickname;

    //用户邮箱
    private String email;

    //用户性别
    private String sex;

    //用户地址
    private String address;

    //用户简介
    private String signal;

    //用户生日
    private Date birthday;


    //将User对象转换为UserRespVo对象
    public static List<UserRespVo> convertFor(List<User> users, List<Account> accounts) {
        List<UserRespVo> userRespVos = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            UserRespVo userRespVo = new UserRespVo();
            BeanUtils.copyProperties(users.get(i), userRespVo);
            BeanUtils.copyProperties(accounts.get(i), userRespVo);
            userRespVos.add(userRespVo);
        }
        return userRespVos;
    }
}
