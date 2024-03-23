package commons;

import java.util.List;
import java.util.Objects;
//import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected int id;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String iban;
    protected int debt;

    //@JsonBackReference
    @ManyToOne
    @JsonIgnoreProperties({"tag", "title", "token", "people", "transactions"})
    public Event event;

    @OneToMany
    @JsonIgnoreProperties({"name", "date", "money", "currency", "expenseType", "participants", "creator"})
    public List<Transaction> createdTransactions;

    @ManyToMany
    @JsonIgnoreProperties({"name", "date", "money", "currency", "expenseType", "type", "participants", "creator"})
    public List<Transaction> transactions;

    /**
     * Constructor for people.
     * @param email individual for each person
     * @param firstName of the person
     * @param lastName of the person
     * @param iban of the bank account of the person
     * @param event that this person is added to
     */
    public Person(String email, String firstName, String lastName, String iban, Event event,
                  List<Transaction> createdTransactions, List<Transaction> transactions) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.iban = iban;
        this.event = event;
        this.debt = 0;
        this.createdTransactions = createdTransactions;
        this.transactions = transactions;
    }

    /**
     * empty constructor (to solve the error given on the class).
     */
    public Person() {

    }


    /**
     * Getter method for the id of the person.
     * @return id of said person
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method for the email of the person.
     * @return email of said person
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter method first name of the person.
     * @return first name of said person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for email.
     * @param email the email to set to
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter for firstName.
     * @param firstName the email to set to
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter for lastName.
     * @param lastName the email to set to
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Setter for iban.
     * @param iban the email to set to
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Setter for debt.
     * @param debt the email to set to
     */
    public void setDebt(int debt) {
        this.debt = debt;
    }

    /**
     * Setter for createdTransactions.
     * @param createdTransactions the email to set to
     */
    public void setCreatedTransactions(List<Transaction> createdTransactions) {
        this.createdTransactions = createdTransactions;
    }

    /**
     * Setter for transactions.
     * @param transactions the email to set to
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Getter method for the last name of the person.
     * @return last name of the person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter method for the iban of that person.
     * @return iban of that person
     */
    public String getIban() {
        return iban;
    }

    /**
     * Getter method for the event that this person has been added to.
     * @return the even that this person has been added to
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Returns the money that this person owes.
     * @return the ammount of money this person owes
     */
    public int getDebt() {
        return debt;
    }

    /**
     * Getter method for the transactions created by the user.
     * @return a set of all transactions that were done by the user.
     */
    public List<Transaction> getCreatedTransactions() {
        return createdTransactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id && debt == person.debt && Objects.equals(email, person.email) &&
                Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) &&
                Objects.equals(iban, person.iban) && Objects.equals(event, person.event) &&
                Objects.equals(createdTransactions, person.createdTransactions) &&
                Objects.equals(transactions, person.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, iban, debt, event, createdTransactions, transactions);
    }

    /**
     * Getter method for the transactions that the person is being into.
     * @return - a set of all the transactions in which the person is a participant.
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    /**
     * Method that adds or substract to dept.
     * @param add the amount to add to the dept
     */
    public void addDept(int add) {
        debt+=add;
    }
}
