package cloud.user.Service;

import Dto.Account;
import Dto.User;
import cloud.user.Mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogService {
    @Resource
    UserMapper userMapper;

    public Account selectUserByAccount(String userAccount)
    {
        return userMapper.selectUserByAccount(userAccount);
    }
    public Account selectUserByEmail(String email)
    {
        return userMapper.selectUserByEmail(email);
    }

    public boolean addAccountAndUser(Account account, User user)
    {
        int IfAccountInsertOk =userMapper.addAccount(account);//插入account，数据库自动补全id
        int userid=userMapper.selectUserByAccount(account.getAccount()).getUserid();//获取刚插入的用户的id
        user.setUserid(userid);//补全User
        int IfUserInsertOk=userMapper.addUser(user);//插入用户
        return IfAccountInsertOk + IfUserInsertOk == 2;//只有两个都成功才返回成功
    }
}
