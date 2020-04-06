import org.easymock.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(EasyMockExtension.class)
public class FriendshipTest {

    @TestSubject
    FriendshipsMongo friendships = new FriendshipsMongo();

    //A nice mock expects recorded calls in any order and returning null for other calls
    @Mock(type = MockType.NICE)
    FriendsCollection friends;

    @Test
    public void mockingWorksAsExpected(){
        Person joe = new Person("Joe");
        //Zapisanie zachowania - co sie ma stac
        expect(friends.findByName("Joe")).andReturn(joe);
        //Odpalenie obiektu do sprawdzenia zachowania
        replay(friends);
        assertThat(friends.findByName("Joe")).isEqualTo(joe);
    }

    @Test
    public void alexDoesNotHaveFriends(){
        assertThat(friendships.getFriendsList("Alex")).isEmpty();
    }

    @Test
    public void joeHas5Friends(){
        List<String> expected = Arrays.asList(new String[]{"Karol","Dawid","Maciej","Tomek","Adam"});
        Person joe = createMock(Person.class);
        expect(friends.findByName("Joe")).andReturn(joe);
        expect(joe.getFriends()).andReturn(expected);
        replay(friends);
        replay(joe);
        assertThat(friendships.getFriendsList("Joe")).hasSize(5).containsOnly("Karol","Dawid","Maciej","Tomek","Adam");
    }

    @Test
    public void nameOfJoeIsJoe() {
        Person joe = createMock(Person.class);
        expect(joe.getName()).andReturn("Joe");
        replay(joe);

        assertThat(joe.getName()).isEqualTo("Joe");
    }

    @Test
    public void findJoesFriend() {
        Person marc = createMock(Person.class);
        expect(friends.findByName("Marc")).andReturn(marc);
        replay(friends);
        replay(marc);

        assertThat(friends.findByName("Marc")).isEqualTo(marc);
    }

    @Test
    public void joeAndMarcAreFriendsTest() {
        List<String> marcFriends = Collections.singletonList("Joe");
        List<String> joeFriends = Collections.singletonList("Marc");

        Person marc = createMock(Person.class);
        Person joe = createMock(Person.class);

        expect(joe.getFriends()).andReturn(joeFriends);
        expect(marc.getFriends()).andReturn(marcFriends);
        expect(friends.findByName("Joe")).andReturn(joe);
        expect(friends.findByName("Marc")).andReturn(marc);

        replay(marc);
        replay(joe);
        replay(friends);

        assertThat(friendships.areFriends("Joe", "Marc")).isTrue();
    }

    @Test
    public void addingFriendWithEmptyStringThrowsException() {
        Person joe = createMock(Person.class);
        joe.addFriend("");
        expectLastCall().andThrow(new IllegalArgumentException());
    }

    @Test
    public void settingNameWithEmptyStringThrowsException() {
        Person joe = createMock(Person.class);
        joe.setName("");
        expectLastCall().andThrow(new IllegalArgumentException());
    }

    @Test
    public void gettingFriendsShouldReturnListOfFriends() {
        List<String> expected = Arrays.asList(new String[]{"Karol","Dawid","Maciej","Tomek","Adam"});
        Person joe = createMock(Person.class);
        expect(joe.getFriends()).andReturn(expected);
        replay(joe);

        assertThat(joe.getFriends()).isEqualTo(expected);
    }

    @Test
    public void gettingNameShouldReturnName() {
        Person joe = createMock(Person.class);
        expect(joe.getName()).andReturn("Joe");
        replay(joe);

        assertThat(joe.getName()).isEqualTo("Joe");
    }


}