package cloud.user.Service.MP.impl;

import Dto.Account;
import Dto.Alg;
import cloud.user.Mapper.MP.UserMapperForAccount;
import cloud.user.Service.MP.UserServiceForAccount;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServicesForAccountIml extends ServiceImpl<UserMapperForAccount, Account> implements UserServiceForAccount {

}
