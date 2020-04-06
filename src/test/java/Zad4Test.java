import org.easymock.MockType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(EasyMockExtension.class)
class Zad4Test {
    @Test
    public void withoutVerify() {
        // Testujemy metode b
        Zad4 mock = mock(MockType.NICE, Zad4.class);
        expect(mock.a()).andReturn(2);
        expect(mock.b()).andReturn(2);
        replay(mock);
        //Zamierzony bład
        assertEquals(2, mock.b());
        //Test przechodzi
    }

    @Test
    public void withVerify() {
        // Testujemy metode a
        Zad4 mock = mock(MockType.NICE, Zad4.class);
        expect(mock.a()).andReturn(3);
        expect(mock.b()).andReturn(3);
        replay(mock);

        assertEquals(3, mock.b());

        //niewywołana metoda a będzie przyczyną nieprzechodzącego testu
        //verify sprawdza czy wszystkie metody zostały wywołane

        //zastosowanie assertThrows by test przeszedł

        assertThrows(AssertionError.class, () ->{
            verify(mock);
        });
    }

}