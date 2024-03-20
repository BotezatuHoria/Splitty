package server.api;

import commons.Currency;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.CurrencyRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestCurrencyRepository implements CurrencyRepository {

    public final List<Currency> currencies = new ArrayList<>();
    @Override
    public void flush() {

    }

    @Override
    public <S extends Currency> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Currency> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Currency> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Currency getOne(Integer integer) {
        return null;
    }

    @Override
    public Currency getById(Integer integer) {
        return null;
    }

    @Override
    public Currency getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Currency> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Currency> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Currency> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Currency> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Currency> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Currency> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Currency, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Currency> S save(S entity) {
        Optional<Currency> c = findById((int) entity.getIso());
        if (c.isPresent()){
            Currency currency = c.get();
            currency.setIso(currency.getIso());
            currency.setName(currency.getName());
            return (S) currency;
        } else {
            currencies.add(entity);
            return entity;
        }

    }

    @Override
    public <S extends Currency> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Currency> findById(Integer integer) {
        Iterator<Currency> iterator = currencies.iterator();
        while (iterator.hasNext()) {
            Currency currency = iterator.next();
            if (currency.getIso() == integer) {
                return Optional.of(currency);
            }
        }
        return Optional.empty();
    }


    public boolean existsById(Integer integer) {
        Iterator<Currency> iterator = currencies.iterator();
        while (iterator.hasNext()) {
            Currency currency = iterator.next();
            if (currency.getIso() == integer) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Currency> findAll() {
        return currencies;
    }

    @Override
    public List<Currency> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {
        Currency delete = null;
        Iterator<Currency> iterator = currencies.iterator();
        while (iterator.hasNext()) {
            Currency currency = iterator.next();
            if (currency.getIso() == integer) {
                delete = currency;
            }
        }
        currencies.remove(delete);
    }

    @Override
    public void delete(Currency entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Currency> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Currency> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Currency> findAll(Pageable pageable) {
        return null;
    }
}
