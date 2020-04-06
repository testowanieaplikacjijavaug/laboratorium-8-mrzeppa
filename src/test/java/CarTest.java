import org.easymock.EasyMock;
import org.easymock.*;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;


public class CarTest {
    private Car myFerrari = EasyMock.createMock(Car.class);

    @Test
    public void test_instance_car(){
        assertTrue(myFerrari instanceof Car);
    }

    @Test
    public void test_default_behavior_needsFuel(){
        assertFalse(myFerrari.needsFuel(), "New test double should return False as boolean");
    }

    @Test
    public void test_default_behavior_temperature(){
        assertEquals(0.0, myFerrari.getEngineTemperature(), "New test double should return 0.0");
    }

    @Test
    public void test_stubbing_mock(){
        EasyMock.expect(myFerrari.needsFuel()).andReturn(true);
        EasyMock.replay(myFerrari);
        assertTrue(myFerrari.needsFuel());

    }

    @Test
    public void test_exception(){
        EasyMock.expect(myFerrari.needsFuel()).andThrow(new RuntimeException());
        EasyMock.replay(myFerrari);
        assertThrows(RuntimeException.class, () -> {
            myFerrari.needsFuel();
        });
    }

    @Test
    public void needsFuelMockTest() {
        expect(myFerrari.needsFuel()).andReturn(true);
        replay(myFerrari);
        assertTrue(myFerrari.needsFuel());
        verify(myFerrari);
    }

    @Test
    public void getEngineTemperatureMockTest() {
        expect(myFerrari.getEngineTemperature()).andReturn(Double.MAX_VALUE);
        replay(myFerrari);

        assertEquals(Double.MAX_VALUE, myFerrari.getEngineTemperature());
        verify(myFerrari);
    }

    @Test
    public void canStartMockExceptionTest() {
        expect(myFerrari.canStart(-1D)).andThrow(new IllegalArgumentException());
        replay(myFerrari);

        assertThrows(IllegalArgumentException.class, () -> {
            myFerrari.canStart(-1D);
        });
    }

    @Test
    public void canStartMockTest() {
        expect(myFerrari.canStart(36.6)).andReturn(true);
        replay(myFerrari);

        assertTrue(myFerrari.canStart(36.6));
        verify(myFerrari);
    }

    @Test
    public void getColorTest() {
        expect(myFerrari.getColorAsHex()).andReturn("#ff77aa");
        replay(myFerrari);

        assertEquals("#ff77aa", myFerrari.getColorAsHex());
        verify(myFerrari);
    }
}