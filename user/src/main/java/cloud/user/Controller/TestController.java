package cloud.user.Controller;

import CommonResponse.CommonResponse;
import cloud.DengSequrity;
import cloud.JwtTokenUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @PostMapping("/user/log/FrountTest")
    public CommonResponse<String> FrountTest(HttpServletRequest request,@RequestParam("requiredRole") String requiredRole)
    {
        return new CommonResponse<String>(200,"测试成功","随便丢点东西占位","随便丢点东西占位");
    }

    @PostMapping("/user/log/testlog")
    public String testlog(@RequestParam("username") String username,@RequestParam("authority") String authority)
    {
        //生成Token
        String token = JwtTokenUtil.createToken(username, String.valueOf(authority));
        return token;
    }

    @PostMapping("/user/tokentest")
    public CommonResponse<String> tokentest(HttpServletRequest request,@RequestParam("requiredRole") String requiredRole)
    {
        //下面那个if语句是权限判断部分，每个需要验证权限的地方都要加，记得根据Controller返回体的范型修改一下
        if(!DengSequrity.DengSequrity(request,requiredRole))
        {
            return new CommonResponse<String>(402,"权限不足或异常","随便丢点东西占位","随便丢点东西占位");
        }
        return new CommonResponse<String>(200,"测试成功","随便丢点东西占位","随便丢点东西占位");
    }


}
