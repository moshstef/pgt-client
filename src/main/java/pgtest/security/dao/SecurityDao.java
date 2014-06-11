package pgtest.security.dao;

import org.apache.ibatis.annotations.Param;
import pgtest.security.domain.User;

import java.util.List;

/**
 * Created by moshstef on 6/10/14.
 */
public interface SecurityDao {

    User loadUser(String username);

    int createUser(User user);

    int updateUser(User user);

    int deleteUser(String username);

    List<User> loadAllUsers();

    int createUserRole(@Param("username")String username, @Param("role")String role);

}
