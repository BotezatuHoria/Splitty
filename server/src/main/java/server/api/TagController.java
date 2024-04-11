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

    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<Tag>> getAll() {
        return tagServiceImplementation.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getById(@PathVariable("id") int id) {
        return tagServiceImplementation.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateById(@PathVariable("id") int id, @RequestBody Tag tag) {
        return tagServiceImplementation.updateById(id, tag);
    }

    @PostMapping(path = {"", "/"})
    public ResponseEntity<Tag> add(Tag tag) {
        return tagServiceImplementation.add(tag);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Tag> deleteById(int id) {
        return tagServiceImplementation.deleteById(id);
    }
}
