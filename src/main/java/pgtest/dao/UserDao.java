package pgtest.dao;

import pgtest.domain.User;

import java.util.List;

/**
 * Created by moshstef on 5/25/14.
 */
public interface UserDao {
    List<User> getAllUsers();
}
