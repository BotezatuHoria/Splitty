package commons;

import jakarta.transaction.Transaction;

import java.util.List;
import java.util.Objects;

public class Event {
    protected String tag;
    protected String title;
    protected int id;
    protected String token;
    protected List<Person> personList;
    protected List<Transaction> transactionList;

    /**
     * constructor, constructs the event with all these attributes.
     * @param tag
     * @param title
     * @param id
     * @param token
     * @param personList
     * @param transactionList
     */
    public Event(String tag, String title, int id, String token, List<Person> personList, List<Transaction> transactionList) {
        this.tag = tag;
        this.title = title;
        this.id = id;
        this.token = token;
        this.personList = personList;
        this.transactionList = transactionList;
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
     * the getter of the id.
     * @return  returns the id of the event.
     */
    public int getId() {
        return id;
    }

    /**
     * the getter of the token, gives you access to the event.
     * @return returns the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * the getter of the list of persons.
     * @return returns the list of persons participating in this event.
     */
    public List<Person> getPersonList() {
        return personList;
    }

    /**
     * the getter of the list of transactions of the event.
     * @return returns the transactions.
     */
    public List<Transaction> getTransactionList() {
        return transactionList;
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
                ", personList=" + personList +
                ", transactionList=" + transactionList +
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
        return id == event.id && Objects.equals(tag, event.tag) && Objects.equals(title, event.title) && Objects.equals(token, event.token) && Objects.equals(personList, event.personList) && Objects.equals(transactionList, event.transactionList);
    }

    /**
     * hashcodes the attributes of the event.
     * @return returns the hashcode of the event.
     */
    @Override
    public int hashCode() {
        return Objects.hash(tag, title, id, token, personList, transactionList);
    }
}

