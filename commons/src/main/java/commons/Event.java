package commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Event {

    protected String tag;
    protected String title;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected int id;
    protected String token;

    private Date creationDate = new Date();
    private Date lastModified = new Date();

    @OneToMany
    @JsonIgnoreProperties({"firstName", "lastName", "iban", "email", "debt", "event", "createdTransactions", "transactions"})
    protected List<Person> people;

    @OneToMany
    @JsonIgnoreProperties({"name", "date", "money", "currency", "expenseType", "participants", "creator"})
    protected List<Transaction> transactions;

    /**
     * constructor, constructs the event with all these attributes.
     * @param tag tag of the event.
     * @param title title of the event.
     * @param id auto generated incrementing id of the event.
     * @param token token which is needed to access the event.
     * @param people people that participate in an event
     * @param transactions transactions of the event
     */
    public Event(String tag, String title, int id, String token, List<Person> people, List<Transaction> transactions) {
        this.tag = tag;
        this.title = title;
        this.id = id;
        this.token = token;
        this.people = people;
        this.transactions = transactions;
    }

    /**
     * unused, empty constructor (to solve the error given on the class).
     */
    public Event() {

    }

    /**
     * the getter of the id.
     * @return returns the id of the event.
     */
    public int getId() {
        return id;
    }

    /**
     * the getter of the tag.
     * @return  returns the tag of the event.
     */
    public String getTag() {
        return tag;
    }

    /**
     * the getter of title.
     * @return returns the title of the event.
     */
    public String getTitle() {
        return title;
    }

    /**
     * the getter of the token, gives you access to the event.
     * @return returns the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * the getter for the transactions of the event.
     * @return the transactions of the event
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * The getter for people that participate in an event.
     * @return people that participate in an event.
     */
    public List<Person> getPeople() {
        return people;
    }

    /**
     * The setter for the events` tag.
      * @param tag the new event tag
     */
    public void setTag(String tag) {
        this.tag = tag;
        lastModified = new Date();
    }

    /**
     * The setter for the events` title.
     * @param title the new event title
     */
    public void setTitle(String title) {
        this.title = title;
        lastModified = new Date();
    }

    /**
     * The setter for the events` token.
     * @param token the new event token
     */
    public void setToken(String token) {
        this.token = token;
        lastModified = new Date();
    }

    /**
     * The setter for the events` people that participate.
     * @param people the new set of people that participate
     */
    public void setPeople(List<Person> people) {
        this.people = people;
        lastModified = new Date();
    }

    /**
     * The setter for the events` transactions.
     * @param transactions the new set of transactions of the event
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        lastModified = new Date();
    }

    /**
     * Adds new person to event.
     * @param person person to add to event.
     */
    public void addPerson(Person person) {
        this.people.add(person);
        lastModified = new Date();
    }

    /**
     * Removes person from event.
     * @param person person to remove
     */
    public void removePerson(Person person) {
        this.people.remove(person);
        lastModified = new Date();
    }

    /**
     * Adds new transaction to event.
     * @param transaction transaction to add to event.
     */
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        lastModified = new Date();
    }

    /**
     * Removes transaction from event.
     * @param transaction transaction to remove
     */
    public void removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        lastModified = new Date();
    }

    /**
     * makes the event into a readable string.
     * @return returns a string of the event.
     */
    @Override
    public String toString() {
        return "Event{" +
                "tag='" + tag + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return id == event.id && Objects.equals(tag, event.tag) && Objects.equals(title, event.title) &&
                Objects.equals(token, event.token) && Objects.equals(people, event.people) &&
                Objects.equals(transactions, event.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, title, id, token, people, transactions);
    }

    /**
     * Returns the creation date of the event.
     * @return returns the creation date
     */
    public Date getCreationDate(){
        return this.creationDate;
    }

    /**
     * Return the date the event was last modified
     * @return returns the date
     */
    public Date getLastModified(){
        return this.lastModified;
    }
}

