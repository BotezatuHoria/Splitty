package commons;

import java.util.Objects;
//import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected int id;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String iban;
    protected double debt;

    /**
     * Constructor for people.
     * @param email individual for each person
     * @param firstName of the person
     * @param lastName of the person
     * @param iban of the bank account of the person
     */
    public Person(String email, String firstName, String lastName, String iban) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.iban = iban;
        this.debt = 0;
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
    public void setDebt(double debt) {
        this.debt = debt;
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
     * Returns the money that this person owes.
     * @return the ammount of money this person owes
     */
    public double getDebt() {
        return debt;
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
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    /**
     * Method that adds or substract to dept.
     * @param add the amount to add to the dept
     */
    public void addDept(double add) {
        debt+=add;
    }
}
