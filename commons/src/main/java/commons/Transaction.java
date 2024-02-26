package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Transaction class.
 */
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private static int id;

    protected String name;
    protected LocalDate date;

    protected double money;

    protected int currency;

    /**
     * Private empty constructor for the Transaction class.
     */
    @SuppressWarnings("unused")
    protected Transaction() {
        // for object mapper
    }

    /**
     * Public class for creating a transaction.
     * @param name - name of the transaction
     * @param date - date of the transaction
     * @param money - value of the transaction
     * @param currency - the currency in which the transaction is handled
     */
    public Transaction(String name, LocalDate date, double money, int currency) {
        id++;
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
    public LocalDate getDate() {
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
    public int getCurrency() {
        return currency;
    }

    /**
     * Setter for the name of the transaction.
     * @param name - name of the transaction.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the date of the transaction.
     * @param date - date of the transaction.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Setter for the value of the transaction.
     * @param money - value of the transaction.
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /**
     * Setter for the currency of the transaction.
     * @param currency - currency in which the transaction is being made.
     */
    public void setCurrency(int currency) {
        this.currency = currency;
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
        return Double.compare(money, that.money) == 0 && Objects.equals(name, that.name) &&
                Objects.equals(date, that.date) && Objects.equals(currency, that.currency);
    }

    /**
     * Hash code method for the transaction.
     * @return - hashCode for the transaction.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, money, currency);
    }

    /**
     * toString method for the Transaction class.
     * @return - string representation of a Transaction with all the data
     */
    @Override
    public String toString() {
        return "Transaction " + "#" + id + ", called: " + name + ", issued on: " + date +
                "with the value: " + currency + money + ";";
    }

}
