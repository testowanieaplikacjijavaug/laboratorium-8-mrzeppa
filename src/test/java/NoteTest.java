import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NoteTest {

    NotesStorage storage;
    NotesService service;

    @BeforeEach
    public void setUp() {
        storage = createMock(NotesStorage.class);
        service = createMock(NotesService.class);
    }

    @AfterEach
    public void tearDown() {
        storage = null;
        service = null;
    }

    @Test
    public void givenValidRecordShouldBeAddedToList() {
        NotesStorage storage = createMock(NotesStorage.class);
        List<Note> expected = Arrays.asList(
                Note.of("Andrew",5),
                Note.of("Andrew", 4)
        );

        expect(storage.getAllNotesOf("Andrew")).andReturn(expected);
        replay(storage);
        assertThat(storage.getAllNotesOf("Andrew")).hasSize(2).containsAll(expected);

        verify(storage);
    }


    @Test
    public void shouldCalculateAverageOfNotes() {
        expect(service.averageOf("Andrew")).andReturn(3.7f);
        replay(service);

        assertThat(service.averageOf("Andrew")).isEqualTo(3.7f);
        verify(service);
    }

    @Test
    public void shouldNotCalculateAverageOfNotesWhenThereIsNoMarksInUser() {
        EasyMock.expect(service.averageOf("Ox")).andThrow(new IllegalArgumentException());
        EasyMock.replay(service);

        assertThrows(IllegalArgumentException.class, () -> {
            service.averageOf("Ox");
        });

        verify(service);

    }

    @Test
    public void whenCalculatingAverageWithEmptyNameShouldRaiseAnException() {
        EasyMock.expect(service.averageOf(""))
                .andThrow(new IllegalArgumentException("Imię ucznia nie może być puste"));
        EasyMock.replay(service);

        assertThrows(
                IllegalArgumentException.class,
                () -> { service.averageOf("");},
                "Imię ucznia nie może być puste"
        );

        verify(service);
    }

    @Test
    public void whenCalculatingAverageWithNullNameShouldRaiseAnException() {
        EasyMock.expect(service.averageOf(null))
                .andThrow(new IllegalArgumentException("Imię ucznia nie może być null"));
        EasyMock.replay(service);
        assertThrows(
                IllegalArgumentException.class,
                () -> { service.averageOf(null);},
                "Imię ucznia nie może być null"
        );

        verify(service);
    }

    @Test
    public  void callingClearFunctionShouldClearMap(){
        ArrayList<Note> noteList = new ArrayList<Note>();
        noteList.add(Note.of("John", 5.0f));
        service.clear();
        EasyMock.expectLastCall().andAnswer(() -> {
            noteList.clear();
            return null;
        }).times(1);
        replay(service);

        service.clear();
        assertThat(noteList.isEmpty()).isTrue();
        verify(service);
    }

    @Test
    public  void addTwoTimesSamePerson(){
        Note note = Note.of("Lmal", 3.0f);
        ArrayList<Note> mockList = new ArrayList<Note>();

        storage.add(note);
        EasyMock.expectLastCall().andAnswer(() -> {
            mockList.add(note);
            return null;
        }).times(2);
        replay(storage);

        storage.add(note);
        storage.add(note);

        assertThat(mockList.get(0).getName()).isEqualTo("Lmal");
        verify(storage);
    }

    @Test
    public void addingPersonTest() {
        Note note = Note.of("Lmal", 3.0f);
        ArrayList<Note> mockList = new ArrayList<Note>();
        storage.add(note);
        EasyMock.expectLastCall().andAnswer(() -> {
            mockList.add(note);
            return null;
        }).times(1);
        replay(storage);

        storage.add(note);
        assertThat(mockList.get(0).getName()).isEqualTo("Lmal");

        verify(storage);
    }

    @Test
    public void getNoteTest() {
        Note note = createMock(Note.class);
        expect(note.getNote()).andReturn(3.f);
        replay(note);

        assertThat(note.getNote()).isEqualTo(3.f);
    }


}