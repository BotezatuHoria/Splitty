package server.services.implementations;

import commons.Person;
import commons.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.database.TransactionRepository;
import server.services.interfaces.TransactionService;

import java.util.List;

@Service
public class TransactionServiceImplementation implements TransactionService {

    TransactionRepository repo;
    public TransactionServiceImplementation(TransactionRepository repo){
        this.repo = repo;
    }
    @Override
    public ResponseEntity<List<Transaction>> getAll() {
        List<Transaction> transactions = repo.findAll();
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(transactions);
        }
    }

    @Override
    public ResponseEntity<Transaction> getById(int id) {
        var res = repo.findById(id);
        return res.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Transaction> add(Transaction transaction) {
        if (transaction == null || isNullOrEmpty(transaction.getName()) ||
                transaction.getCreator() == null || transaction.getParticipants().isEmpty() ||
                transaction.getId() < 0 || transaction.getDate() == null ||
                transaction.getMoney() == 0 || transaction.getCurrency() == 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            repo.save(transaction); // returns null for whatever reason, should look into it
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            // Handle any database-related exceptions (e.g., unique constraint violation)
            return  ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Transaction> deleteById(int id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        if(!repo.findById(id).isPresent()){
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<commons.Transaction> response =  ResponseEntity.ok(repo.findById(id).get());
        repo.deleteById(id);
        return response;
    }

    @Override
    public ResponseEntity<Transaction> updateNameById(int id, String name) {
        if (id < 0 || !repo.existsById(id) || isNullOrEmpty(name)) {
            return ResponseEntity.badRequest().build();
        }
        Transaction transaction = getById(id).getBody();
        if (transaction == null) {
            return ResponseEntity.badRequest().build();
        }
        transaction.setName(name);
        repo.save(transaction);
        return ResponseEntity.ok(transaction);
    }

    @Override
    public ResponseEntity<Transaction> updateParticipantsById(int id, List<Person> participants) {
        if (id < 0 || !repo.existsById(id) || participants == null) {
            return ResponseEntity.badRequest().build();
        }
        Transaction transaction = getById(id).getBody();
        if (transaction == null) {
            return ResponseEntity.badRequest().build();
        }
        transaction.setParticipants(participants);
        repo.save(transaction);
        return ResponseEntity.ok(transaction);
    }

    @Override
    public ResponseEntity<Transaction> updateById(int id, Transaction newData) {
        if (id < 0 || !repo.existsById(id) || newData == null || isNullOrEmpty(newData.getName()) ||
                newData.getCreator() == null || newData.getParticipants().isEmpty() ||
                newData.getId() < 0 || newData.getDate() == null ||
                newData.getMoney() == 0 || newData.getCurrency() == 0) {
            return ResponseEntity.badRequest().build();
        }
        return repo.findById(id)
                .map(existingTransaction -> {
                    existingTransaction.setName(newData.getName());
                    existingTransaction.setDate(newData.getDate());
                    existingTransaction.setMoney(newData.getMoney());
                    existingTransaction.setCurrency(newData.getCurrency());
                    existingTransaction.setParticipants(newData.getParticipants());
                    existingTransaction.setExpenseType(newData.getExpenseType());
                    return ResponseEntity.ok(repo.save(existingTransaction));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Transaction> updateMoneyById(int id, double money) {
        if (id < 0 || !repo.existsById(id) || money == 0.0) {
            return ResponseEntity.badRequest().build();
        }
        Transaction transaction = getById(id).getBody();

        //check if transaction is null, if it is return bad request
        if (transaction == null) {
            return ResponseEntity.badRequest().build();
        }
        transaction.setMoney(money);
        repo.save(transaction);
        return ResponseEntity.ok(transaction);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
