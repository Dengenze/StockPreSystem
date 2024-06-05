package cloud.PythonConnection.Service.Iml;

import Dto.Account;
import cloud.PythonConnection.Mapper.MapperForAccount;
import cloud.PythonConnection.Service.ServiceForAccount;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ServicesForAccountIml extends ServiceImpl<MapperForAccount, Account> implements ServiceForAccount {

}
