package server.api;

import commons.Person;
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
        if(!repo.findById(id).isPresent()){
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
        if (transaction == null || isNullOrEmpty(transaction.getName()) ||
                transaction.getCreator() == null || transaction.getParticipants().isEmpty() ||
                transaction.getId() < 0 || transaction.getDate() == null ||
                transaction.getMoney() == 0 || transaction.getCurrency() == 0) {
            return ResponseEntity.badRequest().build();
        }

        try {
            repo.save(transaction); // returns null for whatever reason, should look into it
            Transaction saved = transaction;
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            // Handle any database-related exceptions (e.g., unique constraint violation)
            return  ResponseEntity.badRequest().build();
        }
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
        if(!repo.findById(id).isPresent()){
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<commons.Transaction> response =  ResponseEntity.ok(repo.findById(id).get());
        repo.deleteById(id);
        return response;
    }

    /**
     * Updates the name of a transaction in the database with a specific id.
     * @param id the id of the transaction to update
     * @param name the changed name of the to update transaction.
     * @return returns the updated transaction
     */
    @PutMapping(path = {"/{id}/name"})
    public ResponseEntity<Transaction> updateNameById(@PathVariable("id") int id, @RequestBody String name){
        if (id < 0 ||
                !repo.existsById(id) || isNullOrEmpty(name)) {
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

    /**
     * Updates the set of participants of a transaction in the database with a specific id.
     * @param id the id of the transaction to update
     * @param participants the changed set of participants of the to update transaction.
     * @return returns the updated transaction
     */
    @PutMapping(path = {"/{id}/participants"})
    public ResponseEntity<Transaction> updateParticipantsById(@PathVariable("id") int id, @RequestBody List<Person> participants){
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

    /**
     * Changes all the values of the transaction.
     * @param id id of a transaction
     * @param newData changed transaction.
     * @return badRequest/ ok + updated Transaction
     */
    @PutMapping(path = {"/{id}"})
    public ResponseEntity<Transaction> updateById(@PathVariable("id") int id, @RequestBody Transaction newData) {
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


    /**
     * Changes money val of the transaction in DB.
     * @param id id of a transaction
     * @param money new money val we want to change old transaction money val to
     * @return badRequest/ ok + updated Transaction
     */
    @PutMapping(path = {"/{id}/money"})
    public ResponseEntity<Transaction> updateMoneyById(@PathVariable("id") int id, @RequestBody double money){
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

}

