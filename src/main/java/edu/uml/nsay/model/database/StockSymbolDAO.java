package edu.uml.nsay.model.database;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class models the stock_symbol database table
 *
 * @author Narith Say
 */
@Entity
@Table(name = "stock_symbol", schema = "", catalog = "stocks")
public class StockSymbolDAO implements DatabasesAccessObject {

    private int id;
    private String symbol;

    /**
     * Constructs a {@code StockSymbolDAO} that needs to be initialized
     */
    public StockSymbolDAO() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid {@code StockSymbol} instance
     * @param symbol
     */
    public StockSymbolDAO(String symbol) {
        setSymbol(symbol);
    }

    /**
     * Primary Key - Unique ID for a particular row in the stock_symbol table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the stock_symbol table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the symbol of this stock_symbol instance
     */
    @Basic
    @Column(name = "symbol", nullable = false, insertable = true, updatable = true, length = 4)
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol of this stock_symbol instance
     *
     * @param symbol a Timestamp value
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
        int result = id;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        return result;
    }
}
