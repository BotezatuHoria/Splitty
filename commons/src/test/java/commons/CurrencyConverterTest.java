package commons;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Test;
import org.apache.commons.lang3.tuple.Pair;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyConverterTest {
  /**
   * tests converting from one currency to another.
   */
  @Test
  void convert1() {
    Currency currency1 = new Currency(67890, "Rubbles");
    Currency currency2 = new Currency(56800, "Euro ");
    CurrencyConverter cc = new CurrencyConverter();
    cc.addCurrencyConversionRate(currency1, currency2, 0.01);
    assertEquals(cc.convert(currency1, currency2, 500), 5);
  }
  /**
   * tests converting from one currency to another.
   */
  @Test
  void convert2() {
    Currency currency1 = new Currency(67890, "Rubbles");
    Currency currency2 = new Currency(56800, "Euro ");
    CurrencyConverter cc = new CurrencyConverter();
    cc.addCurrencyConversionRate(currency1, currency2, 0.01);
    assertEquals(cc.convert(currency2, currency1, 5), 500);
  }
  /**
   * test for correct addition of conversion rates.
   */
  @Test
  void addCurrencyConversionRate() {
    Currency currency1 = new Currency(67890, "Rubbles");
    Currency currency2 = new Currency(56800, "Euro ");
    CurrencyConverter cc = new CurrencyConverter();
    cc.addCurrencyConversionRate(currency1, currency2, 0.01);
    assertEquals(cc.convert(currency1, currency2, 500), 5);
  }

  @Test
  void currencyConverterThrows() {
    Currency currency1 = new Currency(67890, "Rubbles");
    Currency currency2 = new Currency(56800, "Euro ");
    CurrencyConverter cc = new CurrencyConverter();

    assertThrows(IllegalArgumentException.class, () -> cc.convert(currency1, currency2, 10.0));
  }

  @Test
  void currencyConversionConstructor() {
    Currency currency1 = new Currency(67890, "Rubbles");
    Currency currency2 = new Currency(56800, "Euro ");
    HashMap<Pair<Currency, Currency>, Double> currencyConversionRates = new HashMap<>();
    currencyConversionRates.put(new ImmutablePair<>(currency1, currency2), 10.0);
    currencyConversionRates.put(new ImmutablePair<>(currency2, currency1), 0.1);

    CurrencyConverter cc1 = new CurrencyConverter(currencyConversionRates);
    CurrencyConverter cc2 = new CurrencyConverter();
    cc2.addCurrencyConversionRate(currency1, currency2, 10.0);

    assertEquals(cc1.getCurrencyRates(), cc2.getCurrencyRates());
  }

  /**
   * test for correct getting of conversion rates.
   */
  @Test
  void getCurrencyRates() {
    Currency currency1 = new Currency(69420, "Dabloons");
    Currency currency2 = new Currency(56800, "Euro ");
    CurrencyConverter cc = new CurrencyConverter();
    Pair<Currency, Currency> pair = Pair.of(currency1, currency2);
    Pair<Currency, Currency> pair1 = Pair.of(currency2, currency1);
    HashMap<Pair<Currency, Currency>, Double> map = new HashMap<>();
    map.put(pair, 3.22);
    map.put(pair1, 100.0);
    cc.setCurrencyConversionRate(map);
    assertEquals(map, cc.getCurrencyRates());
  }
  /**
   * test for setting conversion rates.
   */
  @Test
  void setCurrencyConversionRate() {
    Currency currency1 = new Currency(67890, "Rubbles");
    Currency currency2 = new Currency(56800, "Euro ");
    CurrencyConverter cc = new CurrencyConverter();
    Pair<Currency, Currency> pair = Pair.of(currency1, currency2);
    Pair<Currency, Currency> pair1 = Pair.of(currency2, currency1);
    HashMap<Pair<Currency, Currency>, Double> map = new HashMap<>();
    map.put(pair, 0.01);
    map.put(pair1, 100.0);
    cc.setCurrencyConversionRate(map);
    assertEquals(map, cc.getCurrencyRates());

  }

  /**
   * test for correct removal of conversion rates.
   */
  @Test
  void removeCurrencyConversionRate() {
    Currency currency1 = new Currency(67890, "Rubbles");
    Currency currency2 = new Currency(56800, "Euro ");
    CurrencyConverter cc = new CurrencyConverter();
    Pair<Currency, Currency> pair = Pair.of(currency1, currency2);
    Pair<Currency, Currency> pair1 = Pair.of(currency2, currency1);
    HashMap<Pair<Currency, Currency>, Double> map = new HashMap<>();
    map.put(pair, 0.01);
    map.put(pair1, 100.0);
    cc.setCurrencyConversionRate(map);
    cc.removeCurrencyConversionRate(currency1,currency2);
    assertTrue(cc.getCurrencyRates().isEmpty());
  }
  /**
   * test for correct replacement of conversion rates.
   */
  @Test
  void testSetCurrencyConversionRate() {
    Currency currency1 = new Currency(67890, "Rubbles");
    Currency currency2 = new Currency(56800, "Euro ");
    CurrencyConverter cc = new CurrencyConverter();
    Pair<Currency, Currency> pair = Pair.of(currency1, currency2);
    Pair<Currency, Currency> pair1 = Pair.of(currency2, currency1);
    HashMap<Pair<Currency, Currency>, Double> map = new HashMap<>();
    map.put(pair, 0.01);
    map.put(pair1, 100.0);
    cc.addCurrencyConversionRate(currency1, currency2, 500);
    cc.setCurrencyConversionRate(currency1, currency2, 0.01);
    assertEquals(map, cc.getCurrencyRates());

  }
}