package server.api;

import commons.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.TransactionRepository;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionRepository repo;

    /**
     * Constructor for the TransactionController.
     * @param repo The TransactionRepository
     */
    public TransactionController(TransactionRepository repo){
        this.repo = repo;
    }

    /**
     * This endpoint lists all the saved transactions.
     * @return returns a list with all the transactions
     */
    @GetMapping(path = {"", "/"})
    public List<Transaction> getAll(){
        return repo.findAll();
    }

    /**
     * This endpoint returns the transaction with the specified id if it exists.
     * @param id the id of the transaction to search for
     * @return returns the transaction with the specified id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable("id") int id){
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    /**
     * Adds a new transaction to the database.
     * @param transaction the transaction to add
     * @return returns the added transaction
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Transaction> add(@RequestBody Transaction transaction) {

        if (transaction.getId() < 0 || isNullOrEmpty(transaction.getName())|| transaction.getDate() == null ||
                transaction.getMoney() == 0 || transaction.getCurrency() == 0 || transaction.getCreator() == null || transaction.getParticipants().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Transaction saved = repo.save(transaction);
        return ResponseEntity.ok(saved);
    }

    /**
     * Checks strings to be either null or empty.
     * @param s string to check
     * @return returns true if the string is empty or null
     */
    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Deletes a transaction from the database with a specified id.
     * @param id the id of the transaction to delete
     * @return returns the deleted transaction
     */
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Transaction> deleteById(@PathVariable("id") int id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<commons.Transaction> response =  ResponseEntity.ok(repo.findById(id).get());
        repo.deleteById(id);
        return response;
    }


}
