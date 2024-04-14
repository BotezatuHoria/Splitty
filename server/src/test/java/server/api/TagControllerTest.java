package server.api;

import server.services.implementations.TagServiceImplementation;
import commons.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TagControllerTest {

    @Mock
    private TagServiceImplementation tagServiceImplementation;

    private TagController tagController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tagController = new TagController(tagServiceImplementation);
    }

    @Test
    public void testGetAllTags() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Tag1"));
        tags.add(new Tag("Tag2"));
        when(tagServiceImplementation.getAll()).thenReturn(new ResponseEntity<>(tags, HttpStatus.OK));

        ResponseEntity<List<Tag>> response = tagController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(tagServiceImplementation, times(1)).getAll();
    }

    @Test
    public void testGetTagById() {
        int tagId = 1;
        Tag tag = new Tag("Tag1");
        when(tagServiceImplementation.getById(tagId)).thenReturn(new ResponseEntity<>(tag, HttpStatus.OK));

        ResponseEntity<Tag> response = tagController.getById(tagId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tagServiceImplementation, times(1)).getById(tagId);
    }

    @Test
    public void testAddTag() {
        Tag tag = new Tag("Tag1");
        when(tagServiceImplementation.add(tag)).thenReturn(new ResponseEntity<>(tag, HttpStatus.CREATED));

        ResponseEntity<Tag> response = tagController.add(tag);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(tag, response.getBody());
        verify(tagServiceImplementation, times(1)).add(tag);
    }

    @Test
    public void testUpdateTagById() {
        int tagId = 1;
        Tag tag = new Tag("UpdatedTag");
        when(tagServiceImplementation.updateById(tagId, tag)).thenReturn(new ResponseEntity<>(tag, HttpStatus.OK));

        ResponseEntity<Tag> response = tagController.updateById(tagId, tag);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tag, response.getBody());
        verify(tagServiceImplementation, times(1)).updateById(tagId, tag);
    }

    @Test
    public void testDeleteTagById() {
        int tagId = 1;
        Tag tag = new Tag("Tag1");
        when(tagServiceImplementation.deleteById(tagId)).thenReturn(new ResponseEntity<>(tag, HttpStatus.NO_CONTENT));

        ResponseEntity<Tag> response = tagController.deleteById(tagId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tagServiceImplementation, times(1)).deleteById(tagId);
    }
}