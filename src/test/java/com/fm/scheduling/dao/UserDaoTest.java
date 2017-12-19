package com.fm.scheduling.dao;

import com.fm.scheduling.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;


public class UserDaoTest extends BaseDaoTest{

    private UserDao userDao;

    @Before
    public void setup() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super.init();
        userDao = new UserDao();
    }

    @Test
    public void testInsertUser_success() throws SQLException {
        User user = User.createInstance(User.class);
        user.setUserName("abcd");
        user.setPassword("password");
        user.setActive(true);
        int i = userDao.insert(user, userCreator);
        Assert.assertTrue(i > 0);
    }

    @Test
    public void testUpdateUser_success() throws SQLException {
        User user = User.createInstance(User.class);
        user.setUserName("abcd");
        user.setPassword("password");
        user.setActive(true);
        int i = userDao.insert(user, userCreator);
        User user1 = userDao.getById(i);

        user1.setUserName("xvcb");
        user1.setPassword("password2");
        user1.setActive(false);
        user1.setLastUpdateBy("EL FANI");
        userDao.update(user1, userCreator, i);
        User user2 = userDao.getById(i);
        Assert.assertNotNull(user2);
        Assert.assertEquals("xvcb", user2.getUserName());
    }

    @Test
    public void testDeleteUser_success() throws SQLException {
        User user = User.createInstance(User.class);
        user.setUserName("abcd");
        user.setPassword("password");
        user.setActive(true);
        int i = userDao.insert(user, userCreator);
        User user1 = userDao.getById(i);
        Assert.assertNotNull(user1);
        userDao.delete(i);
        User user2 = userDao.getById(i);
        Assert.assertNull(user2);
    }
}
