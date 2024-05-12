package cloud.user.Service.MP.impl;

import Dto.Collection;
import Dto.Stock;
import cloud.user.Mapper.MP.UserMapperForCollection;
import cloud.user.Service.MP.UserServiceForCollection;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceForCollectionImpl extends ServiceImpl<UserMapperForCollection, Collection> implements UserServiceForCollection {
}
