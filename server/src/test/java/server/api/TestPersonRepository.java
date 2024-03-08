package server.api;

import commons.Person;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.PersonRepository;

import javax.swing.text.html.Option;
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
        public void deleteAllByIdInBatch(Iterable<Integer> integers) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public Person getOne(Integer integer) {
                return null;
        }

        @Override
        public Person getById(Integer integer) {
                return null;
        }

        @Override
        public Person getReferenceById(Integer integer) {
                return null;
        }

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

        private Optional<Person> find(Integer id) {
                Iterator<Person> iterator = persons.iterator();
                while (iterator.hasNext()) {
                        Person person = iterator.next();
                        if (person.getId() == id) {
                                return Optional.of(person);
                        }
                }
                return Optional.empty();
        }

        @Override
        public List<Person> findAll() {
                return persons;
        }

        @Override
        public List<Person> findAllById(Iterable<Integer> integers) {
                return null;
        }

        @Override
        public <S extends Person> S save(S entity) {
                Optional<Person> p = findById(entity.getId());
                if (p.isPresent()) {
                        Person person = p.get();
                        person.setDebt(entity.getDebt());
                        person.setEmail(entity.getEmail());
                        person.setIban(entity.getIban());
                        person.setEvent(entity.getEvent());
                        person.setFirstName(entity.getFirstName());
                        person.setLastName(entity.getLastName());
                        person.setTransactions(entity.getTransactions());
                        person.setCreatedTransactions(entity.getCreatedTransactions());
                        return (S) person;
                } else {
                        persons.add(entity);
                        return entity;
                }
        }

        @Override
        public Optional<Person> findById(Integer id) {
                return find(id);
        }

        @Override
        public boolean existsById(Integer i) {
                Iterator<Person> iterator = persons.iterator();
                while (iterator.hasNext()) {
                        Person person = iterator.next();
                        if (person.getId() == i) {
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
        public void deleteById(Integer id) {
                Iterator<Person> iterator = persons.iterator();
                while (iterator.hasNext()) {
                        Person person = iterator.next();
                        if (person.getId() == id) {
                                iterator.remove();
                                return;
                        }
                }
        }

        @Override
        public void delete(Person entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends Integer> integers) {

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
