package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Currency;
import java.util.Date;
import java.util.Objects;

/**
 * Transaction class.
 */
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    protected String name;
    protected Date date;

    protected double money;

    protected Currency currency;

    /**
     * Private empty constructor for the Transaction class.
     */
    private Transaction() {

    }

    /**
     * Public class for creating a transaction.
     * @param name
     * @param date
     * @param money
     * @param currency
     */
    public Transaction(String name, Date date, double money, Currency currency) {
        this.name = name;
        this.date = date;
        this.money = money;
        this.currency = currency;
    }

    /**
     * Getter for the id of a transaction.
     * @return - int representation of an id.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the name of a transaction.
     * @return - String representation of the name of the transaction.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the Date of the transaction.
     * @return - Date representation of the transaction.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter for the value of the transaction.
     * @return - double representation of the amount to be transferred.
     */
    public double getMoney() {
        return money;
    }

    /**
     * Getter for the currency in which the transaction is being done.
     * @return - Currency representation of the amount to be transferred.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Equals method for the transaction class.
     * @param o - object to be compared to.
     * @return - if the 2 objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return id == that.id && Double.compare(money, that.money) == 0 && Objects.equals(name, that.name) && Objects.equals(date, that.date) && Objects.equals(currency, that.currency);
    }

    /**
     * Hash code method for the transaction.
     * @return - hashCode for the transaction.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, money, currency);
    }
}
