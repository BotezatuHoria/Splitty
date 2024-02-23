package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Event {


    protected String tag;
    protected String title;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    protected String token;

    /**
     * constructor, constructs the event with all these attributes.
     * @param tag tag of the event.
     * @param title title of the event.
     * @param id auto generated incrementing id of the event.
     * @param token token which is needed to access the event.
     */
    public Event(String tag, String title, int id, String token) {
        this.tag = tag;
        this.title = title;
        this.id = id;
        this.token = token;
    }

    /**
     * unused, empty constructor (to solve the error given on the class).
     */
    public Event() {

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

