package cloud.user.Mapper.MP;

import Dto.Account;
import Dto.Alg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapperForAccount extends BaseMapper<Account> {
}
