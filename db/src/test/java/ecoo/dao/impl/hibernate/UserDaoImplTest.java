package ecoo.dao.impl.hibernate;

import ecoo.dao.UserDao;
import ecoo.data.Role;
import ecoo.data.User;
import ecoo.data.UserStatus;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ecoo-db-test-context.xml"})
@Transactional
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindUsersAssociatedToMe() {
        Collection<User> data = userDao.findUsersAssociatedToMe(-1);
        Assert.assertNotNull(data);

        data = userDao.findUsersAssociatedToMe(1);
        Assert.assertNotNull(data);

        data = userDao.findUsersAssociatedToMe(2);
        Assert.assertNotNull(data);

        data = userDao.findUsersAssociatedToMe(1, 2, 3);
        Assert.assertNotNull(data);
    }

    @Test
    public void testFindUsersByCompany() {
        Collection<User> data = userDao.findUsersByCompany(-1);
        Assert.assertNotNull(data);
    }

    @Test
    public void testFindByRole() throws Exception {
        Collection<User> data = userDao.findByRole(Role.ROLE_SYSADMIN);
        Assert.assertNotNull(data);

        data = userDao.findByRole(Role.ROLE_CHAMBERADMIN);
        Assert.assertNotNull(data);
    }

    @Test
    public void testFindByStatus() throws Exception {
        final Collection<User> data = userDao.findByStatus(UserStatus.Approved);
        Assert.assertNotNull(data);
        Assert.assertTrue(!data.isEmpty());
    }

    @Test
    public void testCountByStatus() throws Exception {
        final long count = userDao.countByStatus(UserStatus.Approved);
        Assert.assertTrue(count > 0);
    }

    @Test
    public void testFindByPersonalReference() throws Exception {
        User SysAdmin = userDao.findByPersonalReference("RSA", "admin");
        Assert.assertNull(SysAdmin);

        SysAdmin = userDao.findByPersonalReference("OTH", "admin");
        Assert.assertNotNull(SysAdmin);
        Assert.assertEquals("SYSTEM", SysAdmin.getFirstName());
        Assert.assertEquals("ADMINISTRATION", SysAdmin.getLastName());
    }

    @Test
    public void testFindAll() {
        final Collection<User> data = userDao.findAll();
        Assert.assertNotNull(data);
    }

    @Test
    public void testFindByUsername() {
        final User Anonymous = userDao.findByUsername("anonymous");
        Assert.assertNotNull(Anonymous);
    }

    @Test
    public void testFindByPrimaryEmailAddress() {
        User Anonymous = userDao.findByPrimaryEmailAddress("xxx");
        Assert.assertNull(Anonymous);

        Anonymous = userDao.findByPrimaryEmailAddress("system@s-squared.co.za");
        Assert.assertNotNull(Anonymous);
    }

    @Test
    public void testFindByMobileNumber() {
        final User newUser = new User();
        newUser.setFirstName("Some");
        newUser.setLastName("Guy");
        newUser.setDisplayName("Some Guy");
        newUser.setPersonalReferenceType("OTH");
        newUser.setPersonalReferenceValue(UUID.randomUUID().toString());
        newUser.setPrimaryEmailAddress("someguy456@hotmail.com");
        newUser.setPassword("password");
        newUser.setUsername("guy456");
        newUser.setMobileNumber("0871230000");
        newUser.setActivationSerialNumber(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.Approved.id());
        userDao.save(newUser);

        User randomUser = userDao.findByPrimaryId(newUser.getPrimaryId());
        Assert.assertNotNull(randomUser);

        User otherUser = userDao.findByMobileNumber("xxx");
        Assert.assertNull(otherUser);

        otherUser = userDao.findByMobileNumber("0871230000");
        Assert.assertNotNull(otherUser);

        userDao.delete(randomUser);
    }

    @Test
    public void testInsert() {
        final User newUser = new User();
        newUser.setFirstName("Some");
        newUser.setLastName("Guy");
        newUser.setDisplayName("Some Guy");
        newUser.setPersonalReferenceType("OTH");
        newUser.setPersonalReferenceValue(UUID.randomUUID().toString());
        newUser.setPrimaryEmailAddress("someguy@hotmail.com");
        newUser.setPassword("password");
        newUser.setUsername("guy123");
        newUser.setActivationSerialNumber(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.Approved.id());
        userDao.save(newUser);

        User randomUser = userDao.findByPrimaryId(newUser.getPrimaryId());
        Assert.assertNotNull(randomUser);

        userDao.delete(randomUser);

        randomUser = userDao.findByPrimaryId(newUser.getPrimaryId());
        Assert.assertNull(randomUser);
    }
}
