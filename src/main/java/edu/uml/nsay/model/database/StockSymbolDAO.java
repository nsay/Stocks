package edu.uml.nsay.model.database;

import javax.persistence.*;

/**
 * This class models the stock_symbols database table
 *
 * @author Narith Say
 */
@Entity
@Table(name="stock_symbols", catalog="stocks")
public class StockSymbolDAO implements DatabaseAccessObject {

    /**
     * Constructs a StockSymbol that needs to be initialized
     */
    public StockSymbolDAO() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid StockSymbol instance
     *
     * @param symbol
     */
    public StockSymbolDAO(String symbol) {
        setSymbol(symbol);
    }

    private int id;
    private String symbol;

    /**
     * @return the id field of this StockSymbol instance
     */
    @Id
    @Column(name = "id",  nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets the id field of this StockSymbol instance
     *
     * @param id an int value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the symbol field of this StockSymbol instance
     */
    @Basic
    @Column(name = "symbol", nullable = false, insertable = true, updatable = true, length = 4)
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol field of this StockSymbol instance
     *
     * @param symbol a String
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockSymbolDAO that = (StockSymbolDAO) o;

        if (id != that.id) return false;
        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 31 * (symbol != null ? symbol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "symbol=" + symbol;
    }
}
