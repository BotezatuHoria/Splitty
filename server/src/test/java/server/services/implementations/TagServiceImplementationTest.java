package server.services.implementations;

import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.TagRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TagServiceImplementationTest {
    @Mock
    private TagRepository repo;
    @Mock
    private TagServiceImplementation service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(TagRepository.class);
        service = new TagServiceImplementation(repo);
    }

    @Test
    void getAllEmpty() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Tag>> response = service.getAll();
        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void getAll() {
        Tag tag = new Tag("Test");
        when(repo.findAll()).thenReturn(Collections.singletonList(tag));
        ResponseEntity<List<Tag>> response = service.getAll();
        assertEquals(ResponseEntity.ok(Collections.singletonList(tag)), response);
    }

    @Test
    void getByIdNotFound() {
        when(repo.findById(1)).thenReturn(java.util.Optional.empty());
        ResponseEntity<Tag> response = service.getById(1);
        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void addNull() {
        ResponseEntity<Tag> response = service.add(null);
        assertEquals(ResponseEntity.badRequest().build(), response);
    }

    @Test
    void addInvalid() {
        Tag tag = null;
        ResponseEntity<Tag> response = service.add(tag);
        assertEquals(ResponseEntity.badRequest().build(), response);
    }

    @Test
    void addValidTag() {
        Tag tag = new Tag("Title");
        when(repo.save(tag)).thenReturn(tag);
        ResponseEntity<Tag> response = service.add(tag);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tag, response.getBody());
    }

    @Test
    void deleteByIdNotFound() {
        when(repo.existsById(1)).thenReturn(false);
        ResponseEntity<Tag> response = service.deleteById(1);
        assertEquals(ResponseEntity.badRequest().build(), response);
    }

    @Test
    void deleteByIdValid() {
        int id = 1;
        Tag tag = new Tag("Test");
        when(repo.existsById(id)).thenReturn(true);
        when(repo.findById(id)).thenReturn(Optional.of(tag));
        ResponseEntity<Tag> response = service.deleteById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tag, response.getBody());
    }

    @Test
    void updateByIdInvalid() {
        Tag tag = new Tag("Test");
        ResponseEntity<Tag> response = service.updateById(1, tag);
        assertEquals(ResponseEntity.badRequest().build(), response);
    }

    @Test
    void updateByIdValid() {
        int validId = 1;
        Tag tag = new Tag("Test");
        Tag newTag = new Tag("Testing");
        when(repo.existsById(validId)).thenReturn(true);
        when(repo.findById(validId)).thenReturn(Optional.of(tag));
        ResponseEntity<Tag> response = service.updateById(validId, newTag);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
