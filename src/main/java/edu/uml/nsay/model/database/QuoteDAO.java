package edu.uml.nsay.model.database;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * This class models the quote database table
 *
 * @author Narith Say
 */
@Entity
@Table(name = "quote", schema = "", catalog = "stocks")
public class QuoteDAO implements DatabasesAccessObject {

    private int id;
    private Timestamp time;
    private BigDecimal price;
    private StockSymbolDAO stockSymbol;

    /**
     * Constructs a {@code QuoteDAO} that needs to be initialized
     */
    public QuoteDAO() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid {@code QuoteDAO}
     * @param time the time to assign to this instance
     * @param price  the price to assign to this instance
     * @param stockSymbol the stockSymbol to assign to this instance
     */
    public QuoteDAO(Timestamp time, BigDecimal price, StockSymbolDAO stockSymbol) {
        setTime(time);
        setPrice(price);
        setStockSymbol(stockSymbol);
    }

    /**
     * Primary Key - Unique ID for a particular row in the quote table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "id",  nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the quote table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the time of this quote instance
     */
    @Basic
    @Column(name = "time", nullable = false, insertable = true, updatable = true)
    public Timestamp getTime() {
        return time;
    }

    /**
     * Sets the time of this quote instance
     *
     * @param time a Timestamp value
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    /**
     * @return the price of this quote instance
     */
    @Basic
    @Digits(integer=5, fraction=2)
    @Column(name = "price", nullable = false, insertable = true, updatable = true, precision = 0)
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price of this quote instance
     *
     * @param price a BigDecimal value
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Returns a defensive copy of the mutable {@code StockSymbolDAO} object
     * assigned to the corresponding field of this class
     *
     * @return the stockSymbol field of this {@code StockSymbolDAO} instance
     */
    @ManyToOne
    @JoinColumn(name = "symbol_id", referencedColumnName = "id",nullable = false)
    public StockSymbolDAO getStockSymbol() {
        StockSymbolDAO stockSymbol = new StockSymbolDAO(this.stockSymbol.getSymbol());
        stockSymbol.setId(this.stockSymbol.getId());
        return stockSymbol;
    }

    /**
     * Sets the stockSymbol field of this {@code StockSymbolDAO} instance
     *
     * @param stockSymbol a DatabaseStockSymbol object
     */
    public void setStockSymbol(StockSymbolDAO stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuoteDAO quoteDAO = (QuoteDAO) o;

        if (id != quoteDAO.id) return false;
        if (price != quoteDAO.price) return false;
        if (time != null ? !time.equals(quoteDAO.time) : quoteDAO.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + price.hashCode();
        return result;
    }
}
