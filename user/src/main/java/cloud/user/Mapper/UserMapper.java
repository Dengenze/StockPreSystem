package cloud.user.Mapper;


import Dto.Account;
import Dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from account where userid = #{userAccount}")
    public Account selectUserByAccount(String userAccount);

    @Select("select * from account where userid = #{email}")
    public Account selectUserByEmail(String email);
    @Insert("insert into account(userid, account, password, email) values(#{userid}, #{account}, #{password}, #{email})")
    public int addAccount(Account account);
    @Insert("insert into user(userid, nickname, ifbanned) values(#{userid}, #{nickname}, #{ifbanned})")
    public int addUser(User user);
}
