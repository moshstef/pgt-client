package pgttest.security;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pgtest.security.SecurityService;
import pgtest.security.dao.SecurityDao;
import pgtest.security.domain.Role;
import pgtest.security.domain.User;
import pgttest.BaseServiceTestCase;

import java.util.ArrayList;
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
        String username = "test4";
        user.setUsername(username);
        user.setPassword("test");
        user.setEnabled(true);

        List<Role> roles = new ArrayList<Role>();
        roles.add(new Role("ADMINISTRATOR"));
        user.setRoles(roles);
        securityService.createUser(user);

        User loaded = securityService.loadUser(username);
        Assert.assertNotNull(loaded);
        Assert.assertEquals(username, loaded.getUsername());
        Assert.assertEquals("test", loaded.getPassword());
        Assert.assertEquals(true, loaded.isEnabled());
        Assert.assertNotNull(loaded.getRoles());
        Assert.assertEquals(1, loaded.getRoles().size());
        Assert.assertEquals("ADMINISTRATOR", loaded.getRoles().get(0).getRole());

    }
}
