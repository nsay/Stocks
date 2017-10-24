package edu.uml.nsay.model.database;

import edu.uml.nsay.util.DatabaseUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for QuoteDOA
 *
 * Created by Narith
 */
public class QuoteDAOTest extends AbstractBaseDAOTest {

    @Test
    public void testRead() {
        QuoteDAO quoteDAO = DatabaseUtils.findUniqueResultBy("id", 1, QuoteDAO.class, true);
        assertTrue("first quoteDAO found", quoteDAO.getId() == 1);
    }


}
