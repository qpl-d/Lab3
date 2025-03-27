package Lab;
import java.util.ArrayList;

public class Family {
    private ArrayList<Guest> members;
    private ArrayList<Integer> roomNumbers;

    public Family() {
        this.members = new ArrayList<>();
        this.roomNumbers = new ArrayList<>();
    }

    // добавление члена семьи
    public void addMember(Guest guest, int roomNumber) {
        members.add(guest);
        roomNumbers.add(roomNumber);
    }

    // получение списка членов семьи
    public ArrayList<Guest> getMembers() {
        return new ArrayList<>(members);
    }

    // получение списка номеров, которые занимает семья
    public ArrayList<Integer> getRoomNumbers() {
        return new ArrayList<>(roomNumbers);
    }

    // является ли гость членом семьи
    public boolean isMember(Guest guest) {
        return members.contains(guest);
    }

    // получение количества членов семьи
    public int size() {
        return members.size();
    }
}