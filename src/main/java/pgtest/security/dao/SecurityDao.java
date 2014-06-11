package pgtest.security.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetails;
import pgtest.security.domain.Role;
import pgtest.security.domain.User;

import java.util.List;

/**
 * Created by moshstef on 6/10/14.
 */
public interface SecurityDao {

    User loadUser(Long userId);

    int createUser(User user);

    int updateUser(User user);

    int deleteUser(Long userId);

    List<User> loadAllUsers();

    int createUserRole(@Param("userId")Long userId, @Param("roleId")Long roleId);

    List<Role> loadAllRoles();

    User loadUserByUsername(String username);
}
