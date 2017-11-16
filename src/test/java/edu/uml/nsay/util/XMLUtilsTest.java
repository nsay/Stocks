package edu.uml.nsay.util;

import edu.uml.nsay.model.xml.XMLStocksList;
import org.junit.Test;

import javax.xml.transform.sax.SAXResult;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the XMLUtils class
 *
 * @author Narith Say
 */
public final class XMLUtilsTest {

    /**
     * Test that unmarshal() return value is XMLStocks instance
     * @throws XMLUnmarshalException
     */
    @Test
    public final void testUnmarshalPositive() throws XMLUnmarshalException {
        XMLStocksList quoteList = XMLUtils.unmarshal(XMLUtils.xmlFilePath);
        assertTrue("unmarshal() return value is not an XMLStocksList instance", quoteList instanceof XMLStocksList);
    }

    /**
     * Test that passing an invalid file path reference to unmarshal() throws an XMLUnarshalException
     * @throws XMLUnmarshalException
     */
    @Test(expected=XMLUnmarshalException.class)
    public final void testUnmarshalNegative() throws XMLUnmarshalException {
        XMLStocksList quoteList = XMLUtils.unmarshal("invalid path");
    }

    /**
     * Test that marshal() return value is SAXResult instance
     * @throws XMLMarshalException
     * @throws XMLUnmarshalException
     */
    @Test
    public final void testMarshalPositive() throws XMLMarshalException, XMLUnmarshalException {
        XMLStocksList quoteList = XMLUtils.unmarshal(XMLUtils.xmlFilePath);
        SAXResult result = XMLUtils.marshal(quoteList);
        assertTrue("marshal() return value is not an SAXResult instance", result instanceof SAXResult);
    }

    /**
     * Test that passing a null XMLStocksList to marshal throws an XMLMarhshalException
     * @throws XMLMarshalException
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testMarshalNegative() throws XMLMarshalException {
        XMLStocksList quoteList = null;
        XMLUtils.marshal(quoteList);
    }
}
