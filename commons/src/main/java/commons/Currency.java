package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


import java.util.Objects;

@Entity
public class Currency {

  @Id
  protected int iso;
  protected String name;

  /**
   * Constructor.
   * @param iso iso number of a currency
   * @param name name of a currency
   */
  public Currency(int iso, String name) {
    this.iso = iso;
    this.name = name;
  }
  /**
   * empty constructor (to solve the error given on the class).
   */
  public Currency() {

  }

  /**
   * Getter for iso number.
   * @return iso
   */
  public int getIso() {
    return iso;
  }

  /**
   * Getter for name of the currency.
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   *Compares the class with some object and returns true
   * if it is the same.
   * @param o some object
   * @return true if equals or false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Currency currency = (Currency) o;
    return iso == currency.iso && Objects.equals(name, currency.name);
  }

  /**
   * Hashcode.
   * @return hashcode of the class
   */
  @Override
  public int hashCode() {
    return Objects.hash(iso, name);
 }

 /**
  * String representation.
  * @return string representation of the class
  */
 @Override
  public String toString() {
    return "Iso number is: " + iso +
            ", name of the currency is: " + name;
  }
}
