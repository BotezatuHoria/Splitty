package server.api;

import commons.Person;
import commons.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.TransactionRepository;
import server.services.implementations.TransactionServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionServiceImplementation tsi;

    /**
     * Constructor for the TransactionController.
     * @param tsi The TransactionServiceImplementation
     */
    public TransactionController(TransactionServiceImplementation tsi){
        this.tsi = tsi;
    }

    /**
     * This endpoint lists all the saved transactions.
     * @return returns a list with all the transactions
     */
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<Transaction>> getAll(){
        return tsi.getAll();
    }

    /**
     * This endpoint returns the transaction with the specified id if it exists.
     * @param id the id of the transaction to search for
     * @return returns the transaction with the specified id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable("id") int id){
        return tsi.getById(id);
    }

    /**
     * Adds a new transaction to the database.
     * @param transaction the transaction to add
     * @return returns the added transaction
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Transaction> add(@RequestBody Transaction transaction) {
        return tsi.add(transaction);
    }

    /**
     * Deletes a transaction from the database with a specified id.
     * @param id the id of the transaction to delete
     * @return returns the deleted transaction
     */
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Transaction> deleteById(@PathVariable("id") int id) {
        return tsi.deleteById(id);
    }

    /**
     * Updates the name of a transaction in the database with a specific id.
     * @param id the id of the transaction to update
     * @param name the changed name of the to update transaction.
     * @return returns the updated transaction
     */
    @PutMapping(path = {"/{id}/name"})
    public ResponseEntity<Transaction> updateNameById(@PathVariable("id") int id, @RequestBody String name){
       return tsi.updateNameById(id, name);
    }

    /**
     * Updates the set of participants of a transaction in the database with a specific id.
     * @param id the id of the transaction to update
     * @param participants the changed set of participants of the to update transaction.
     * @return returns the updated transaction
     */
    @PutMapping(path = {"/{id}/participants"})
    public ResponseEntity<Transaction> updateParticipantsById(@PathVariable("id") int id,
                                                              @RequestBody List<Person> participants){
        return tsi.updateParticipantsById(id, participants);
    }

    /**
     * Changes all the values of the transaction.
     * @param id id of a transaction
     * @param newData changed transaction.
     * @return badRequest/ ok + updated Transaction
     */
    @PutMapping(path = {"/{id}"})
    public ResponseEntity<Transaction> updateById(@PathVariable("id") int id, @RequestBody Transaction newData) {
        return tsi.updateById(id, newData);
    }


    /**
     * Changes money val of the transaction in DB.
     * @param id id of a transaction
     * @param money new money val we want to change old transaction money val to
     * @return badRequest/ ok + updated Transaction
     */
    @PutMapping(path = {"/{id}/money"})
    public ResponseEntity<Transaction> updateMoneyById(@PathVariable("id") int id, @RequestBody double money){
       return tsi.updateMoneyById(id, money);
    }

}

