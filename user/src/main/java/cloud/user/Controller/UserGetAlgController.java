package cloud.user.Controller;

import CommonResponse.CommonResponse;
import Dto.Alg;
import cloud.user.Service.MP.UserServiceForAlg;
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
    @PostMapping("user/getAlg")
    public CommonResponse<List<Alg>> usergetAlg(HttpServletRequest request,
                                          @RequestParam("algname")String algname)
    {
        if(algname==null)
        {
            List<Alg> yes = userServiceForAlg.lambdaQuery().eq(Alg::getIfpass, "YES").list();
            return new CommonResponse<>(200,"",yes,null);
        }

        List<Alg> yes = userServiceForAlg.lambdaQuery().eq(Alg::getIfpass, "YES").like(Alg::getAlgname,algname).list();
        return new CommonResponse<>(200,"",yes,null);
    }

    @PostMapping("/user/RootGetAlg")
    public CommonResponse<List<Alg>> RootGetAlgg(HttpServletRequest request,
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
}
