package server.api;

import commons.Currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CurrencyControllerTest {

    private CurrencyController currencyController;
    private TestCurrencyRepository currencyRepository;


    @BeforeEach
    public void setup(){
        currencyRepository = new TestCurrencyRepository();
        currencyController = new CurrencyController(currencyRepository);
    }

    @Test
    void addTest(){
        Currency currency = new Currency(978, "EUR");
        var actual = currencyController.add(currency);
        assertEquals(actual.getBody(), currency);
    }

    @Test
    void addNullTest(){
        Currency nullCurrency = null;
        Currency wrongFields = new Currency(978, null);
        var actual1 = currencyController.add(nullCurrency);
        var actual2 = currencyController.add(wrongFields);
        assertEquals(BAD_REQUEST, actual1.getStatusCode());
        assertEquals(BAD_REQUEST, actual2.getStatusCode());
    }

    @Test
    void getByIso() {
        Currency currency = new Currency(978, "EUR");
        currencyController.add(currency);
        var actual = currencyController.getById(978);
        assertEquals(currency, actual.getBody());
    }

    @Test
    void invalidIdTest() {
        var actual1 = currencyController.getById(-1);
        var actual2 = currencyController.getById(5);
        assertEquals(BAD_REQUEST, actual1.getStatusCode());
        assertEquals(BAD_REQUEST, actual2.getStatusCode());
    }

    @Test
    void getAllTest() {
        Currency currency = new Currency(978, "EUR");
        currencyController.add(currency);
        var actual = currencyController.getAll();
        assertNotEquals(0, actual.size());
        assertTrue(actual.contains(currency));
    }

    @Test
    void updateByIdTest() {
        Currency currency = new Currency(978, "EUR");
        currencyController.add(currency);
        Currency eddited = new Currency(978, "USD");
        var actual = currencyController.updateById(eddited, 978);
        assertEquals(eddited, actual.getBody());
    }
    @Test
    void updateByIDInvalidTest() {
        Currency currency = new Currency(978, "EUR");
        currencyController.add(currency);
        Currency wrongIso = new Currency(-1, "USD");
        Currency wrongFields = new Currency(-1, null);
        var actual1 = currencyController.updateById(wrongIso, 1);
        var actual2 = currencyController.updateById(wrongFields, -1);
        var actual3 = currencyController.updateById(wrongIso, 2);
        assertEquals(BAD_REQUEST, actual1.getStatusCode());
        assertEquals(BAD_REQUEST, actual2.getStatusCode());
        assertEquals(BAD_REQUEST, actual3.getStatusCode());
    }
    @Test
    void deleteByIsoTest() {
        Currency currency = new Currency(978, "EUR");
        currencyController.add(currency);
        var actual = currencyController.deleteById(978);
        assertEquals(currency, actual.getBody());
        assertEquals(BAD_REQUEST, currencyController.getById(978).getStatusCode());
    }

    @Test
    void deleteByInvalidIsoTest() {
        assertEquals(BAD_REQUEST, currencyController.deleteById(46).getStatusCode());
    }
}
