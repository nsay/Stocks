package edu.uml.nsay.model.database;

import edu.uml.nsay.util.DatabaseUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for PersonDAO Class
 *
 * @author Narith Say
 */
public class PersonDAOTest extends AbstractBaseDAOTest {

    @Test
    public void testRead() {
        PersonDAO personDAO = DatabaseUtils.findUniqueResultBy("id", 1, PersonDAO.class, true);
        assertTrue("first PersonDAO found", personDAO.getId() == 1);
    }

    @Test
    public void testWrite() throws Exception {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        PersonDAO personDAO = new PersonDAO();
        personDAO.setUserName("spencer");
        session.saveOrUpdate(personDAO);
        transaction.commit();
    }


}