package server.services.interfaces;

import commons.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface TagService {
    public ResponseEntity<List<Tag>> getAll();
    public ResponseEntity<Tag> getById(int id);
    public ResponseEntity<Tag> updateById(int id, Tag tag);
    public ResponseEntity<Tag> add(Tag tag);
    public ResponseEntity<Tag> deleteById(int id);
}
