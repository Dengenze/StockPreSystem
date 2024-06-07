package cloud.user.Controller;

import CommonResponse.CommonResponse;
import Dto.Account;
import Dto.User;
import Dto.UserRespVo;
import cloud.user.Service.UserService;
import Utils.JwtTokenUtil;
import Utils.SendEmailUtils;
import Utils.ValidateCodeUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
public class LogController {
    @Resource
    UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户注册
     * @param userAccount 用户账号
     *        password 用户密码
     *        email 用户邮箱
     *        captcha 验证码
     * @return 相应的提示信息
     *         如果账号相同，则返回账号已存在（400）
     *         如果邮箱相同，则返回邮箱已存在（400）
     *         如果不满足以上条件，则返回账号注册成功（200）
     */
    @PostMapping("/user/log/register")
    public CommonResponse<String> register(@RequestParam("userAccount") String userAccount,
                                           @RequestParam("password")String password,
                                           @RequestParam("email")String email,
                                           @RequestParam("captcha")String captcha)
    {
        //不需要权限
        //账号密码已存在的情况
        if(userService.selectUserByAccount(userAccount)!=null)
        {
            return new CommonResponse<String>(400,"账号已存在",null,null);
        }
        if(userService.selectAccountByEmail(email)!=null)
        {
            return new CommonResponse<String>(400,"邮箱已存在",null,null);
        }

        //获取Redis中的验证码
        String code = stringRedisTemplate.opsForValue().get(email);

        //如果验证码为空，注册失败
        if(code==null)
        {
            return new CommonResponse<String>(400,"验证码已过期",null,null);
        }

        //如果验证码不匹配，注册失败
        if(!code.equals(captcha))
        {
            return new CommonResponse<String>(400,"验证码错误",null,null);
        }

        //删除Redis中的验证码
        //stringRedisTemplate.delete(email);

        //写入新用户
        User user = new User();
        Account account = new Account();
        account.setAccount(userAccount).setEmail(email).setPassword(password);
        if(userService.insertUserAndAccount(user, account))//如果插入成功
        {
            return new CommonResponse<String>(200,"注册成功",null,null);
        }
        else
        {
            return new CommonResponse<String>(400,"注册失败",null,null);
        }
    }

    /**
     * 用户登录
     * @param userAccount 用户账号
     *        password 用户密码
     * @return 相应的提示信息
     *         如果账号不存在，则返回账号不存在（400）
     *         如果密码错误，则返回密码错误（400）
     *         如果不满足以上条件，则返回登录成功（200）
     */
    @PostMapping("/user/log/login")
    public CommonResponse<UserRespVo> register(@RequestParam("userAccount") String userAccount,
                                           @RequestParam("password")String password)
    {
        //不需要权限
        //账号不存在的情况
        if(userService.selectUserByAccount(userAccount)==null)
        {
            return new CommonResponse<UserRespVo>(400,"账号不存在",null,null);
        }

        //密码错误的情况
        if(!userService.selectAccountByAccount(userAccount).getPassword().equals(password))
        {
            return new CommonResponse<UserRespVo>(400,"密码错误",null,null);
        }

        //用户存在，将用户信息封装到UserRespVo对象中
        UserRespVo userRespVo = userService.selectUserAndAccountByAccount(userAccount);

        //生成token
        String token = JwtTokenUtil.createToken(userAccount, userService.selectRoleByUserId(userRespVo.getUserid()));

        return new CommonResponse<UserRespVo>(200,"登录成功", userRespVo, token);
    }

    /**
     * 发送邮箱验证码
     * @param email 用户邮箱
     * @return 相应的提示信息
     *        如果发送成功，则返回验证码发送成功（200）
     *        如果发送失败，则返回验证码发送失败（400）
     */
    @GetMapping("/user/log/sendCaptcha")
    public CommonResponse<String> sendCaptcha(@RequestParam("email") String email)
    {
        //不需要权限

        //如果邮箱为空，发送失败
        if(email==null)
        {
            return new CommonResponse<String>(400,"邮箱为空",null,null);
        }

        //随机生成验证码
        String code = String.valueOf(ValidateCodeUtils.generateValidateCode(8));

        //发送验证码
        String result = SendEmailUtils.sendAuthCodeEmail(email, code);

        if (result.equals("error")) return new CommonResponse<String>(400,"验证码发送失败",null,null);

        //将验证码存入Redis中(有效期5分钟)
        stringRedisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);

        return new CommonResponse<String>(200,"验证码发送成功",null,null);
    }

    /**
     * 邮箱登录
     * @param email 用户邮箱
     *        captcha 验证码
     * @return 相应的提示信息
     *       如果验证码错误，则返回验证码错误（400）
     *       如果验证码正确，则返回登录成功（200）
     */
    @PostMapping("/user/log/loginByCaptcha")
    public CommonResponse<UserRespVo> loginByCaptcha(@RequestParam("email") String email,
                                                 @RequestParam("captcha") String captcha)
    {
        //不需要权限

        //如果邮箱为空，登录失败
        if(email==null)
        {
            return new CommonResponse<UserRespVo>(400,"邮箱为空",null,null);
        }

        //获取Redis中的验证码
        String code = stringRedisTemplate.opsForValue().get(email);

        //如果验证码为空，登录失败
        if(code==null)
        {
            return new CommonResponse<UserRespVo>(400,"验证码已过期",null,null);
        }

        //如果验证码不匹配，登录失败
        if(!code.equals(captcha))
        {
            return new CommonResponse<UserRespVo>(400,"验证码错误",null,null);
        }

        //用户存在，将用户信息封装到UserRespVo对象中
        UserRespVo userRespVo = userService.selectUserAndAccountByEmail(email);

        //生成token
        String token = JwtTokenUtil.createToken(userRespVo.getAccount(), userService.selectRoleByUserId(userRespVo.getUserid()));

        return new CommonResponse<UserRespVo>(200,"登录成功",userRespVo,token);
    }

    @GetMapping("/user/log/getUserRole")
    public CommonResponse<String> sendCaptcha(HttpServletRequest request)
    {
        // 从请求头获取token
        String token = request.getHeader("Authorization");

        // 通过JwtTokenUtil工具类获取当前用户的权限
        String userRole = cloud.JwtTokenUtil.getUserRole(token);

        return new CommonResponse<String>(200,"",userRole,token);
    }
}
