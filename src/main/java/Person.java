import java.util.ArrayList;
import java.util.List;

public class Person {

    private String name;
    private List<String> friends;

    public Person(String name){
        this.setName(name);
        setFriends(new ArrayList<String>());
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public void addFriend(String name){
        if(name.isEmpty())
            throw new IllegalArgumentException();
        if(!friends.contains(name)){
            friends.add(name);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.isEmpty())
            throw new IllegalArgumentException();
        this.name = name;
    }

}