package commons;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String email;
    private String firstName;
    private String lastName;
    private String iban;
    private int debt;

    @ManyToOne
    public Event event;

    @OneToMany
    public Set<Transaction> createdTransactions;

    @ManyToMany
    public Set<Transaction> transactions;

    /**
     * Constructor for people.
     * @param email individual for each person
     * @param firstName of the person
     * @param lastName of the person
     * @param iban of the bank account of the person
     * @param event that this person is added to
     */
    public Person(String email, String firstName, String lastName, String iban, Event event,
                  Set<Transaction> createdTransactions, Set<Transaction> transactions) {
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
    public Set<Transaction> getCreatedTransactions() {
        return createdTransactions;
    }

    /**
     * Getter method for the transactions that the person is being into.
     * @return - a set of all the transactions in which the person is a participant.
     */
    public Set<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Equals method that returns true if the person is the same else false.
     * @param o object to compare too
     * @return true if the email is the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass())
        {return false;}
        Person that = (Person) o;
        return Objects.equals(email, that.email);
    }

    /**
     * Hashcode for the people.
     * @return integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    /**
     * Method that adds or substract to dept.
     * @param add the amount to add to the dept
     */
    public void addDept(int add) {
        debt+=add;
    }
}