package commons;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Event {


    protected String tag;
    protected String title;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    protected String token;

    @OneToMany
    protected Set<Person> people;

    @OneToMany
    protected Set<Transaction> transactions;

    /**
     * constructor, constructs the event with all these attributes.
     * @param tag tag of the event.
     * @param title title of the event.
     * @param id auto generated incrementing id of the event.
     * @param token token which is needed to access the event.
     * @param people people that participate in an event
     * @param transactions transactions of the event
     */
    public Event(String tag, String title, int id, String token, Set<Person> people, Set<Transaction> transactions) {
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
    public Set<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * The getter for people that participate in an event.
     * @return people that participate in an event.
     */
    public Set<Person> getPeople() {
        return people;
    }

    /**
     * The setter for the events` tag.
      * @param tag the new event tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * The setter for the events` title.
     * @param title the new event title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The setter for the events` token.
     * @param token the new event token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * The setter for the events` people that participate.
     * @param people the new set of people that participate
     */
    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    /**
     * The setter for the events` transactions.
     * @param transactions the new set of transactions of the event
     */
    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Adds new person to event.
     * @param person person to add to event.
     */
    public void addPerson(Person person) {
        this.people.add(person);
    }

    /**
     * Removes person from event.
     * @param person person to remove
     */
    public void removePerson(Person person) {
        this.people.remove(person);
    }

    /**
     * Adds new transaction to event.
     * @param transaction transaction to add to event.
     */
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    /**
     * Removes transaction from event.
     * @param transaction transaction to remove
     */
    public void removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
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

    /**
     * compares two objects, if they are the same.
     * @param o object to compare.
     * @return returns wheter two object are the same event.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()){ return false;}
        Event event = (Event) o;
        return id == event.id && Objects.equals(tag, event.tag) && Objects.equals(title, event.title) && Objects.equals(token, event.token) ;
    }

    /**
     * hashcodes the attributes of the event.
     * @return returns the hashcode of the event.
     */
    @Override
    public int hashCode() {
        return Objects.hash(tag, title, id, token);
    }
}

