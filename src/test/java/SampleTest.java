import org.easymock.EasyMockExtension;
import org.easymock.MockType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(EasyMockExtension.class)
class SampleTest {
    @Test
    void strictTest() {
        //Wywoływanie funkcji mockowanych musi być w tej samej kolejności co ich mockowanie. W momencie, gdy zamienimy ze sobą dwie funkcje, dodają do listy liczby, test nie przejdzie

        ArrayList<Integer> mock = mock(MockType.STRICT, ArrayList.class);

        expect(mock.add(11)).andReturn(true);
        expect(mock.add(321)).andReturn(true);
        expect(mock.size()).andReturn(2);
        expect(mock.get(0)).andReturn(11);
        replay(mock);

        mock.add(11);
        mock.add(321);

        assertEquals(mock.size(), 2);
        assertEquals(11, (int) mock.get(0));

        verify(mock);
    }
    @Test
    public void niceTest() {
        //Kolejność mockowania nie ma znaczenia.
        //Wszystkie expecty muszą zostać użyte
        ArrayList<Integer> mockList = mock(MockType.NICE, ArrayList.class);
        //zamockowana funkcja jest wywoływana jako druga w kolejności -> test przechodzi
        expect(mockList.size()).andReturn(2);

        expect(mockList.add(10)).andReturn(true);
        expect(mockList.get(0)).andReturn(10);
        replay(mockList);

        mockList.add(10);
        // Gdy dodamy do listy liczbe 30 (ten przyklad nie jest zamockowany!), to zostaną zwrócone domyślne wartości (null, 0, false)
        boolean b = mockList.add(30);
        assertFalse(b);

        assertEquals(mockList.size(), 2);
        assertEquals(10, (int) mockList.get(0));
        verify(mockList);
    }

    @Test
    public void defaultTest() {
        //Wywoływane funkcje MUSZĄ zostać zamockowane
        ArrayList<Integer> mockList = mock(ArrayList.class);
        expect(mockList.add(10)).andReturn(true);
        expect(mockList.size()).andReturn(2);
        expect(mockList.get(0)).andReturn(10);
        replay(mockList);

        assertEquals(10, (int) mockList.get(0));
        assertTrue(mockList.add(10));
        assertEquals(2, mockList.size());

        verify(mockList);
    }
}