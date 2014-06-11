package pgtest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pgtest.security.dao.SecurityDao;
import pgtest.security.domain.Role;
import pgtest.security.domain.User;

/**
 * Created by moshstef on 6/10/14.
 */
public class SecurityService {

    @Autowired
    SecurityDao securityDao;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createUser(User user) {
        securityDao.createUser(user);
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                securityDao.createUserRole(user.getUsername(), role.getRole());
            }
        }
    }


    public User loadUser(String username) {
        return securityDao.loadUser(username);
    }
}
