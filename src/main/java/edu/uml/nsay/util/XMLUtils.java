package edu.uml.nsay.util;

import edu.uml.nsay.model.xml.XMLStocksList;
import org.dom4j.io.SAXContentHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXResult;
import java.io.*;

/**
 * A class that contains XML-related utility methods.
 *
 * @author Narith Say
 */
public final class XMLUtils {
    // fields of this class
    public static final String xmlFilePath = "src/main/resources/xml/stock_info.xml";

    /**
     * Unmarshals XML data into XML domain object XMLStocksList
     *
     * @param xmlPath String containing a reference to the file containing XML data
     * @return XMLStocksList
     * @throws XMLUnmarshalException
     */
    public static final XMLStocksList unmarshal(String xmlPath) throws XMLUnmarshalException {
        XMLStocksList quotes = null;
        try {
            boolean eof = false;
            StringBuilder xmlInstance = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(xmlPath));
            while (!eof) {
                String line = reader.readLine();
                if (line == null) {
                    eof = true;
                } else {
                    xmlInstance.append(line);
                }
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(XMLStocksList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            quotes = (XMLStocksList) unmarshaller.unmarshal(new StringReader(xmlInstance.toString()));
        } catch (IOException | JAXBException e) {
            throw new XMLUnmarshalException(e.getMessage());
        }
        return quotes;
    }

    /**
     * Marshals XML domain object XMLStocksList into XML data
     *
     * @param quotes list of quotes in the format of an XML domain object
     * @return SAXResult
     * @throws XMLMarshalException
     */
    public static final SAXResult marshal(XMLStocksList quotes) throws XMLMarshalException {
        // here is how to go from Java to XML
        SAXResult result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(XMLStocksList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            result = new SAXResult(new SAXContentHandler());
            marshaller.marshal(quotes, result);
        } catch (JAXBException e) {
            throw new XMLMarshalException(e.getMessage());
        }
        return result;
    }
}
