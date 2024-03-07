package commons;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Entity
public class Currency {

  @Id
  protected int iso;
  protected String name;
  protected double eurConversion;

  /**
   * Constructor.
   * @param iso iso number of a currency
   * @param name name of a currency
   */
  public Currency(int iso, String name) {
    this.iso = iso;
    this.name = name;
    try {
      eurConversion = fetchConversion();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  /**
   * empty constructor (to solve the error given on the class).
   */
  public Currency() {

  }

  /**
   * Getter for ico number.
   * @return ico
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
    return "Ico number is: " + iso +
            ", name of the currency is: " + name;
  }

  /**
   * Fetches the currencyConversion from eur to the different specified currency.
   * @return returns the conversion rate
   * @throws IOException throws ioException
   */
  private double fetchConversion() throws IOException {
    URL url = new URL("https://v6.exchangerate-api.com/v6/874a6a98cff76d00444b486f/pair/EUR/" + "USD");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    //?
    con.setDoOutput(true);
    con.setConnectTimeout(10000);
    con.setReadTimeout(10000);

    int status = con.getResponseCode();
    System.out.println("Currency GET response code: " + status);

    //if response code is 200
    double result = 0;
    if (status == HttpURLConnection.HTTP_OK){
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();

      while ((inputLine = in.readLine()) != null){
        response.append(inputLine);
      }
      in.close();

      final JsonNode node = new ObjectMapper().readTree(response.toString());

      System.out.println("Conversion rate: " + node.get("conversion_rate"));
      result = Double.parseDouble(node.get("conversion_rate").toString());
    }
    return result;
  }

  /**
   * getter for the eurConversion rate.
   * @return returns the conversion rate
   */
  public double getEurConversion(){
    return this.eurConversion;
  }

  /**
   * Setter for the conversion rate. Sets the rate using an external api.
   */
  public void updateConversion(){
    try {
      this.eurConversion = fetchConversion();
    } catch (IOException e) {
      System.out.println("Failed to update conversion rate");
    }
  }

  /**
   * Setter for the currency name.
   * @param name the new currency name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Setter for the iso.
   * @param iso new iso
   */
  public void setIso(int iso) {
    this.iso = iso;
  }
}
