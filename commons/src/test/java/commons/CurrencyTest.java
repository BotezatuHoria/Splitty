package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {
  /**
   * tests getter for ico number
   */
  @Test
  void getIcoTest() {
    Currency currency = new Currency(76003872, "Euro");
    assertEquals(currency.getIco(),76003872 );
  }

  /**
   * tests getter for name of the currency
   */
  @Test
  void getNameTest() {
    Currency currency = new Currency(76003872, "Euro");
    assertEquals(currency.getName(),"Euro" );
  }

  /**
   * tests equals method
   */
  @Test
  void testEqualsTest() {
    Currency currency1 = new Currency(76003872, "Euro");
    Currency currency2 = new Currency(76003872, "Euro");
    assertEquals(currency1,currency2 );
  }

  /**
   * tests hashcode generation
   */
  @Test
  void testHashCodeTest() {
    Currency currency1 = new Currency(75588872, "Dollar");
    Currency currency2 = new Currency(76003872, "Euro");
    assertNotEquals(currency2.hashCode(), currency1.hashCode());
  }

  /**
   * tests string representation of the object
   */
  @Test
  void testToStringTest() {
    Currency currency = new Currency(75588872, "Dollar");
    assertEquals(currency.toString(),"Ico number is: 75588872, name of the currency is: Dollar");
  }
}