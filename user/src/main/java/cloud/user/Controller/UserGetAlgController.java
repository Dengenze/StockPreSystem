package cloud.user.Controller;

import CommonResponse.CommonResponse;
import Dto.Alg;
import Dto.Str;
import cloud.JwtTokenUtil;
import cloud.user.Service.MP.UserServiceForAlg;
import cloud.user.Service.MP.UserServiceForStr;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserGetAlgController {
    @Resource
    UserServiceForAlg userServiceForAlg;

    @Resource
    UserServiceForStr userServiceForStr;

    @PostMapping("user/getAlg")
    public CommonResponse<List<Alg>> usergetAlg(HttpServletRequest request,
                                          @RequestParam("algname")String algname)
    {
        // 从请求头获取token
        String token = request.getHeader("Authorization");

        // 通过JwtTokenUtil工具类获取当前用户的权限
        String userRole = JwtTokenUtil.getUserRole(token);

        if ("Root".equals(userRole) || "Vip".equals(userRole) || "AlgE".equals(userRole) || "StrE".equals(userRole)) {
            userRole = "Vip";
        }

        if(algname==null)
        {
            List<Alg> yes = userServiceForAlg.lambdaQuery().eq(Alg::getIfpass, "YES").list();
            return new CommonResponse<>(200,"",yes,null);
        }

        List<Alg> yes = userServiceForAlg.lambdaQuery().eq(Alg::getIfpass, "YES").like(Alg::getAlgname,algname).list();
        return new CommonResponse<>(200,"",yes,null);
    }

    @PostMapping("/user/RootGetAlg")
    public CommonResponse<List<Alg>> RootGetAlg(HttpServletRequest request,
                                                @RequestParam("algname")String algname)
    {
        if(algname==null)
        {
            List<Alg> yes = userServiceForAlg.lambdaQuery().list();
            return new CommonResponse<>(200,"",yes,null);
        }

        List<Alg> yes = userServiceForAlg.lambdaQuery().like(Alg::getAlgname,algname).list();
        return new CommonResponse<>(200,"",yes,null);
    }
    @PostMapping("user/getStr")
    public CommonResponse<List<Str>> usergetStr(HttpServletRequest request,
                                                @RequestParam("strname")String strname)
    {
        // 从请求头获取token
        String token = request.getHeader("Authorization");

        // 通过JwtTokenUtil工具类获取当前用户的权限
        String userRole = JwtTokenUtil.getUserRole(token);

        if ("Root".equals(userRole) || "Vip".equals(userRole) || "AlgE".equals(userRole) || "StrE".equals(userRole)) {
            userRole = "Vip";
        }

        if(strname ==null)
        {
            List<Str> yes = userServiceForStr.lambdaQuery().eq(Str::getIfpass, "YES").list();
            return new CommonResponse<>(200,"",yes,null);
        }

        List<Str> yes = userServiceForStr.lambdaQuery().eq(Str::getIfpass, "YES").like(Str::getStrname, strname).list();
        return new CommonResponse<>(200,"",yes,null);
    }

    @PostMapping("/user/RootGetStr")
    public CommonResponse<List<Str>> RootGetStr(HttpServletRequest request,
                                                @RequestParam("strname")String strname)
    {
        if(strname ==null)
        {
            List<Str> yes = userServiceForStr.lambdaQuery().list();
            return new CommonResponse<>(200,"",yes,null);
        }

        List<Str> yes = userServiceForStr.lambdaQuery().like(Str::getStrname, strname).list();
        return new CommonResponse<>(200,"",yes,null);
    }

    @PostMapping("user/RootCheckAlg")
    public CommonResponse<String> RootCheckAlg(HttpServletRequest request,
                                                  @RequestParam("algid")Integer algid,
                                                @RequestParam("ifpass")String ifpass,
                                                @RequestParam("algname")String algname,
                                                  @RequestParam("alggrade")String alggrade)
    {
        if(!userServiceForAlg.lambdaQuery().ne(Alg::getAlgid,algid).eq(Alg::getAlgname,algname).list().isEmpty())
        {
            return new CommonResponse<String>(400,"策略名称重复",null,null);
        }

        boolean update = userServiceForAlg.lambdaUpdate().eq(Alg::getAlgid, algid).set(Alg::getIfpass, ifpass).set(Alg::getAlgname, algname).set(Alg::getAlggrade, alggrade).update();

        if(update)
        {
            return new CommonResponse<>(200,"","",null);
        }
        else
        {
            return new CommonResponse<>(400,"修改失败","",null);
        }

    }
    @PostMapping("user/RootCheckStr")
    public CommonResponse<String> RootCheckStr(HttpServletRequest request,
                                               @RequestParam("strid")Integer strid,
                                               @RequestParam("ifpass")String ifpass,
                                               @RequestParam("strname")String strname,
                                               @RequestParam("strgrade")String strgrade)
    {
        if(!userServiceForStr.lambdaQuery().ne(Str::getStrid,strid).eq(Str::getStrname,strname).list().isEmpty())
        {
            return new CommonResponse<String>(400,"策略名称重复",null,null);
        }

        boolean update = userServiceForStr.lambdaUpdate().eq(Str::getStrid, strid).set(Str::getIfpass, ifpass).set(Str::getStrname, strname).set(Str::getStrgrade, strgrade).update();

        if(update)
        {
            return new CommonResponse<>(200,"","",null);
        }
        else
        {
            return new CommonResponse<>(400,"修改失败","",null);
        }

    }

    @PostMapping("user/RootDeleteStr")
    public CommonResponse<String> RootDeleteStr(HttpServletRequest request,
                                               @RequestParam("strid")Integer strid
                                               )
    {
        boolean b = userServiceForStr.lambdaUpdate().eq(Str::getStrid, strid).remove();

        if(b)
        {
            return new CommonResponse<>(200,"删除成功","",null);
        }
        else
        {
            return new CommonResponse<>(400,"删除失败","",null);
        }
    }


    @PostMapping("user/RootDeleteAlg")
    public CommonResponse<String> RootDeleteAlg(HttpServletRequest request,
                                                @RequestParam("algid")Integer algid
    )
    {
        boolean b = userServiceForAlg.lambdaUpdate().eq(Alg::getAlgid, algid).remove();

        if(b)
        {
            return new CommonResponse<>(200,"删除成功","",null);
        }
        else
        {
            return new CommonResponse<>(400,"删除失败","",null);
        }
    }

}
