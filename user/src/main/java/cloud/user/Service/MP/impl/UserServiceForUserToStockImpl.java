package cloud.user.Service.MP.impl;

import Dto.usertostock;
import cloud.user.Mapper.MP.UserMapperForUserToStock;
import cloud.user.Service.MP.UserServiceForUserToStock;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceForUserToStockImpl extends ServiceImpl<UserMapperForUserToStock, usertostock> implements UserServiceForUserToStock {
}
