package pgtest.security;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pgtest.security.domain.Role;
import pgtest.security.domain.User;
import pgtest.BaseServiceTestCase;
import pgtest.security.service.SecurityService;

import java.util.List;

/**
 * Created by moshstef on 6/10/14.
 */
public class SecurityServiceTestCase  extends BaseServiceTestCase {

    @Autowired
    SecurityService securityService;

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        String username = "test9";
        String password = "test";
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);

        List<Role> roles = securityService.loadAllRoles();

        user.addRole(roles.get(0));
        user.addRole(roles.get(1));
        securityService.createUser(user);
        Assert.assertNotNull(user.getId());


        User loaded = securityService.loadUser(user.getId());
        Assert.assertNotNull(loaded);
        Assert.assertEquals(username, loaded.getUsername());
        Assert.assertEquals(password, loaded.getPassword());
        Assert.assertEquals(true, loaded.isEnabled());
        Assert.assertNotNull(loaded.getRoles());
        Assert.assertEquals(2, loaded.getRoles().size());
        Assert.assertEquals(roles.get(0).getName(), loaded.getRoles().get(0).getName());
        Assert.assertEquals(roles.get(1).getName(), loaded.getRoles().get(1).getName());

        Assert.assertNotNull(loaded.getAuthorities());
        Assert.assertEquals(4, loaded.getAuthorities().size());


    }
}
