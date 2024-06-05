package cloud.user.Service.MP.impl;

import Dto.Alg;
import Dto.Str;
import cloud.user.Mapper.MP.UserMapperForAlg;
import cloud.user.Mapper.MP.UserMapperForStr;
import cloud.user.Service.MP.UserServiceForAlg;
import cloud.user.Service.MP.UserServiceForStr;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceForStrIml extends ServiceImpl<UserMapperForStr, Str> implements UserServiceForStr {
}
