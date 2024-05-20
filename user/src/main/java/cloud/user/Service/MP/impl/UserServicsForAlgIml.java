package cloud.user.Service.MP.impl;

import Dto.Alg;
import cloud.user.Mapper.MP.UserMapperForAlg;
import cloud.user.Service.MP.UserServiceForAlg;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServicsForAlgIml extends ServiceImpl<UserMapperForAlg, Alg> implements UserServiceForAlg {
}
