package edu.uml.nsay.model.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class models the person_stocks database table
 *
 * @author Narith Say
 */
@Entity
@Table(name = "person_stocks", schema = "", catalog = "stocks")
public class PersonStockDAO implements DatabasesAccessObject {

    // private fields of this class
    private int id;
    private PersonDAO personDAO;
    private StockSymbolDAO stockSymbolDAO;

    /**
     * Constructs a {@code PersonStockDAO} that needs to be initialized
     */
    public PersonStockDAO() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid {@code PersonStockDAO}
     * @param personDAO the person to assign the stock symbol to
     * @param stockSymbol  the stock symbol to associate the person with
     */
    public PersonStockDAO(PersonDAO personDAO, StockSymbolDAO stockSymbol) {
        setPerson(personDAO);
        setStockSymbol(stockSymbol);
    }

    /**
     * Primary Key - Unique ID for a particular row in the person_stocks table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the person_stocks table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns a defensive copy of the mutable {@code PersonDAO} object
     * assigned to the corresponding field of this class
     *
     * @return the person associated with this {@code PersonStockDAO} instance
     */
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public PersonDAO getPerson() {
        PersonDAO personDAO = new PersonDAO(this.personDAO.getId(), this.personDAO.getUserName());
        personDAO.setId(this.personDAO.getId());
        return personDAO;
    }

    /**
     * Sets the first name of this {@code PersonStockDAO} instance
     * @param personDAO a PersonDAO instance
     */
    public void setPerson(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    /**
     * Returns a defensive copy of the mutable {@code StockSymbolDAO} object
     * assigned to the corresponding field of this class
     *
     * @return the stock symbol of this {@code StockSymbolDAO}
     */
    @ManyToOne
    @JoinColumn(name = "symbol_id", referencedColumnName = "id", nullable = false)
    public StockSymbolDAO getStockSymbol() {

        StockSymbolDAO stockSymbolDAO = new StockSymbolDAO(this.stockSymbolDAO.getSymbol());
        stockSymbolDAO.setId(this.stockSymbolDAO.getId());
        return stockSymbolDAO;
    }

    /**
     * Sets the stock symbol of this {@code StockSymbolDAO} instance
     *
     * @param stockSymbolDAO a String value
     */
    public void setStockSymbol(StockSymbolDAO stockSymbolDAO) {
        this.stockSymbolDAO = stockSymbolDAO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonStockDAO that = (PersonStockDAO) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}