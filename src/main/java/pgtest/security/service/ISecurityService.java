package pgtest.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pgtest.security.domain.Role;
import pgtest.security.domain.User;

import java.util.List;

/**
 * Created by moshstef on 6/13/14.
 */
public interface ISecurityService extends UserDetailsService {
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void createUser(User user);

    User loadUser(Long userId);

    List<Role> loadAllRoles();

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    int increaseLoginTries(Long userId);

    int updateLastLoginInfoForUser(Long userId);
}
