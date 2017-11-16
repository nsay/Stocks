package edu.uml.nsay.model.xml;

import edu.uml.nsay.util.XMLUnmarshalException;
import edu.uml.nsay.util.XMLUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the XMLStocksList class.
 *
 * @author Narith Say
 */
public final class XMLStocksListTest {

    /**
     * Test that getStocks returns a list of XMLStocks instances
     *
     * @throws XMLUnmarshalException
     */
    @Test
    public final void testGetStocksPositive() throws XMLUnmarshalException {
        XMLStocksList quoteList = XMLUtils.unmarshal(XMLUtils.xmlFilePath);
        assertTrue("getStocks does not return a list of XMLStocks instances",
                quoteList.getStock() instanceof List && quoteList.getStock().get(0) instanceof XMLStocks);
    }

    /**
     * Test that passing a nonexistant file path reference to unmarshal() generates an XMLUnmarshalException
     *
     * @throws XMLUnmarshalException
     */
    @Test(expected=XMLUnmarshalException.class)
    public final void testGetStocksNegative() throws XMLUnmarshalException {
        XMLStocksList quoteList = XMLUtils.unmarshal("Hamm N Ch");
    }

    /**
     * Test that toString returns a String instance
     *
     * @throws XMLUnmarshalException
     */
    @Test
    public final void testToStringPositive() throws XMLUnmarshalException {
        XMLStocksList quoteList = XMLUtils.unmarshal(XMLUtils.xmlFilePath);
        assertTrue("toString does not return a String instance", quoteList.toString() instanceof String);
    }

    /**
     * Test that toString does not return a String other than one which is derived from XML data
     *
     * @throws XMLUnmarshalException
     */
    @Test
    public final void testToStringNegative() throws XMLUnmarshalException {
        XMLStocksList quoteList = XMLUtils.unmarshal(XMLUtils.xmlFilePath);
        assertFalse("toString returns a String containing nonsense", quoteList.toString().equals("StringCheese"));
    }
}
