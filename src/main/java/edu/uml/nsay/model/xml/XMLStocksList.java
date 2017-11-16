package edu.uml.nsay.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 *  This class is originally part of XMLStocks's "List<XMLStocks.stock>" methods.
 *  This is separated for easier access and less confusion.
 *
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;sequence>
 *    &lt;element ref="{}stock"/>
 *   &lt;/sequence>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "stock"
})
@XmlRootElement(name = "stocks")
public class XMLStocksList implements XMLDomainObject {

    @XmlElement(required = true)
    protected List<XMLStocks> stock;

    /**
     * Gets the value of the stock property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the child property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStocks().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLStocks }
     *
     *
     *
     */
    public List<XMLStocks> getStock() {
        if (this.stock == null) {
            this.stock = new ArrayList<XMLStocks>();
        }
        return this.stock;
    }

    @Override
    public String toString() {
        return "XMLStocksList{" + stock +
                '}';
    }
}
