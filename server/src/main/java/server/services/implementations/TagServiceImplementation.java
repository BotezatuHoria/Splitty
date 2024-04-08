package server.services.implementations;

import commons.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.database.TagRepository;
import server.services.interfaces.TagService;

import java.util.List;

@Service
public class TagServiceImplementation implements TagService {

    private final TagRepository repo;

    public TagServiceImplementation(TagRepository repo) {
        this.repo = repo;
    }

    @Override
    public ResponseEntity<List<Tag>> getAll() {
        List<Tag> tagList = repo.findAll();
        if (tagList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(repo.findAll());
    }

    @Override
    public ResponseEntity<Tag> getById(int id) {
        var res = repo.findById(id);
        return res.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Tag> updateById(int id, Tag newTag) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return repo.findById(id)
                .map(existingTag -> {
                    existingTag.setTitle(newTag.getTitle());
                    return ResponseEntity.ok(repo.save(existingTag));
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Tag> add(Tag tag) {
        if (tag == null || tag.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Tag saved = repo.save(tag);
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<Tag> deleteById(int id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        Tag tag = repo.findById(id).get();
        ResponseEntity<Tag> response = ResponseEntity.ok(tag);
        repo.deleteById(id);
        return response;
    }
}
