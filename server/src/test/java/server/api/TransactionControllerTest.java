package server.api;

import commons.Transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


class TransactionControllerTest {

    private TestTransactionRepository db;
    private TransactionController sut;
    @BeforeEach
    public void setup () {
        db = new TestTransactionRepository();
        sut = new TransactionController(db);
    }
    @Test
    public void getTransactionNullTest() {
        var actual = sut.getById(-14);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }
    @Test
    public void deleteTransactionNullTest() {
        var actual = sut.deleteById(-1);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void cannotAddNullTransaction() {
        Transaction test = null;
        var actual = sut.add(test);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }



    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void add() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void updateNameById() {
    }

    @Test
    void updateParticipantsById() {
    }

    @Test
    void updateCreatorById() {
    }
}