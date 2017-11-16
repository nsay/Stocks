package edu.uml.nsay.model.database;

import edu.uml.nsay.model.StockQuote;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * This class models the quotes database table
 *
 * @author Narith Say
 */
@Entity
@Table(name="quotes", catalog="stocks")
public class QuoteDAO implements DatabaseAccessObject {
    private int id;
    private Timestamp time;
    private BigDecimal price;
    private StockSymbolDAO stockSymbol;

    /**
     * Constructs a QuoteDAO that needs to be initialized
     */
    public QuoteDAO() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid QuoteDAO
     *
     * @param time the time to assign to this instance
     * @param price  the price to assign to this instance
     * @param symbol the stockSymbol to assign to this instance
     */
    public QuoteDAO(DateTime time, BigDecimal price, StockSymbolDAO symbol) {
        setTime(time);
        setPrice(price);
        setStockSymbol(symbol);
    }

    /**
     * @return the id field of this QuoteDAO instance
     */
    @Id
    @Column(name = "id",  nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets the id field of this user to the parameter value
     *
     * @param id an integer value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the time field of this QuoteDAO instance
     */
    @Basic
    @Column(name = "time", nullable = false, insertable = true, updatable = true)
    public Timestamp getTime() {
        return time;
    }

    /**
     * Sets the time field of this user to the parameter value
     *
     * @param time a Timestamp object
     */
    public void setTime(DateTime time) {
        this.time = new Timestamp(time.getMillis());
    }

    /**
     * @return the price field of this QuoteDAO instance
     */
    @Basic
    @Column(name = "price", nullable = false, insertable = true, updatable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price field of this QuoteDAO instance
     *
     * @param price a BigDecimal object
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Returns a defensive copy of the mutable StockSymbolDAO object
     * assigned to the corresponding field of this class
     *
     * @return the stockSymbol field of this QuoteDAO instance
     */
    @ManyToOne
    @JoinColumn(name = "symbol_id", referencedColumnName = "id",nullable = false)
    public StockSymbolDAO getStockSymbol() {
        StockSymbolDAO stockSymbol = new StockSymbolDAO(this.stockSymbol.getSymbol());
        stockSymbol.setId(this.stockSymbol.getId());
        return stockSymbol;
    }

    /**
     * Sets the stockSymbol field of this QuoteDAO instance
     *
     * @param stockSymbol a StockSymbolDAO object
     */
    public void setStockSymbol(StockSymbolDAO stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuoteDAO stockQuote = (QuoteDAO) o;

        if (id != stockQuote.id) return false;
        if (price != stockQuote.price) return false;
        if (time != null ? !time.equals(stockQuote.time) : stockQuote.time != null) return false;
        if (!stockSymbol.equals(stockQuote.stockSymbol)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + price.hashCode();
        result = 31 * result + (stockSymbol != null ? stockSymbol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StockQuote{" +
                "id =" + id +
                ", stockSymbol=" + stockSymbol +
                ", date='" + new DateTime(time).toString(StockQuote.getDateFormatter()) + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
