package edu.uml.nsay.model.database;

import javax.persistence.*;

/**
 * This class models the person_stocks database table
 *
 * @author Narith Say
 */
@Entity
@Table(name="person_stocks", catalog="stocks")
public class PersonStockDAO implements DatabaseAccessObject {
    // private fields of this class
    private int id;
    private PersonDAO person;
    private StockSymbolDAO stockSymbol;

    /**
     * Constructs a PersonStockDAO that needs to be initialized
     */
    public PersonStockDAO() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Constructs a valid PersonStockDAO instance
     *
     * @param person the person to assign the stock symbol to
     * @param symbol  the stock symbol to associate the person with
     */
    public PersonStockDAO(PersonDAO person, StockSymbolDAO symbol) {
        setPerson(person);
        setStockSymbol(symbol);
    }

    /**
     * Gets the unique ID for this PersonStockDAO instance
     *
     * @return an integer value representing the unique ID for this PersonStock
     */
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID for this PersonStockDAO instance.
     * This method should not be called by client code. The value is managed internally.
     *
     * @param id an integer value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns a defensive copy of the mutable PersonDAO object
     * assigned to the corresponding field of this class
     *
     * @return the person associated with this PersonStockDAO instance
     */
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public PersonDAO getPerson() {
        PersonDAO person = new PersonDAO(this.person.getFirstName(), this.person.getLastName());
        person.setId(this.person.getId());
        return person;
    }

    /**
     * Sets the first name of this PersonStockDAO instance
     *
     * @param person a Person instance
     */
    public void setPerson(PersonDAO person) {
        this.person = person;
    }

    /**
     * Returns a defensive copy of the mutable StockSymbolDAO object
     * assigned to the corresponding field of this class
     *
     * @return the stock symbol of this PersonStockDAO
     */
    @ManyToOne
    @JoinColumn(name = "symbol_id", referencedColumnName = "id", nullable = false)
    public StockSymbolDAO getStockSymbol() {

        StockSymbolDAO stockSymbol = new StockSymbolDAO(this.stockSymbol.getSymbol());
        stockSymbol.setId(this.stockSymbol.getId());
        return stockSymbol;
    }

    /**
     * Sets the stock symbol of this PersonStockDAO instance
     *
     * @param stockSymbol a String value
     */
    public void setStockSymbol(StockSymbolDAO stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonStockDAO personStock = (PersonStockDAO) o;

        if (id != personStock.id) return false;
        if (!person.equals(personStock.person)) return false;
        if (!stockSymbol.equals(personStock.stockSymbol)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + person.hashCode();
        result = 31 * result + stockSymbol.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PersonStock{" +
                "id=" + id +
                ", person='" + person + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                '}';
    }
}
