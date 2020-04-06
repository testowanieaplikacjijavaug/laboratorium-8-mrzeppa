import static org.assertj.core.api.Assertions.*;
import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.MockType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NoteTest {

    NotesStorage storage;
    NotesService service;

    @BeforeEach
    public void setUp() {
        storage = mock(MockType.NICE, NotesStorage.class);
        service = NotesServiceImpl.createWith(storage);
    }

    @AfterEach
    public void tearDown() {
        storage = null;
        service = null;
    }

    @Test
    public void shouldAddItemToList() {
        replay(storage);
        service.add(Note.of("asdf", 2.0f));
        verify(storage);
    }

    @Test
    public void shouldClearList() {
        replay(storage);
        service.clear();
        verify(storage);
    }

    @Test
    public void shouldNotCalculateAverageOfNullStudent() {
        assertThatThrownBy(() -> service.averageOf(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldNotCalculateAverageOfEmptyStudent() {
        assertThatThrownBy(() -> service.averageOf("")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldCalculateAverageOfStudentWithoutNotes() {
        expect(storage.getAllNotesOf("Roman")).andReturn(new ArrayList<>());
        replay(storage);

        assertThat(service.averageOf("Roman")).isCloseTo(0.0f, offset(0.01f));

        verify(storage);
    }

    @Test
    public void shouldCalculateAverage() {
        expect(storage.getAllNotesOf("Roman")).andReturn(Arrays.asList(Note.of("a", 3.0f), Note.of("b", 2.0f)));
        replay(storage);

        assertThat(service.averageOf("Roman")).isCloseTo(2.5f, offset(0.01f));

        verify(storage);
    }

}