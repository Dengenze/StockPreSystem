package cloud.user.Controller;

import CommonResponse.CommonResponse;
import Dto.UserRespVo;
import cloud.DengSequrity;
import cloud.user.Service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class InfoController {

    @Resource
    UserService userService;

    /**
     * 查询所有用户
     * @return 所有用户
     *        如果查询成功，则返回所有用户（200）
     *        如果权限不足，则返回权限不足（402）
     *        如果查询失败，则返回查询失败（400）
     */
    @GetMapping("user/getAllUser")
    public CommonResponse<List<UserRespVo>> getAllUser(HttpServletRequest request) {

        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<List<UserRespVo>>(402,"权限不足",null,null);
        }

        //获取所有用户
        List<UserRespVo> userRespVos = userService.selectAllUserAndAccount();

        //如果用户为空，返回查询失败
        if(userRespVos == null)
        {
            return new CommonResponse<List<UserRespVo>>(400,"查询失败",null,null);
        }

        //返回所有用户
        return new CommonResponse<List<UserRespVo>>(200,"查询成功",userRespVos,null);

    }

    /**
     * 根据用户id查询用户
     * @param userId 用户id
     * @return 用户
     *        如果查询成功，则返回用户（200）
     *        如果权限不足，则返回权限不足（402）
     *        如果查询失败，则返回查询失败（400）
     */
    @GetMapping("user/getUserById")
    public CommonResponse<UserRespVo> getUserById(HttpServletRequest request,
                                                  @RequestParam("userId")Integer userId) {

        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<UserRespVo>(402,"权限不足",null,null);
        }

        //获取用户
        UserRespVo userRespVo = userService.selectUserAndAccountById(userId);

        //如果用户为空，返回查询失败
        if(userRespVo == null)
        {
            return new CommonResponse<UserRespVo>(400,"查询失败",null,null);
        }

        //返回用户
        return new CommonResponse<UserRespVo>(200,"查询成功",userRespVo,null);

    }

    /**
     * 根据用户账号查询用户
     * @param userAccount 用户账号
     * @return 用户
     *        如果查询成功，则返回用户（200）
     *        如果权限不足，则返回权限不足（402）
     *        如果查询失败，则返回查询失败（400）
     */
    @GetMapping("user/getUserByAccount")
    public CommonResponse<UserRespVo> getUserByAccount(HttpServletRequest request,
                                                       @RequestParam("userAccount")String userAccount) {

        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<UserRespVo>(402,"权限不足",null,null);
        }

        //获取用户
        UserRespVo userRespVo = userService.selectUserAndAccountByAccount(userAccount);

        //如果用户为空，返回查询失败
        if(userRespVo == null)
        {
            return new CommonResponse<UserRespVo>(400,"查询失败",null,null);
        }

        //返回用户
        return new CommonResponse<UserRespVo>(200,"查询成功",userRespVo,null);

    }

    /**
     * 用户修改密码
     * @param userId 用户id
     *        oldPassword 旧密码
     *        newPassword 新密码
     * @return 相应的提示信息
     *       如果修改成功，则返回修改成功（200）
     *       如果权限不足，则返回权限不足（402）
     *       如果修改失败，则返回修改失败（400）
     */
    @PostMapping("user/updatePassword")
    public CommonResponse<String> updatePassword(HttpServletRequest request,
                                                 @RequestParam("userId")Integer userId,
                                                 @RequestParam("oldPassword")String oldPassword,
                                                 @RequestParam("newPassword")String newPassword) {

        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<String>(402,"权限不足",null,null);
        }

        //修改密码
        int result = userService.updatePassword(userId, oldPassword, newPassword);

        //如果修改成功，返回修改成功
        if(result == 1)
        {
            return new CommonResponse<String>(200,"修改成功",null,null);
        }

        //返回修改失败
        return new CommonResponse<String>(400,"修改失败",null,null);

    }

    /**
     * 用户修改信息
     * @param userId 用户id
     *        nickname 用户昵称
     *        sex 用户性别
     *        address 用户地址
     *        birthday 用户生日
     *        signal 用户个性签名
     * @return 相应的提示信息
     *      如果修改成功，则返回修改成功（200）
     *      如果权限不足，则返回权限不足（402）
     *      如果修改失败，则返回修改失败（400）
     */
    @PostMapping("user/updateUser")
    public CommonResponse<String> updateUser(HttpServletRequest request,
                                             @RequestParam("userId")Integer userId,
                                             @RequestParam("nickname")String nickname,
                                             @RequestParam("sex")String sex,
                                             @RequestParam("address")String address,
                                             @RequestParam("birthday")String birthday,
                                             @RequestParam("signal")String signal) {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"User"))
        {
            return new CommonResponse<String>(402,"权限不足",null,null);
        }

        //用户id为空
        if(userId == null)
        {
            return new CommonResponse<String>(400,"用户id为空！",null,null);
        }

        //生日格式不正确
        if(!birthday.matches("^\\d{4}-\\d{2}-\\d{2}$")){
            return new CommonResponse<String>(400,"生日格式不正确！",null,null);
        }

        //生日转为Date类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(birthday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //性别不正确
        if(!sex.equals("男") && !sex.equals("女")){
            return new CommonResponse<String>(400,"性别不正确！",null,null);
        }

        //修改用户信息
        boolean result = userService.updateUser(userId, nickname, sex, address, date, signal);

        //如果修改成功，返回修改成功
        if(result)
        {
            return new CommonResponse<String>(200,"修改成功",null,null);
        }

        //返回修改失败
        return new CommonResponse<String>(400,"修改失败",null,null);
    }
    @PostMapping("user/RootChangeRole")
    public CommonResponse<String> RootChangeRole(HttpServletRequest request,
                                                 @RequestParam("userId")Integer userId,
                                                 @RequestParam("role")String role)
    {
        //需要权限
        if(!DengSequrity.DengSequrity(request,"Root"))
        {
            return new CommonResponse<String>(402,"权限不足",null,null);
        }

        //去数据库里修改用户表
        if((userService.RootChangeRole(userId,role))!=0)
        {
            return new CommonResponse<String>(200,"修改成功",null,null);
        }
        else
        {
            //返回修改失败
            return new CommonResponse<String>(400,"修改失败",null,null);
        }
    }
}
