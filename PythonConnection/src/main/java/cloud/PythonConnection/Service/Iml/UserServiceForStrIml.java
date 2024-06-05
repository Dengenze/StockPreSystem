package cloud.PythonConnection.Service.Iml;

import Dto.Str;
import cloud.PythonConnection.Mapper.UserMapperForStr;
import cloud.PythonConnection.Service.UserServiceForStr;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceForStrIml extends ServiceImpl<UserMapperForStr, Str> implements UserServiceForStr {
}
