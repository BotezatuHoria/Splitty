package commons;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;

public class CurrencyConverter {
  //stores rates of different currency conversions
  protected HashMap<Pair<Currency, Currency>, Double> currencyConversionRates;

  /**
   * Creates empty currency Calculator.
   * NOTE: NEEDS IMPLEMENTATION TO FETCH CURRENCIES FROM DATABASE AND FILLS THE CONVERTOR MAP
   */
  public CurrencyConverter() {
    this.currencyConversionRates = new HashMap<Pair<Currency, Currency>, Double>();
  }

  /**
   * Creates currency converter with custom values.
   * @param currencyConversionRates hashmap which stores rates of different currencies
   */
  public CurrencyConverter(HashMap<Pair<Currency, Currency>, Double> currencyConversionRates){
    this.currencyConversionRates = currencyConversionRates;
  }

  /**
   * Converts currencies.
   * @param amount how much currency you have
   * @param currencyFrom currency you want to convert from
   * @param currencyTo currency you want to convert to
   * @return corresponding amount of currencyTo
   */
  protected double convert(Currency currencyFrom, Currency currencyTo, double amount){
    if(!currencyConversionRates.containsKey(Pair.of(currencyFrom, currencyTo))) {
      throw new IllegalArgumentException("Currency conversion not supported");
    }

    double multiplier = currencyConversionRates.get(Pair.of(currencyFrom, currencyTo));
    return amount * multiplier;
  }

  /**
   * Getter for hashmap with currency rates.
   * @return hashmap with currency rates
   */
  public HashMap<Pair<Currency, Currency>, Double> getCurrencyRates() {
    return currencyConversionRates;
  }

  /**
   * Imports a new currency to rate map.
   * @param currencyConversionRates the map that
   */
  public void setCurrencyConversionRate(HashMap<Pair<Currency, Currency>, Double> currencyConversionRates) {
    this.currencyConversionRates = currencyConversionRates;
  }

  /**
   * Adds a new currency to rate key value pair to the map.
   * @param currencyFrom the Currency to convert from
   * @param currencyTo the Currency to convert to
   * @param rate the rate of conversion
   */
  public void addCurrencyConversionRate(Currency currencyFrom, Currency currencyTo, double rate) {
    this.currencyConversionRates.put(Pair.of(currencyFrom, currencyTo), rate);
    this.currencyConversionRates.put(Pair.of(currencyTo, currencyFrom), 1.0/rate);
  }

  /**
   * Removes currency rate from map.
   * @param currencyFrom the Currency to convert from
   * @param currencyTo the Currency to convert to
   */
  public void removeCurrencyConversionRate(Currency currencyFrom, Currency currencyTo) {
    this.currencyConversionRates.remove(Pair.of(currencyFrom, currencyTo));
    this.currencyConversionRates.remove(Pair.of(currencyTo, currencyFrom));
  }

  /**
   * Sets new rate of conversion for currency pair.
   * @param currencyFrom the Currency to convert from
   * @param currencyTo the Currency to convert to
   * @param rate the new rate of conversion
   */
  public void setCurrencyConversionRate(Currency currencyFrom, Currency currencyTo, double rate) {
    this.currencyConversionRates.replace(Pair.of(currencyFrom, currencyTo), rate);
    this.currencyConversionRates.replace(Pair.of(currencyTo, currencyFrom), 1.0/rate);
  }
}
