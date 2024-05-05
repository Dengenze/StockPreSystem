package cloud.user.Controller;

import CommonResponse.CommonResponse;
import Dto.Account;
import Dto.User;
import cloud.user.Mapper.UserMapper;
import cloud.user.Service.LogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LogController {
    @Resource
    LogService logService;

    @PostMapping("/user/log/register")
    public CommonResponse<String> register(@RequestParam("userAccount") String userAccount,
                                           @RequestParam("password")String password,
                                           @RequestParam("email")String email,
                                           @RequestParam("captcha")String captcha)
    {
        //不需要权限
        //账号密码已存在的情况
        if(logService.selectUserByAccount(userAccount)!=null)
        {
            return new CommonResponse<String>(400,"账号已存在","占位","占位");
        }
        if(logService.selectUserByEmail(email)!=null)
        {
            return new CommonResponse<String>(400,"邮箱已存在","占位","占位");
        }

        //TODO:Redis处理验证码的部分，我不会搞redis


        //写入新用户
        User user = new User();
        Account account = new Account();
        account.setAccount(userAccount).setEmail(email).setPassword(password);
        if(logService.addAccountAndUser(account,user))//如果插入成功
        {
            return new CommonResponse<>(200,"注册成功","占位","占位");
        }
        else
        {
            return new CommonResponse<>(400,"注册失败","占位","占位");
        }
    }
}
