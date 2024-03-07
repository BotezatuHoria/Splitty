package server.api;

import commons.Transaction;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestTransactionRepository implements TransactionRepository {

  @Override
  public void flush() {

  }

  @Override
  public <S extends Transaction> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends Transaction> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Transaction> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Integer> integers) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Transaction getOne(Integer integer) {
    return null;
  }

  @Override
  public Transaction getById(Integer integer) {
    return null;
  }

  @Override
  public Transaction getReferenceById(Integer integer) {
    return null;
  }

  @Override
  public <S extends Transaction> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Transaction> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Transaction> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Transaction> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Transaction> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Transaction> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends Transaction, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public <S extends Transaction> S save(S entity) {
    return null;
  }

  @Override
  public <S extends Transaction> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<Transaction> findById(Integer integer) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Integer integer) {
    return false;
  }

  @Override
  public List<Transaction> findAll() {
    return null;
  }

  @Override
  public List<Transaction> findAllById(Iterable<Integer> integers) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(Integer integer) {

  }

  @Override
  public void delete(Transaction entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Integer> integers) {

  }

  @Override
  public void deleteAll(Iterable<? extends Transaction> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<Transaction> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Transaction> findAll(Pageable pageable) {
    return null;
  }
}
