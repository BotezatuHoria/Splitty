package server.api;

import commons.Currency;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.CurrencyRepository;

import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyRepository repo;

    /**
     * Constructor for the currency controller.
     * @param repo repository for the currency
     */
    public CurrencyController(CurrencyRepository repo){
        this.repo = repo;
    }

    /**
     * This endpoint lists all the currency's.
     * @return returns a list with all the currency's
     */
    @GetMapping(path = {"", "/"})
    public List<Currency> getAll(){
        return repo.findAll();
    }

    /**
     * This endpoint gets a currency from a given ico.
     * @param ico the ico of the currency
     * @return returns the currency
     */
    @GetMapping("/{ico}")
    public ResponseEntity<Currency> getById(@PathVariable("ico") int ico){
        if (ico < 0 || !repo.existsById(ico)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(ico).get());
    }

    /**
     * This endpoint adds a new currency to the repository.
     * @param currency the new currency to add
     * @return returns the added currency
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Currency> add(@RequestBody Currency currency) {

        if (currency.getIso() == 0 || isNullOrEmpty(currency.getName())) {
            return ResponseEntity.badRequest().build();
        }

        Currency saved = repo.save(currency);
        return ResponseEntity.ok(saved);
    }

    /**
     * This endpoint deletes a currency given an ico.
     * @param ico
     * @return
     */
    @DeleteMapping(path = {"/{ico}"})
    public ResponseEntity<Currency> deleteById(@PathVariable("ico") int ico) {
        if (ico < 0 || !repo.existsById(ico)) {
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<commons.Currency> response =  ResponseEntity.ok(repo.findById(ico).get());
        repo.deleteById(ico);
        return response;
    }
    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }



}
