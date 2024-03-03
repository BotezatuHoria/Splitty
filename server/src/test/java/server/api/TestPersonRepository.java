package server.api;

import commons.Person;
import commons.Quote;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.PersonRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestPersonRepository implements PersonRepository {
        public final List<Person> persons = new ArrayList<>();

        @Override
        public void flush() {

        }

        @Override
        public <S extends Person> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public <S extends Person> List<S> saveAllAndFlush(Iterable<S> entities) {
            return null;
        }

        @Override
        public void deleteAllInBatch(Iterable<Person> entities) {

        }

        @Override
        public void deleteAllByIdInBatch(Iterable<String> strings) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public Person getOne(String s) {
            return null;
        }

        @Override
        public Person getById(String s) {
                return find(s).get();
        }

        @Override
        public Person getReferenceById(String s) {
                return find(s).get();        }

        @Override
        public <S extends Person> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends Person> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public <S extends Person> List<S> saveAll(Iterable<S> entities) {
            return null;
        }

        private Optional<Person> find(String email) {
                Iterator<Person> iterator = persons.iterator();
                while (iterator.hasNext()) {
                        Person person = iterator.next();
                        if (person.getEmail().equals(email)) {
                                return Optional.of(person);
                        }
                }
                return null;
        }

        @Override
        public List<Person> findAll() {
                return persons;
        }

        @Override
        public List<Person> findAllById(Iterable<String> strings) {
            return null;
        }

        @Override
        public <S extends Person> S save(S entity) {
                persons.add(entity);
                return entity;
        }

        @Override
        public Optional<Person> findById(String s) {
                return Optional.of(find(s).get());
        }

        @Override
        public boolean existsById(String s) {
                Iterator<Person> iterator = persons.iterator();
                while (iterator.hasNext()) {
                        Person person = iterator.next();
                        if (person.getEmail().equals(s)) {
                                return true;
                        }
                }
                return false;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(String email) {
                Iterator<Person> iterator = persons.iterator();
                while (iterator.hasNext()) {
                        Person person = iterator.next();
                        if (person.getEmail().equals(email)) {
                                iterator.remove();
                                return;
                        }
                }
        }

        @Override
        public void delete(Person entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends String> strings) {

        }

        @Override
        public void deleteAll(Iterable<? extends Person> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public List<Person> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<Person> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Person> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends Person> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Person> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends Person> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends Person, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }
}
