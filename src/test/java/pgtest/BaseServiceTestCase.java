package pgtest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by moshstef on 5/25/14.
 */
@ContextConfiguration(locations = {"classpath*:spring/system-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseServiceTestCase {


    @Autowired
    JdbcTemplate jdbcTemplate;




}

