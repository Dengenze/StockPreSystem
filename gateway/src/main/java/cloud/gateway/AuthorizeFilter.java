package cloud.gateway;

import cloud.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @Order(-1)
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        //无条件放行登录类界面
        if(request.getURI().getPath().contains("/user/log"))
        {
            return chain.filter(exchange);
        }

        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("authorization");//获取token
        // 验证令牌
        Claims claims = JwtTokenUtil.checkJWT(token);
        if (claims == null) {
            // 如果没有生成Token
            // 设置状态码
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 拦截请求
            return exchange.getResponse().setComplete();
        }
        // 如果token正常，放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
