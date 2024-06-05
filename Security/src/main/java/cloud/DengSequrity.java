package cloud;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DengSequrity {
    public static Boolean DengSequrity(HttpServletRequest request, String requiredRole)
    {
        // 使用Map来定义权限和它们的级别
        Map<String, Integer> authorityMap = new HashMap<>();
        authorityMap.put("User", 1);
        authorityMap.put("Vip", 2);
        authorityMap.put("StrE", 3);
        authorityMap.put("AlgE", 3);
        authorityMap.put("Root", 4);

        // 从请求头获取token
        String token = request.getHeader("Authorization");

        // 通过JwtTokenUtil工具类获取当前用户的权限
        String userRole = JwtTokenUtil.getUserRole(token);

        // 获取userRole和requiredRole的权限级别
        Integer userRoleLevel = authorityMap.get(userRole);
        Integer requiredRoleLevel = authorityMap.get(requiredRole);

        // 检查权限级别是否有效（确保角色存在于Map中）
        if (userRoleLevel != null && requiredRoleLevel != null) {
            // 检查用户权限级别是否足够
            if (userRoleLevel >= requiredRoleLevel) {
                if (userRoleLevel==3) // 如果请求的是StrE或AlgE专属内容，确保角色匹配
                {
                    if((userRole.equals("StrE"))&&(requiredRole.equals("AlgE")))
                    {
                        return false; // 互斥情况1
                    }
                    else if((userRole.equals("AlgE"))&&(requiredRole.equals("StrE")))
                    {
                        return false; // 互斥情况2
                    }
                    else {
                        return true;//不互斥
                    }
                }
                else //不涉及平行权限
                {
                    return true;
                }
            }
        }
        return false; // 用户权限级别不足或角色不存在于权限映射中
    }
}
