package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {
  /**
   * tests getter for ico number.
   */
  @Test
  void getIcoTest() {
    Currency currency = new Currency(76003872, "Euro");
    assertEquals(currency.getIso(),76003872 );
  }

  /**
   * tests getter for name of the currency.
   */
  @Test
  void getNameTest() {
    Currency currency = new Currency(76003872, "Euro");
    assertEquals(currency.getName(),"Euro" );
  }

  /**
   * tests equals method.
   */
  @Test
  void testEqualsTest() {
    Currency currency1 = new Currency(76003872, "Euro");
    Currency currency2 = new Currency(76003872, "Euro");
    assertEquals(currency1,currency2 );
  }

  /**
   * tests equals method on an unequal ico.
   */
  @Test
  void testEqualsTestDifferentIco() {
    Currency currency1 = new Currency(76003872, "Euro");
    Currency currency2 = new Currency(86003872, "Euro");
    assertNotEquals(currency1,currency2 );
  }

  /**
   * tests equals method on an unequal name.
   */
  @Test
  void testEqualsTestDifferentName() {
    Currency currency1 = new Currency(76003872, "Euro");
    Currency currency2 = new Currency(76003872, "Yen");
    assertNotEquals(currency1,currency2 );
  }

  /**
   * tests equals method on a null input.
   */
  @Test
  void testEqualsTestNull() {
    Currency currency1 = new Currency(76003872, "Euro");
    Currency currency2 = new Currency(0, null);
    assertNotEquals(currency1,currency2 );
    assertNotEquals(currency1, null);
  }

  /**
   * tests hashcode generation.
   */
  @Test
  void testHashCodeTest() {
    Currency currency1 = new Currency(75588872, "Dollar");
    Currency currency2 = new Currency(75588872, "Dollar");
    assertEquals(currency1, currency2);
    assertEquals(currency2.hashCode(), currency1.hashCode());
  }

  /**
   * tests hashcode generation on non-equal objects.
   */
  @Test
  void testHashCodeTestNotEqual() {
    Currency currency1 = new Currency(75588872, "Dollar");
    Currency currency2 = new Currency(76003872, "Euro");
    assertNotEquals(currency1, currency2);
    assertNotEquals(currency2.hashCode(), currency1.hashCode());
  }

  /**
   * tests string representation of the object.
   */
  @Test
  void testToStringTest() {
    Currency currency = new Currency(75588872, "Dollar");
    assertEquals(currency.toString(),"Iso number is: 75588872, name of the currency is: Dollar");
  }

  @Test
  void emptyConstructorTest() {
    Currency currency = new Currency();
    assertNotEquals(currency, null);
  }

  @Test
  void sameObjectEquals() {
    Currency currency = new Currency(75588872, "Dollar");
    assertEquals(currency, currency);
  }

  @Test
  void setNameTest() {
    Currency currency = new Currency(75588872, "Dollar");
    currency.setName("Euro");
    assertEquals(currency.getName(), "Euro");
  }

  @Test
  void setIsoTest() {
    Currency currency = new Currency(75588872, "Dollar");
    currency.setIso(123);
    assertEquals(currency.getIso(), 123);
  }

}
