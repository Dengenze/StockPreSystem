package cloud.user.Service.MP.impl;

import Dto.Collection;
import Dto.UserToStock;
import cloud.user.Mapper.MP.UserMapperForCollection;
import cloud.user.Mapper.MP.UserMapperForUserToStock;
import cloud.user.Service.MP.UserServiceForCollection;
import cloud.user.Service.MP.UserServiceForUserToStock;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceForUserToStockImpl extends ServiceImpl<UserMapperForUserToStock, UserToStock> implements UserServiceForUserToStock {
}
