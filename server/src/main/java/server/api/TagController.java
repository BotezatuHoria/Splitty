package server.api;

import commons.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.implementations.TagServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    private final TagServiceImplementation tagServiceImplementation;

    public TagController(TagServiceImplementation tagServiceImplementation) {
        this.tagServiceImplementation = tagServiceImplementation;
    }

    /**
     * gets the list of all tags.
     * @return list of tags.
     */
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<Tag>> getAll() {
        return tagServiceImplementation.getAll();
    }

    /**
     * gets the tags of certain id.
     * @param id event id.
     * @return list of tags of that id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getById(@PathVariable("id") int id) {
        return tagServiceImplementation.getById(id);
    }

    /**
     * updated tag.
     * @param id tag id.
     * @param tag tag entity.
     * @return tag entity that is updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateById(@PathVariable("id") int id, @RequestBody Tag tag) {
        return tagServiceImplementation.updateById(id, tag);
    }

    /**
     * new tag made.
     * @param tag the tag thats  made.
     * @return the tag.
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Tag> add(Tag tag) {
        return tagServiceImplementation.add(tag);
    }

    /**
     * deletes the tags by id.
     * @param id tag id.
     * @return tag that is deleted
     */
    @DeleteMapping("/id")
    public ResponseEntity<Tag> deleteById(int id) {
        return tagServiceImplementation.deleteById(id);
    }
}
