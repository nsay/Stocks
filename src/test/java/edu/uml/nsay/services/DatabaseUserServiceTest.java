package edu.uml.nsay.services;

import edu.uml.nsay.model.User;
import edu.uml.nsay.util.DatabaseInitializationException;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for DatabaseUserService class
 *
 * Created by Narith on 10/22/17.
 */
public class DatabaseUserServiceTest extends DatabaseServiceTest{


    private UserService databaseUserService;

    @Before
    public void setUp() throws DatabaseInitializationException {
        super.setUp();
        databaseUserService = ServiceFactory.getUserService();
    }

    @Test
    public void testAddPerson() throws Exception{
        String sam = "Vic";
        User user = new User(sam);
        databaseUserService.addPerson(user);
    }

    @Test(expected = DuplicateUserNameException.class)
    public void testAddPersonDuplicateUser() throws Exception{
        String sam = "Sam";
        User user = new User(sam);
        databaseUserService.addPerson(user);

    }

}
