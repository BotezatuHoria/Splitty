package server.services.interfaces;

import commons.Person;
import commons.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {
    public ResponseEntity<List<Transaction>> getAll();
    public ResponseEntity<Transaction> getById(int id);
    public ResponseEntity<Transaction> add(Transaction transaction);
    public ResponseEntity<Transaction> deleteById(int id);
    public ResponseEntity<Transaction> updateNameById(int id, String name);
    public ResponseEntity<Transaction> updateParticipantsById(int id, List<Person> participants);
    public ResponseEntity<Transaction> updateById(int id, Transaction newData);
    public ResponseEntity<Transaction> updateMoneyById(int id, double money);
}
