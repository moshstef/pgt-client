package pgttest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pgtest.dao.UserDao;
import pgtest.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Created by moshstef on 5/25/14.
 */
@ContextConfiguration(locations = {"classpath*:spring/system-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseServiceTestCase {


    @Autowired
    JdbcTemplate jdbcTemplate;




}

