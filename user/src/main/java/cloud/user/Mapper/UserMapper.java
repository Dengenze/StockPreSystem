package cloud.user.Mapper;


import Dto.Account;
import Dto.Stock;
import Dto.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {

    //查询所有用户
    @Select("select * FROM user")
    public List<User> selectAllUser();

    //查询所有账户
    @Select("select * from account")
    public List<Account> selectAllAccount();

    //根据id查询用户
    @Select("select * from user where userid = #{id}")
    public User selectUserById(Integer id);

    //根据id查询账户
    @Select("select * from account where userid = #{id}")
    Account selectAccountById(Integer id);

    //根据账号查询用户
    @Select("select * from user where userid = (select userid from account where account = #{userAccount})")
    public User selectUserByAccount(String userAccount);

    //根据账号查询账户
    @Select("select * from account where account = #{userAccount}")
    public Account selectAccountByAccount(String userAccount);

    //根据邮箱查询账户
    @Select("select * from account where email = #{email}")
    public Account selectAccountByEmail(String email);

    //验证账号和密码
    @Select("select count(*) from account where account = #{userAccount} and password = #{password}")
    public int verifyPassword(@Param("userAccount")String userAccount, @Param("password")String password);

    //插入用户
    @Insert("insert into user(userid, nickname, ifbanned) values(#{userid}, #{nickname}, #{ifbanned})")
    public int insertUser(User user);

    //插入账户
    @Insert("insert into account(userid, account, password, email) values(#{userid}, #{account}, #{password}, #{email})")
    public int insertAccount(Account account);

    //删除用户
    @Delete("delete from user where userid = #{id}")
    public int deleteUserById(Integer id);

    //删除账户
    @Delete("delete from account where userid = #{id}")
    public int deleteAccountById(Integer id);

    //删除用户收藏
    @Delete("delete from usertostock where collectionid = (select collectionid from collection where userid = #{id})")
    public void deleteCollectionById(Integer id);

    //删除用户收藏夹
    @Delete("delete from collection where userid = #{id}")
    public void deleteCollectionsById(Integer id);

    //删除账户角色
    @Delete("delete from roletoaccount where userid = #{id}")
    public void deleteRoleToAccountById(Integer id);

    //修改密码
    @Update("update account set password = #{newPassword} where userid = #{id} and password = #{oldPassword}")
    public int updatePassword(@Param("id")Integer id, @Param("oldPassword")String oldPassword, @Param("newPassword")String newPassword);


    //修改用户信息
    @Update("update user set nickname = #{nickname}, sex = #{sex}, address = #{address}, birthday = #{birthday}, `signal` = #{signal} where userid = #{userId}")
    boolean updateUser(@Param("userId") Integer userId, @Param("nickname") String nickname, @Param("sex") String sex, @Param("address") String address, @Param("birthday") Date birthday, @Param("signal") String signal);

    //根据用户id查询用户角色
    @Select("select role from role where roleid = (select roleid from roletoaccount where userid = #{userId})")
    String selectRoleByUserId(Integer userId);
    //修正用户的权限
    @Update("UPDATE roletoaccount SET role = #{role} WHERE userId = #{userId}")
    int changeRole(@Param("userId")int userId,@Param("role") String role);

    //查询收藏夹里全部股票
    @Select("Select * FROM usertostock join stock on usertostock.symbol= stock.symbol WHERE usertostock.collectionid = #{collectionid}")
    List<Stock> selectStockFromCollection(@Param("collectionid") int collectionid);

    //插入用户角色
    @Insert("insert into roletoaccount(userid, roleid) values(#{userid}, #{roleid})")
    public int insertRoleToAccount(@Param("userid") Integer userid, @Param("roleid") Integer roleid);
}
