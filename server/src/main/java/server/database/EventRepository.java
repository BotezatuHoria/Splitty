package server.database;

import commons.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {
    /**
     * This is an example of a query and how to write it.
     * @param id id you want to remove
     */
    @Modifying
    @Query("delete from Event e where e.id = ?1")
    void deleteById(long id);
}
