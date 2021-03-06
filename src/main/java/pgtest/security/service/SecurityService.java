package pgtest.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pgtest.security.dao.SecurityDao;
import pgtest.security.domain.Role;
import pgtest.security.domain.User;

import java.util.List;

/**
 * Created by moshstef on 6/10/14.
 */
public class SecurityService implements  ISecurityService {

     SecurityDao securityDao;

     PasswordEncryption passwordEncryption;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createUser(User user) {
        if( user.getPassword() != null ){
            user.setPassword(passwordEncryption.encrypt(user.getPassword()));
        }
        securityDao.createUser(user);
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                securityDao.createUserRole(user.getId(), role.getId());
            }
        }
    }


    @Override
    public User loadUser(Long userId) {
        return securityDao.loadUser(userId);
    }

    @Override
    public List<Role> loadAllRoles() {
        return securityDao.loadAllRoles();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = securityDao.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("Username '" + username + "' not found");
        }
        return userDetails;
    }

    @Override
    public int increaseLoginTries(Long userId) {
        return securityDao.increaseLoginTries(userId);
    }

    @Override
    public int updateLastLoginInfoForUser(Long userId) {
        return securityDao.updateLastLoginInfoForUser(userId);

    }

    public void setSecurityDao(SecurityDao securityDao) {
        this.securityDao = securityDao;
    }

    public void setPasswordEncryption(PasswordEncryption passwordEncryption) {
        this.passwordEncryption = passwordEncryption;
    }
}
