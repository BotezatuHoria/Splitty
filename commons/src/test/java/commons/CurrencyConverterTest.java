package commons;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyConverterTest {

  /**
   * test for correct getting of conversion rates.
   */
  @Test
  void getCurrencyRates() {
    Currency currency1 = new Currency(69420, "Dabloons");
    Currency currency2 = new Currency(56800, "Euro ");
    CurrencyConverter cc = new CurrencyConverter();
    Pair<Currency, Currency> pair = new Pair<>(currency1, currency2);
    Pair<Currency, Currency> pair1 = new Pair<>(currency2, currency1);
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
    Pair<Currency, Currency> pair = new Pair<>(currency1, currency2);
    Pair<Currency, Currency> pair1 = new Pair<>(currency2, currency1);
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
    Pair<Currency, Currency> pair = new Pair<>(currency1, currency2);
    Pair<Currency, Currency> pair1 = new Pair<>(currency2, currency1);
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
    Pair<Currency, Currency> pair = new Pair<>(currency1, currency2);
    Pair<Currency, Currency> pair1 = new Pair<>(currency2, currency1);
    HashMap<Pair<Currency, Currency>, Double> map = new HashMap<>();
    map.put(pair, 0.01);
    map.put(pair1, 100.0);
    cc.addCurrencyConversionRate(currency1, currency2, 500);
    cc.setCurrencyConversionRate(currency1, currency2, 0.01);
    assertEquals(map, cc.getCurrencyRates());

  }
}