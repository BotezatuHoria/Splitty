package commons;

import java.util.Objects;

public class Currency {
  protected int ico;
   protected String name;

  /**
   * Constructor.
   * @param ico ico number of a currency
   * @param name name of a currency
   */
  public Currency(int ico, String name) {
    this.ico = ico;
    this.name = name;
  }
  /**
   * Getter for ico number.
   * @return ico
   */
  public int getIco() {
    return ico;
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
    return ico == currency.ico && Objects.equals(name, currency.name);
  }

  /**
   * Hashcode.
   * @return hashcode of the class
   */
  @Override
  public int hashCode() {
    return Objects.hash(ico, name);
 }

 /**
  * String representation.
  * @return string representation of the class
  */
 @Override
  public String toString() {
    return "Ico number is: " + ico +
            ", name of the currency is: " + name;
  }
}
