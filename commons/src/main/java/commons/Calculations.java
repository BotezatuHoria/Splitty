package commons;

import java.util.HashMap;

public class Calculations {
  //stores rates of different currencies to eur
  protected HashMap<String, Integer> currencyToEurRate;

  /**
   * Constructor.
   * @param currencyToEurRate hashmap which stores rates of different currencies to eur
   */
  public Calculations(HashMap<String, Integer> currencyToEurRate){
    this.currencyToEurRate = currencyToEurRate;
  }
  /**
   * Converts any currency to eur.
   * @param money how much currency you have
   * @param currency currency you want to convert to eur
   * @return corresponding number of eur
   */
  protected int eurCurrency(int money, String currency){
    if(!currencyToEurRate.containsKey(currency)) {
      throw new IllegalArgumentException("currency is not supported");
    }
    if(money == 0){
      return 0;
    }

    int multiplier = currencyToEurRate.get(currency);
    return money * multiplier;
  }

  /**
   * Getter for hashmap with currency rates to eur.
   * @return hashmap with currency rates to eur
   */
  public HashMap<String, Integer> getCurrencyToEurRate() {
    return currencyToEurRate;
  }

}
