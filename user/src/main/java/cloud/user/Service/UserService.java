package cloud.user.Service;

import Dto.Account;
import Dto.User;
import Dto.UserRespVo;
import cloud.user.Mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Resource
    UserMapper userMapper;

    //查询所有用户
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }

    //查询所有账户
    public List<Account> selectAllAccount() {
        return userMapper.selectAllAccount();
    }

    //查询所有用户和账户
    public List<UserRespVo> selectAllUserAndAccount() {
        List<User> users = selectAllUser();
        List<Account> accounts = selectAllAccount();
        return UserRespVo.convertFor(users, accounts);
    }

    //根据id查询用户
    public User selectUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    //根据id查询账户
    public Account selectAccountById(Integer id) {
        return userMapper.selectAccountById(id);
    }

    //根据id查询用户和账户
    public UserRespVo selectUserAndAccountById(Integer id) {
        User user = selectUserById(id);
        Account account = selectAccountById(id);
        UserRespVo userRespVo = new UserRespVo();
        BeanUtils.copyProperties(user, userRespVo);
        BeanUtils.copyProperties(account, userRespVo);
        return userRespVo;
    }

    //根据账号查询用户
    public User selectUserByAccount(String userAccount) {
        return userMapper.selectUserByAccount(userAccount);
    }

    //根据账号查询账户
    public Account selectAccountByAccount(String userAccount) {
        return userMapper.selectAccountByAccount(userAccount);
    }

    //根据账号查询用户和账户
    public UserRespVo selectUserAndAccountByAccount(String userAccount) {
        User user = selectUserByAccount(userAccount);
        Account account = selectAccountByAccount(userAccount);
        if (user == null || account == null) {
            return null;
        }
        UserRespVo userRespVo = new UserRespVo();
        BeanUtils.copyProperties(user, userRespVo);
        BeanUtils.copyProperties(account, userRespVo);
        return userRespVo;
    }

    //根据邮箱查询账户
    public Account selectAccountByEmail(String email) {
        return userMapper.selectAccountByEmail(email);
    }

    //根据邮箱查询用户和账户
    public UserRespVo selectUserAndAccountByEmail(String email) {
        Account account = selectAccountByEmail(email);
        if (account == null) {
            return null;
        }
        User user = selectUserById(account.getUserid());
        UserRespVo userRespVo = new UserRespVo();
        BeanUtils.copyProperties(user, userRespVo);
        BeanUtils.copyProperties(account, userRespVo);
        return userRespVo;
    }

    //验证账号和密码
    public int verifyPassword(String userAccount, String password) {
        return userMapper.verifyPassword(userAccount, password);
    }

    //插入用户
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    //插入账户
    public int insertAccount(Account account) {
        return userMapper.insertAccount(account);
    }

    //插入用户和账户
    public boolean insertUserAndAccount(User user, Account account) {
        //插入账户
        int insertAccount = insertAccount(account);
        //设置用户id
        Integer userid = selectAccountByAccount(account.getAccount()).getUserid();
        user.setUserid(userid);
        //插入用户
        int insertUser = insertUser(user);
        return insertAccount + insertUser == 2;
    }
//    public boolean addAccountAndUser(Account account, User user) {
//        int IfAccountInsertOk =userMapper.addAccount(account);//插入account，数据库自动补全id
//        int userid=userMapper.selectUserByAccount(account.getAccount()).getUserid();//获取刚插入的用户的id
//        user.setUserid(userid);//补全User
//        int IfUserInsertOk=userMapper.addUser(user);//插入用户
//        return IfAccountInsertOk + IfUserInsertOk == 2;//只有两个都成功才返回成功
//    }

    //删除用户
    public int deleteUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }

    //删除账户
    public int deleteAccountById(Integer id) {
        return userMapper.deleteAccountById(id);
    }

    //修改密码
    public int updatePassword(Integer id, String oldPassword, String newPassword) {
        return userMapper.updatePassword(id, oldPassword, newPassword);
    }

    //修改用户信息
    public boolean updateUser(Integer userId, String nickname, String sex, String address, Date birthday, String signal) {
        return userMapper.updateUser(userId, nickname, sex, address, birthday, signal);
    }

    //根据用户id查询用户角色
    public String selectRoleByUserId(Integer userId) {
        return userMapper.selectRoleByUserId(userId);
    }
}
