import java.util.ArrayList;
import java.util.List;

class Family {
    private List<Guest> members;
    private List<Integer> roomNumbers;

    public Family() {
        this.members = new ArrayList<>();
        this.roomNumbers = new ArrayList<>();
    }

    // Добавление члена семьи
    public void addMember(Guest guest, int roomNumber) {
        members.add(guest);
        roomNumbers.add(roomNumber);
    }

    // Получение списка членов семьи
    public List<Guest> getMembers() {
        return new ArrayList<>(members);
    }

    // Получение списка номеров, занимаемых семьей
    public List<Integer> getRoomNumbers() {
        return new ArrayList<>(roomNumbers);
    }

    // Проверка, является ли гость членом этой семьи
    public boolean isMember(Guest guest) {
        return members.contains(guest);
    }

    // Проверка, является ли номер частью этой семьи
    public boolean isFamilyRoom(int roomNumber) {
        return roomNumbers.contains(roomNumber);
    }

    // Получение количества членов семьи
    public int size() {
        return members.size();
    }
}